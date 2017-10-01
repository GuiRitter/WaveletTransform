package io.github.guiritter.wavelet;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 *
 * @author Guilherme Alan Ritter
 */
public final class Math {

    public static Double[] box(double unboxed[]) {
        return Arrays.stream(unboxed).boxed().toArray(Double[]::new);
    }

    public static double[] convolution(double f[], double g[]) {
        int N = f.length;
        int M = g.length;
        int O = N + M - 1;
        double fog[] = new double[O];
        double sum;
        int n;
        int k;
        int o;
        if (M < N) {
            for (n = 0; n < O; n++) {
                sum = 0;
                for (k = 0; k < M; k++) {
                    o = n - k;
                    sum += (((o < 0) || (o >= N)) ? 0 : f[o]) * g[k];
                }
                fog[n] = sum;
            }
        } else {
            for (n = 0; n < O; n++) {
                sum = 0;
                for (k = 0; k < N; k++) {
                    o = n - k;
                    sum += (((o < 0) || (o >= M)) ? 0 : g[o]) * f[k];
                }
                fog[n] = sum;
            }
        }
        return fog;
    }

    public static double[] convolutionNew(double f[], double g[]) {
        int N = f.length;
        int M = g.length;
        int O = N + M - 1;
        double fog[] = new double[O];
        double sum;
        int n;
        int k;
        int o;
        if (M < N) {
            for (n = 0; n < O; n++) {
                sum = 0;
                for (k = 0; k < M; k++) {
                    o = n - k;
                    sum += (((o < 0) || (o >= N)) ? 0 : f[o]) * g[k];
                }
                fog[n] = sum;
            }
        } else {
            for (n = 0; n < O; n++) {
                sum = 0;
                for (k = 0; k < N; k++) {
                    o = n - k;
                    sum += (((o < 0) || (o >= M)) ? 0 : g[o]) * f[k];
                }
                fog[n] = sum;
            }
        }
        double fogSame[] = new double[f.length];
        System.arraycopy(fog, (int) java.lang.Math.round(java.lang.Math.ceil(((double) (M - 1)) / 2.0)), fogSame, 0, fogSame.length);
        return fogSame;
    }

    public static double[] divide(double a, double b[]) {
        double c[] = new double[b.length];
        for (int i = 0; i < c.length; i++) {
            c[i] = a / b[i];
        }
        return c;
    }

    public static double[] downsample(double x[]) {
        double y[] = new double[x.length / 2];
        for (int i = 0; i < y.length; i++) {
            y[i] = x[(2 * i) + 1];
        }
        return y;
    }

    public static double[] removeTrailingFiller(double x[]) {
        double y[] = new double[x.length - 1];
        System.arraycopy(x, 0, y, 0, y.length);
        return y;
    }

    public static double[] sum(double a[], double b[]) {
        double c[] = new double[java.lang.Math.max(a.length, b.length)];
        for (int i = 0; i < c.length; i++) {
            c[i] = a[i] + b[i];
        }
        return c;
    }

    public static double[] unbox(Double boxed[]) {
        return Stream.of(boxed).mapToDouble(Double::doubleValue).toArray();
    }

    public static double[] upsample(double x[]) {
        double y[] = new double[2 * x.length];
        for (int i = 0; i < x.length; i++) {
            y[ 2 * i     ] = x[i];
            y[(2 * i) + 1] = 0;
        }
        return y;
    }

    public static void main(String args[]) {
        double p[] = new double[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37};
        int n = 6;
        int i, j;
        double a[];
        double b[];
        int lengthStart;
        int lengthEnd;
        for (i = 2; i <= n; i++) {
            for (j = 2; j <= n; j++) {
                a = new double[i];
                System.arraycopy(p, 0, a, 0, a.length);
                lengthStart = (2 * n) - j + 1;
                lengthEnd = (2 * n);
                b = new double[lengthEnd - lengthStart + 1];
                System.arraycopy(p, lengthStart - 1, b, 0, b.length);
                b = divide(1, b);
                System.out.println(i + "\t" + j);
                System.out.println(Arrays.toString(convolution(a, b)));
                System.out.println(Arrays.toString(convolutionNew(a, b)));
                System.out.println();
            }
        }
    }
}
