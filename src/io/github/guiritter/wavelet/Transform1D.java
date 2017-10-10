package io.github.guiritter.wavelet;

import static io.github.guiritter.wavelet.Math.box;
import static io.github.guiritter.wavelet.Math.convolution;
import static io.github.guiritter.wavelet.Math.downsample;
import static io.github.guiritter.wavelet.Math.removeTrailingFiller;
import static io.github.guiritter.wavelet.Math.sum;
import static io.github.guiritter.wavelet.Math.unbox;
import static io.github.guiritter.wavelet.Math.upsample;
import static java.lang.Math.sqrt;
import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author Guilherme Alan Ritter
 */
public final class Transform1D implements TransformData{

    private final LinkedList<Double[]> detailList = new LinkedList<>();

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

    private int j;

    private final int JMaximum;

    private final int originalSize;

    private double returnArray[][];

    private double smooth[];

    private double uDetail[];

    private double uSmooth[];

    private double wDetail[];

    private double wSmooth[];

    @Override
    public int getJ() {
        return detailList.size();
    }

    @Override
    public int getJMaximum() {
        return JMaximum;
    }

    @Override
    public boolean transformForward() {
        if (detailList.size() >=  JMaximum) {
            return false;
        }
        detailList.add(box(downsample(convolution(smooth, filterD))));
        smooth = downsample(convolution(smooth, filterC));
        //*
        System.out.println("smooth " + detailList.size());
        System.out.println(Arrays.toString(smooth));
        System.out.println("detail " + detailList.size());
        System.out.println(Arrays.toString(detailList.getLast()));
        /**/
        return true;
    }

    public double[][] transformInverse(int J) {
        if (J > JMaximum) {
            throw new IllegalArgumentException("Requested level (" + J + ") is higher than maximum level (" + JMaximum + ").");
        }
        if (J < 0) {
            returnArray = null;
        } else {
            if (J > detailList.size()) {
                for (i = detailList.size(); i < J; i++) {
                    transformForward();
                }
            }
            returnArray = new double[J + 1][];
        }
        if (J == detailList.size()) {
            returnArray[0] = new double[smooth.length];
            System.arraycopy(smooth, 0, returnArray[0], 0, smooth.length);
            for (i = 1; i < returnArray.length; i++) {
                returnArray[i] = unbox(detailList.get(detailList.size() - i));
            }
        } else if (J < detailList.size()) {
            for (i = detailList.size() - 1; i >= J; i--) {
                if (i == (detailList.size() - 1)) {
                    uSmooth = upsample(smooth);
                } else {
                    uSmooth = upsample(returnArray[0]);
                }
                uDetail = upsample(unbox(detailList.get(i)));
                if (((i == 0) && (uSmooth.length > originalSize)) || ((i != 0) && (uSmooth.length > detailList.get(i - 1).length))) {
                    uSmooth = removeTrailingFiller(uSmooth);
                }
                if (((i == 0) && (uDetail.length > originalSize)) || ((i != 0) && (uDetail.length > detailList.get(i - 1).length))) {
                    uDetail = removeTrailingFiller(uDetail);
                }
                wSmooth = convolution(uSmooth, filterF);
                wDetail = convolution(uDetail, filterG);
                if (((i == 0) && (wSmooth.length > originalSize)) || ((i != 0) && (wDetail.length > detailList.get(i - 1).length))) {
                    wSmooth = removeTrailingFiller(wSmooth);
                    wDetail = removeTrailingFiller(wDetail);
                }
                returnArray[0] = sum(wSmooth, wDetail);
                System.out.println("reconstructed smooth " + i);
                System.out.println(Arrays.toString(returnArray[0]));
            }
            for (i = 1; i < returnArray.length; i++) {
                returnArray[i] = unbox(detailList.get(J - i));
            }
        }
        return returnArray;
    }

    public Transform1D(double original[], double filterC[], double filterD[], double filterF[], double filterG[], int J) {
        originalSize = original.length;
        if (original.length < 2) {
            throw new IllegalArgumentException("Array must have at least two values.");
        }
        i = original.length;
        j = 0;
        while (true) {
            i = i + filterC.length - 1;
            i /= 2;
            if (i == 1) {
                break;
            }
            j++;
        }
        JMaximum = j;
             smooth = new double[original.length]; System.arraycopy(original, 0, smooth, 0, original.length);
        this.filterC = new double[filterC.length]; System.arraycopy(filterC, 0, this.filterC, 0, filterC.length);
        this.filterD = new double[filterD.length]; System.arraycopy(filterD, 0, this.filterD, 0, filterD.length);
        this.filterF = new double[filterF.length]; System.arraycopy(filterF, 0, this.filterF, 0, filterF.length);
        this.filterG = new double[filterG.length]; System.arraycopy(filterG, 0, this.filterG, 0, filterG.length);
        for (i = 0; i < J; i++) {
            transformForward();
        }
        /*
        double y0[] = convolution(original, filterC);
        double y1[] = convolution(original, filterD);
        double v0[] = downsample(y0);
        double v1[] = downsample(y1);
        double u0[] = upsample(v0);
        double u1[] = upsample(v1);
        double w0[] = convolution(u0, filterF);
        double w1[] = convolution(u1, filterG);
        */
//        System.out.println(Arrays.toString(v0));
//        System.out.println(Arrays.toString(v1));
//        System.out.println();
//        for (int i = 0; i < w0.length; i++) {
//            System.out.print(w0[i] + w1[i]);
//            System.out.print("\t");
//        }
//        System.out.println();
    }

    public static void main(String args[]) {
        double x = 1 / sqrt(2);
        //*
        Transform1D transform1D = new Transform1D(
         new double[]{2, 3, 5, 7, 11, 13},
//         TestData.signalWithNoise,
         new double[]{ x,  x},
         new double[]{ x, -x},
         new double[]{ x,  x},
         new double[]{-x,  x},
         10);
        double transformInverse[][] = transform1D.transformInverse(2);
        System.out.println(Arrays.deepToString(transformInverse));
        /**/
//        transform1D.transformForward();
//        transform1D.transformForward();
//        transform1D.transformForward();
//        transform1D.transformForward();
//        double transformInverse[][] = transform1D.transformInverse(1);
//        System.out.println(Arrays.deepToString(transformInverse));
        /*
        double doubleArray[];
        Transform1D transform1D;
        for (int sizeI = 2; sizeI < 103; sizeI++) {
//        for (int sizeI = 8; sizeI < 9; sizeI++) {
            doubleArray = new double[sizeI];
            Arrays.fill(doubleArray, 0);
            transform1D = new Transform1D(
             doubleArray,
             new double[]{ x,  x},
             new double[]{ x, -x},
             new double[]{ x,  x},
             new double[]{-x,  x},
             0);
            while (transform1D.getJ() != transform1D.getJMaximum()) {
                transform1D.transformForward();
            }
            System.out.println(sizeI + "\t" + transform1D.transformInverse(0)[0].length);
        }
        */
    }
}
