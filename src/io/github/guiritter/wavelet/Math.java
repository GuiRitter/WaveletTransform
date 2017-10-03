package io.github.guiritter.wavelet;

import static java.lang.Math.ceil;
import static java.lang.Math.max;
import static java.lang.Math.round;
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

    public static double[][] convolutionX(double f[][], double g[]) {
        int N = f[0].length;
        int M = g.length;
        int O = N + M - 1;
        double fog[][] = new double[f.length][O];
        double sum;
        int n;
        int k;
        int o;
        int y;
        if (M < N) {
            for (y = 0; y < f.length; y++) {
                for (n = 0; n < O; n++) {
                    sum = 0;
                    for (k = 0; k < M; k++) {
                        o = n - k;
                        sum += (((o < 0) || (o >= N)) ? 0 : f[y][o]) * g[k];
                    }
                    fog[y][n] = sum;
                }
            }
        } else {
            for (y = 0; y < f.length; y++) {
                for (n = 0; n < O; n++) {
                    sum = 0;
                    for (k = 0; k < N; k++) {
                        o = n - k;
                        sum += (((o < 0) || (o >= M)) ? 0 : g[o]) * f[y][k];
                    }
                    fog[y][n] = sum;
                }
            }
        }
        return fog;
    }

    public static double[][] convolutionY(double f[][], double g[]) {
        int N = f.length;
        int M = g.length;
        int O = N + M - 1;
        double fog[][] = new double[O][f[0].length];
        double sum;
        int n;
        int k;
        int o;
        int x;
        if (M < N) {
            for (n = 0; n < O; n++) {
                for (x = 0; x < f[0].length; x++) {
                    sum = 0;
                    for (k = 0; k < M; k++) {
                        o = n - k;
                        sum += (((o < 0) || (o >= N)) ? 0 : f[o][x]) * g[k];
                    }
                    fog[n][x] = sum;
                }
            }
        } else {
            for (n = 0; n < O; n++) {
                for (x = 0; x < f.length; x++) {
                    sum = 0;
                    for (k = 0; k < N; k++) {
                        o = n - k;
                        sum += (((o < 0) || (o >= M)) ? 0 : g[o]) * f[k][x];
                    }
                    fog[n][x] = sum;
                }
            }
        }
        return fog;
    }

    /**
     * Same as MATLAB's <code>conv(a, b, 'same')</code>.
     * @param f
     * @param g
     * @return
     */
    public static double[] convolutionSame(double f[], double g[]) {
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
        System.arraycopy(fog, (int) round(ceil(((double) (M - 1)) / 2.0)), fogSame, 0, fogSame.length);
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

    public static double[][] downsample(double a[][]) {
        double b[][] = new double[a.length / 2][a[0].length / 2];
        for (int y = 0; y < b.length; y++) {
            for (int x = 0; x < b[y].length; x++) {
                b[y][x] = a[(2 * y) + 1][(2 * x) + 1];
            }
        }
        return b;
    }

    public static double[] removeTrailingFiller(double x[]) {
        double y[] = new double[x.length - 1];
        System.arraycopy(x, 0, y, 0, y.length);
        return y;
    }

    public static double[][] removeTrailingFiller(double a[][], int maximumWidth, int maximumHeight) {
        if ((a.length <= maximumHeight) && (a[0].length) <= maximumWidth) {
            return a;
        }
        double b[][] = new double[maximumHeight][maximumWidth];
        int x;
        int y;
        for (y = 0; y < maximumHeight; y++) {
            for (x = 0; x < maximumWidth; x++) {
                b[y][x] = a[y][x];
            }
        }
        return b;
    }

    public static double[] sum(double a[], double b[]) {
        double c[] = new double[max(a.length, b.length)];
        for (int i = 0; i < c.length; i++) {
            c[i] = a[i] + b[i];
        }
        return c;
    }

    public static double[][] sum(double a[][], double b[][]) {
        double c[][] = new double[max(a.length, b.length)][max(a[0].length, b[0].length)];
        int x;
        int y;
        for (y = 0; y < c.length; y++) {
            for (x = 0; x < c[0].length; x++) {
                c[y][x] = a[y][x] + b[y][x];
            }
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

    public static double[][] upsample(double a[][]) {
        double b[][] = new double[2 * a.length][2 * a[0].length];
        int x;
        int y;
        for (y = 0; y < a.length; y++) {
            for (x = 0; x < a[0].length; x++) {
                b[ 2 * y     ][ 2 * x     ] = a[y][x];
                b[ 2 * y     ][(2 * x) + 1] = 0;
                b[(2 * y) + 1][ 2 * x     ] = 0;
                b[(2 * y) + 1][(2 * x) + 1] = 0;
            }
        }
        return b;
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
                System.out.println(Arrays.toString(convolutionSame(a, b)));
                System.out.println();
            }
        }
    }
}
