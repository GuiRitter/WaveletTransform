package io.github.guiritter.wavelet;

import static io.github.guiritter.wavelet.Math.convolution;
import static io.github.guiritter.wavelet.Math.downsample;
import static io.github.guiritter.wavelet.Math.upsample;
import static java.lang.Math.sqrt;
import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author Guilherme Alan Ritter
 */
public final class Transform1D {

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

    /**
     * First element is the original signal.
     */
    private final LinkedList<Double[]> smoothList = new LinkedList<>();

    private double vDetail[];

    private double vSmooth[];

    private double yDetail[];

    private double ySmooth[];

    public void transformForward() {
        detailList.add(Arrays.stream(downsample(convolution(smoothList.getLast(), filterD))).boxed().toArray(Double[]::new));
        smoothList.add(Arrays.stream(downsample(convolution(smoothList.getLast(), filterC))).boxed().toArray(Double[]::new));
        System.out.println("smooth " + (smoothList.size() - 1));
        System.out.println(Arrays.toString(smoothList.getLast()));
        System.out.println("detail " + (detailList.size() - 1));
        System.out.println(Arrays.toString(detailList.getLast()));
    }

    public Transform1D(double[] original, double[] filterC, double[] filterD, double[] filterF, double[] filterG, int J) {
        smoothList.add(Arrays.stream(original).boxed().toArray(Double[]::new));
        detailList.add(null);
        this.filterC = new double[filterC.length];
        System.arraycopy(filterC, 0, this.filterC, 0, filterC.length);
        this.filterD = new double[filterD.length];
        System.arraycopy(filterD, 0, this.filterD, 0, filterD.length);
        this.filterF = new double[filterF.length];
        System.arraycopy(filterF, 0, this.filterF, 0, filterF.length);
        this.filterG = new double[filterG.length];
        System.arraycopy(filterG, 0, this.filterG, 0, filterG.length);
        J = 1;
        double y0[] = convolution(original, filterC);
        double y1[] = convolution(original, filterD);
        double v0[] = downsample(y0);
        double v1[] = downsample(y1);
        double u0[] = upsample(v0);
        double u1[] = upsample(v1);
        double w0[] = convolution(u0, filterF);
        double w1[] = convolution(u1, filterG);
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
        Transform1D transform1D = new Transform1D(
         //         new double[]{-2, 1, 3, 2, -3, 4},
         new double[]{
             0.053767,
              0.379995,
              0.159654,
              0.645638,
              0.743343,
              0.704971,
              0.884030,
              1.017102,
              1.357763,
              1.254920,
              0.782864,
              1.125394,
              0.766408,
              0.532442,
              0.434072,
              0.151798,
              -0.037149,
              -0.071828,
              -0.267339,
              -0.438032,
              -0.661480,
              -0.969815,
              -0.864635,
              -0.824078,
              -0.950422,
              -0.869045,
              -0.835066,
              -0.837905,
              -0.646456,
              -0.596473,
              -0.250596,
              -0.262584,
              -0.057433,
              0.163905,
              0.136269,
              0.743566,
              0.777867,
              0.786380,
              1.081785,
              0.819607,
              0.987864,
              0.942313,
              0.929021,
              0.824011,
              0.570918,
              0.493420,
              0.299578,
              0.186140,
              0.035184,
              -0.157834,
              -0.539253,
              -0.611601,
              -0.883021,
              -0.985503,
              -0.953259,
              -0.840548,
              -1.073221,
              -0.922671,
              -0.908456,
              -0.665670,
              -0.747473,
              -0.471546,
              -0.237250,
              0.011275,
              0.253208,
              0.301096,
              0.325643,
              0.564337,
              0.671247,
              1.120943,
              0.898249,
              1.071062,
              0.974569,
              1.041435,
              0.797668,
              0.621383,
              0.477099,
              0.501708,
              0.251023,
              0.054537,
              0.018561,
              -0.286908,
              -0.476644,
              -0.498636,
              -0.873172,
              -0.827438,
              -0.882948,
              -1.022460,
              -0.969192,
              -1.061340,
              -0.976668,
              -0.734860,
              -0.527502,
              -0.172148,
              -0.311543,
              -0.030721,
              0.139628,
              0.146134,
              0.473848,
              0.496376,
              0.891598,
              0.818951,
              0.982523,
              0.944859,
              1.017453,
              0.876326,
              0.898063,
              0.802566,
              0.750940,
              0.388830,
              0.006962,
              -0.059224,
              -0.036835,
              -0.469814,
              -0.442652,
              -0.681462,
              -0.678232,
              -1.113943,
              -0.997746,
              -1.120708,
              -0.692038,
              -0.844868,
              -0.697842,
              -0.817284,
              -0.606282,
              -0.412785,
              -0.086764,
              -0.027787
         },
         new double[]{x, x},
         new double[]{x, -x},
         new double[]{x, x},
         new double[]{-x, x},
         1);
        transform1D.transformForward();
        transform1D.transformForward();
        transform1D.transformForward();
        transform1D.transformForward();
    }
}
