package io.github.guiritter.wavelet;

import static io.github.guiritter.wavelet.Detail2D.DD;
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

/**
 *
 * @author Guilherme Alan Ritter
 */
public final class Main {

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
                    componentArray[i][0][0][y][x] /= 2.0 * ((double) J);
                }
            }
        }
        for (i = 0; i < componentArray.length; i++) {
            for (j = 1; j <= J; j++) {
                for (k = 0; k < 3; k++) {
                    for (y = 0; y < componentArray[i][j][k].length; y++) {
                        for (x = 0; x < componentArray[i][j][k][0].length; x++) {
                            componentArray[i][j][k][y][x] += 2.0 * ((double) J) * 255.0;
                            componentArray[i][j][k][y][x] /= 4.0 * ((double) J);
                        }
                    }
                }
            }
        }
    }

    public static final BufferedImage transform2DToImage(double componentArray[][][][][]) {
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
        int startX = 0;
        int startY = 0;
        int color[] = new int[componentArray.length];
        for (y = 0; y < componentArray[0][0][0].length; y++) {
            for (x = 0; x < componentArray[0][0][0][0].length; x++) {
                for (i = 0; i < color.length; i++) {
//                    color[i] = componentArray[0][0][0][y][x];
                }
            }
        }
        return image;
    }
}
