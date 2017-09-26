package io.github.guiritter.wavelet;

import static io.github.guiritter.wavelet.Math.convolution;
import static java.lang.Math.sqrt;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Guilherme Alan Ritter
 */
public final class Transform1D {

    private final List<Double[]> detailList = new LinkedList<>();

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

    private final double original[];

    private double smooth[];

    public Transform1D(double[] original, double[] filterC, double[] filterD, double[] filterF, double[] filterG, int J) {
        this.original = new double[original.length]; System.arraycopy(original, 0, this.original, 0, original.length);
        this.filterC  = new double[filterC .length]; System.arraycopy(filterC , 0, this.filterC , 0, filterC .length);
        this.filterD  = new double[filterD .length]; System.arraycopy(filterD , 0, this.filterD , 0, filterD .length);
        this.filterF  = new double[filterF .length]; System.arraycopy(filterF , 0, this.filterF , 0, filterF .length);
        this.filterG  = new double[filterG .length]; System.arraycopy(filterG , 0, this.filterG , 0, filterG .length);
        J = 1;
        double y0[] = convolution(original, filterC);
        double y1[] = convolution(original, filterD);
        System.out.println(Arrays.toString(y1));
    }

    public static void main(String args[]) {
        double x = 1 / sqrt(2);
        Transform1D transform1D = new Transform1D(
         new double[]{-2, 1, 3, 2, -3, 4},
         new double[]{ x,  x},
         new double[]{ x, -x},
         new double[]{ x,  x},
         new double[]{-x,  x},
         1);
    }
}
