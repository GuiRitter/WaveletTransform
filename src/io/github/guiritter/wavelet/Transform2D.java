package io.github.guiritter.wavelet;

import static io.github.guiritter.wavelet.Detail2D.CD;
import static io.github.guiritter.wavelet.Detail2D.DC;
import static io.github.guiritter.wavelet.Detail2D.DD;
import static io.github.guiritter.wavelet.Math.convolutionX;
import static io.github.guiritter.wavelet.Math.convolutionY;
import static io.github.guiritter.wavelet.Math.downsample;
import static io.github.guiritter.wavelet.Math.removeTrailingFiller;
import static io.github.guiritter.wavelet.Math.sum;
import static io.github.guiritter.wavelet.Math.upsample;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import static java.lang.Math.sqrt;
import java.util.LinkedList;
import javax.imageio.ImageIO;

/**
 *
 * @author Guilherme Alan Ritter
 */
public final class Transform2D {

//    private final LinkedList<Double[][][]> detailList = new LinkedList<>();
    private final LinkedList<Detail2D> detailList = new LinkedList<>();

    /**
     * Low pass synthesis filter;
     */
    private final double filterC[];

    /**
     * High pass synthesis filter;
     */
    private final double filterD[];

    /**
     * Low pass analysis filter;
     */
    private final double filterF[];

    /**
     * High pass analysis filter;
     */
    private final double filterG[];

    private int i;

    private final int JMaximum;

    private int maximumSizeX;

    private int maximumSizeY;

    private final int originalSizeX;

    private final int originalSizeY;

    private double returnArray[][][][];

    private double smooth[][];

    private double temporaryC[][];

    private double temporaryD[][];

    private double uDetailCD[][];

    private double uDetailDC[][];

    private double uDetailDD[][];

    private double uSmooth[][];

    private double wDetailCD[][];

    private double wDetailDC[][];

    private double wDetailDD[][];

    private double wSmooth[][];

    private int x;

    private int y;

    public static final double[][] imageToMatrix(BufferedImage image, int componentIndex) {
        double[][] doubleMatrix = new double[image.getHeight()][image.getWidth()];
        int x;
        int y;
        WritableRaster raster = image.getRaster();
        int color[] = raster.getPixel(0, 0, (int[]) null);
        for (y = 0; y < image.getHeight(); y++) {
            for (x = 0; x < image.getWidth(); x++) {
                raster.getPixel(x, y, color);
                doubleMatrix[y][x] = color[componentIndex];
            }
        }
        return doubleMatrix;
    }

    public static final double[][] matrixClone(double a[][]) {
        int x;
        int y;
        double b[][] = new double[a.length][a[0].length];
        for (y = 0; y < a.length; y++) {
            for (x = 0; x < a[y].length; x++) {
                b[y][x] = a[y][x];
            }
        }
        return b;
    }

    public boolean transformForward() {
        if (detailList.size() >=  JMaximum) {
            return false;
        }
        temporaryC = convolutionY(smooth, filterC);
        temporaryD = convolutionY(smooth, filterD);
//        detailList.add(new Double[3][][]);
        detailList.add(new Detail2D());
                         smooth = downsample(convolutionX(temporaryC, filterC));
        detailList.getLast().cd = downsample(convolutionX(temporaryC, filterD));
        detailList.getLast().dc = downsample(convolutionX(temporaryD, filterC));
        detailList.getLast().dd = downsample(convolutionX(temporaryD, filterD));
        /*
        System.out.println(Arrays.deepToString(smooth));
        System.out.println(Arrays.deepToString(detailList.getLast().cd));
        System.out.println(Arrays.deepToString(detailList.getLast().dc));
        System.out.println(Arrays.deepToString(detailList.getLast().dd));
        /**/
//        if (detailList.size() == 3) {
//            System.out.println(Arrays.deepToString(smooth));
//        }
        return true;
    }

    public double[][][][] transformInverse(int J) {
        if (J > JMaximum) {
            throw new IllegalArgumentException("Required leve (" + J + ")l is higher than maximum level (" + JMaximum + ").");
        }
        if (J < 0) {
            returnArray = null;
        } else {
            if (J > detailList.size()) {
                for (i = detailList.size(); i < J; i++) {
                    transformForward();
                }
            }
            returnArray = new double[J + 1][][][];
            returnArray[0] = new double[1][][];
        }
        if (J == detailList.size()) {
            returnArray[0][0] = matrixClone(smooth);
            for (i = 1; i < returnArray.length; i++) {
                returnArray[i] = new double[3][][];
                returnArray[i][CD] = matrixClone(detailList.get(detailList.size() - i).cd);
                returnArray[i][DC] = matrixClone(detailList.get(detailList.size() - i).dc);
                returnArray[i][DD] = matrixClone(detailList.get(detailList.size() - i).dd);
            }
        } else if (J < detailList.size()) {
            for (i = detailList.size() - 1; i >= J; i--) {
                if (i == 0) {
                    maximumSizeX = originalSizeX;
                    maximumSizeY = originalSizeY;
                } else {
                    maximumSizeX = detailList.get(i - 1).dd[0].length;
                    maximumSizeY = detailList.get(i - 1).dd.length;
                }
                if (i == (detailList.size() - 1)) {
                    uSmooth = upsample(smooth);
                } else {
                    uSmooth = upsample(returnArray[0][0]);
                }
                uDetailCD = upsample(detailList.get(i).cd);
                uDetailDC = upsample(detailList.get(i).dc);
                uDetailDD = upsample(detailList.get(i).dd);
                uSmooth   = removeTrailingFiller(uSmooth  , maximumSizeX, maximumSizeY);
                uDetailCD = removeTrailingFiller(uDetailCD, maximumSizeX, maximumSizeY);
                uDetailDC = removeTrailingFiller(uDetailDC, maximumSizeX, maximumSizeY);
                uDetailDD = removeTrailingFiller(uDetailDD, maximumSizeX, maximumSizeY);
                wSmooth   = convolutionX(uSmooth  , filterF);
                wDetailCD = convolutionX(uDetailCD, filterG);
                wDetailDC = convolutionX(uDetailDC, filterF);
                wDetailDD = convolutionX(uDetailDD, filterG);
                temporaryC = sum(wSmooth  , wDetailCD);
                temporaryD = sum(wDetailDC, wDetailDD);
                temporaryC = convolutionY(temporaryC, filterF);
                temporaryD = convolutionY(temporaryD, filterG);
                /**/try {
                returnArray[0][0] = removeTrailingFiller(sum(temporaryC, temporaryD), maximumSizeX, maximumSizeY);
                /**/} catch (Exception ex) {
                    System.out.println("ex");
                }
            }
            for (i = 1; i < returnArray.length; i++) {
                returnArray[i] = new double[][][]{detailList.get(J - i).cd, detailList.get(J - i).dc, detailList.get(J - i).dd};
            }
        }
        return returnArray;
    }

    public Transform2D(double original[][], double filterC[], double filterD[], double filterF[], double filterG[], int J) {
        originalSizeX = original[0].length;
        originalSizeY = original.length;
        if ((originalSizeX < 2) || (originalSizeY < 2)) {
            throw new IllegalArgumentException("Array must have at least two values.");
        }
        x = originalSizeX;
        y = originalSizeY;
        i = 0;
        while (true) {
            x = x + filterC.length - 1;
            y = y + filterC.length - 1;
            x /= 2;
            y /= 2;
            if ((x <= (filterC.length - 1)) || (y <= (filterC.length - 1))) {
                break;
            }
            i++;
        }
        JMaximum = i;
        smooth = matrixClone(original);
        this.filterC = new double[filterC.length]; System.arraycopy(filterC, 0, this.filterC, 0, filterC.length);
        this.filterD = new double[filterD.length]; System.arraycopy(filterD, 0, this.filterD, 0, filterD.length);
        this.filterF = new double[filterF.length]; System.arraycopy(filterF, 0, this.filterF, 0, filterF.length);
        this.filterG = new double[filterG.length]; System.arraycopy(filterG, 0, this.filterG, 0, filterG.length);
        for (i = 0; i < J; i++) {
            transformForward();
        }
    }

    public static void main(String args[]) throws IOException {
        double x = 1 / sqrt(2);
        Transform2D transform2D = new Transform2D(
         imageToMatrix(ImageIO.read(new File("C:/users/guir/documents/test.png")), 0),
         new double[]{ x,  x},
         new double[]{ x, -x},
         new double[]{ x,  x},
         new double[]{-x,  x},
         2);
    }
}
