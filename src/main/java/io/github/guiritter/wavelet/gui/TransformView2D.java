package io.github.guiritter.wavelet.gui;

import io.github.guiritter.image_component.ImageComponent;
import static io.github.guiritter.wavelet.Detail2D.DD;
import static io.github.guiritter.wavelet.Main.create2ByteGrayAlphaImage;
import static io.github.guiritter.wavelet.Main.normalize2DImage;
import static io.github.guiritter.wavelet.Transform2D.detailOffset;
import java.awt.Component;
import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_BYTE_GRAY;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import java.awt.image.WritableRaster;
import static java.lang.Math.round;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 *
 * @author Guilherme Alan Ritter
 */
public final class TransformView2D implements TransformView{

    private final ImageComponent imageComponent;

    private final JPanel panel;

    @Override
    public BufferedImage getImage() {
        return imageComponent.getImage();
    }

    @Override
    public void setView(double[][] signalArray) {
        throw new UnsupportedOperationException("Not supported for this version.");
    }

    @Override
    public void setView(double[][][][][] componentArray) {
        imageComponent.setImage(transform2DToImage(componentArray));
    }

    public static final BufferedImage transform2DToImage(double componentArray[][][][][]) {
//        double componentArray[][][][][] = matrixClone(input);
        normalize2DImage(componentArray);
        int width = componentArray[0][0][0][0].length;
        int height = componentArray[0][0][0].length;
        int i, j, k, x = 0, y;
        for (i = 1; i < componentArray[0].length; i++) {
            width += componentArray[0][i][DD][0].length;
            height += componentArray[0][i][DD].length;
        }
        BufferedImage image;
        if (componentArray.length == 2) {
            image = create2ByteGrayAlphaImage(width, height);
        } else {
            image = new BufferedImage(width, height, (componentArray.length == 1) ? TYPE_BYTE_GRAY : (componentArray.length == 4) ? TYPE_INT_ARGB : TYPE_INT_RGB);
        }
        WritableRaster raster = image.getRaster();
        int color[] = new int[componentArray.length];
        for (y = 0; y < componentArray[0][0][0].length; y++) {
            for (x = 0; x < componentArray[0][0][0][0].length; x++) {
                for (i = 0; i < color.length; i++) {
                    color[i] = (int) round(componentArray[i][0][0][y][x]);
                }
                raster.setPixel(x, y, color);
            }
        }
        int startX = x;
        int startY = y;
        for (j = 1; j < componentArray[0].length; j++) {
            for (int offset[] : detailOffset) {
                k = offset[0];
                for (y = 0; y < componentArray[0][j][k].length; y++) {
                    for (x = 0; x < componentArray[0][j][k][0].length; x++) {
                        for (i = 0; i < color.length; i++) {
                            color[i] = (int) round(componentArray[i][j][k][y][x]);
                        }
                        raster.setPixel(x + (offset[1] * startX), y + (offset[2] * startY), color);
                    }
                }
            }
            startX += x;
            startY += y;
        }
        return image;
    }

    public TransformView2D(double componentArray[][][][][]) {
        this.imageComponent = new ImageComponent(transform2DToImage(componentArray));
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(imageComponent);
    }

    @Override
    public Component getComponent() {
        return panel;
    }
}
