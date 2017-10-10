package io.github.guiritter.wavelet;

import static io.github.guiritter.wavelet.Detail2D.CD;
import static io.github.guiritter.wavelet.Detail2D.DC;
import static io.github.guiritter.wavelet.Detail2D.DD;
import io.github.guiritter.wavelet.gui.MainFrame;
import java.awt.Point;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_BYTE_GRAY;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.pow;
import static java.lang.Math.round;
import static java.lang.Math.sqrt;

/**
 *
 * @author Guilherme Alan Ritter
 */
public final class Main {

    private static final int detailOffset[][] = new int[][] {
        {CD, 1, 0},
        {DC, 0, 1},
        {DD, 1, 1}
    };

    private static int i;

    private static int j;

    private static int k;

    private static int x;

    private static int y;

    /**
     * https://stackoverflow.com/questions/21176754/how-can-i-convert-an-image-to-grayscale-without-losing-transparency
     * @param width
     * @param height
     * @return
     */
    public static BufferedImage create2ByteGrayAlphaImage(int width, int height) {
        int[] bandOffsets = new int[]{1, 0}; // gray + alpha
        int bands = bandOffsets.length; // 2, that is

        // Init data buffer of type byte
        DataBuffer buffer = new DataBufferByte(width * height * bands);

        // Wrap the data buffer in a raster
        WritableRaster raster = Raster.createInterleavedRaster(buffer, width, height, width * bands, bands, bandOffsets, new Point(0, 0));

        // Create a custom BufferedImage with the raster and a suitable color model
        return new BufferedImage(new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_GRAY), true, false, Transparency.TRANSLUCENT, DataBuffer.TYPE_BYTE), raster, false, null);
    }

    /**
     * Assumes the transform originated from an image: the original values
     * ranged from 0 to 255. The smooth coefficients must be divided by
     * 2 * J and the detail coefficients must be summed by 2 * J * 255 and
     * divided by 4 * J.
     * @param componentArray
     */
    public static final void normalize2DImage(double componentArray[][][][][]) {
        int J = componentArray[0].length - 1;
        for (i = 0; i < componentArray.length; i++) {
            for (y = 0; y < componentArray[i][0][0].length; y++) {
                for (x = 0; x < componentArray[i][0][0][0].length; x++) {
                    componentArray[i][0][0][y][x] /= pow(2, J);//2.0 * ((double) J);
                }
            }
        }
        FitLinear fit;
        double minimum;
        double maximum;
        for (i = 0; i < componentArray.length; i++) {
            for (j = 1; j <= J; j++) {
                for (k = 0; k < 3; k++) {
                    minimum = Double.POSITIVE_INFINITY;
                    maximum = Double.NEGATIVE_INFINITY;
                    for (y = 0; y < componentArray[i][j][k].length; y++) {
                        for (x = 0; x < componentArray[i][j][k][0].length; x++) {
                            minimum = min(minimum, componentArray[i][j][k][y][x]);
                            maximum = max(maximum, componentArray[i][j][k][y][x]);
//                            componentArray[i][j][k][y][x] += 2.0 * ((double) J) * 255.0;
//                            componentArray[i][j][k][y][x] /= 4.0 * ((double) J);
                        }
                    }
                    fit = new FitLinear(minimum, 0, maximum, 255);
                    for (y = 0; y < componentArray[i][j][k].length; y++) {
                        for (x = 0; x < componentArray[i][j][k][0].length; x++) {
                            componentArray[i][j][k][y][x] = fit.f(componentArray[i][j][k][y][x]);
                        }
                    }
                }
            }
        }
    }

    public static final BufferedImage transform2DToImage(double componentArray[][][][][]) {
        normalize2DImage(componentArray);
        int width = componentArray[0][0][0][0].length;
        int height = componentArray[0][0][0].length;
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

    public static void main(String args[]) throws IOException {
        double a = 1 / sqrt(2);
        //*
        double c[] = new double[]{ a,  a};
        double d[] = new double[]{-a,  a};
        double f[] = new double[]{ a,  a};
        double g[] = new double[]{ a, -a};
        /**/
        /*
        double c[] = new double[]{-0.1294,  0.2241,  0.8365,  0.4830};
        double d[] = new double[]{-0.4830,  0.8365, -0.2241, -0.1294};
        double f[] = new double[]{ 0.4830,  0.8365,  0.2241, -0.1294};
        double g[] = new double[]{-0.1294, -0.2241,  0.8365, -0.4830};
        /**/
        /*
        double c[] = new double[]{-0.1294,  0.2241,  0.8365,  0.4830};
        double d[] = new double[]{-0.4830,  0.8365, -0.2241, -0.1294};
        double f[] = new double[]{ 0.4830,  0.8365,  0.2241, -0.1294};
        double g[] = new double[]{-0.1294, -0.2241,  0.8365, -0.4830};
        /**/
        /*
        double c[] = new double[]{-0.0157, -0.0727,  0.3849,  0.8526,  0.3379, -0.0727};
        double d[] = new double[]{ 0.0727,  0.3379, -0.8526,  0.3849,  0.0727, -0.0157};
        double f[] = new double[]{-0.0727,  0.3379,  0.8526,  0.3849, -0.0727, -0.0157};
        double g[] = new double[]{-0.0157,  0.0727,  0.3849, -0.8526,  0.3379,  0.0727};
        /**/
        int b = 6;
//        String s = "C:/users/guir/documents/Lenna.png";
//        String s = "C:/users/guir/documents/test downsampling without smoothing.png";
        String s = "/home/guir/Imagens/thumb-1920-262599.jpg";
        int e = b;
        /*
        Transform2D transform2D = new Transform2D(
         imageToMatrix(ImageIO.read(new File(s)), 0),
         new double[]{ a,  a},
         new double[]{-a,  a},
         new double[]{ a,  a},
         new double[]{ a, -a},
         b);
        double componentArray[][][][][] = new double[][][][][]{transform2D.transformInverse(b)};
        /**/
        /*
        Transform2D transform2D = new Transform2D(imageToMatrix(ImageIO.read(new File(s)), 0), c, d, f, g, b);
        double componentArray[][][][][] = new double[][][][][]{transform2D.transformInverse(e), null, null};
        transform2D = new Transform2D(imageToMatrix(ImageIO.read(new File(s)), 1), c, d, f, g, b);
        componentArray[1] = transform2D.transformInverse(e);
        transform2D = new Transform2D(imageToMatrix(ImageIO.read(new File(s)), 2), c, d, f, g, b);
        componentArray[2] = transform2D.transformInverse(e);
//        transform2D = new Transform2D(imageToMatrix(ImageIO.read(new File(s)), 3), c, d, f, g, b);
//        componentArray[3] = transform2D.transformInverse(e);
        /**/
//        ImageIO.write(transform2DToImage(componentArray), "png", new File("/home/guir/Imagens/test out.png"));
        final MainFrame gui = new MainFrame() {

            @Override
            public void onImageButtonPressed(File file) {
            }
        };
    }
}
