package io.github.guiritter.wavelet;

import java.util.stream.Stream;

/**
 *
 * @author Guilherme Alan Ritter
 */
public final class Math {

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

    public static double[] convolution(Double f[], double g[]) {
        return convolution(Stream.of(f).mapToDouble(Double::doubleValue).toArray(), g);
    }

    public static double[] downsample(double x[]) {
        double y[] = new double[x.length / 2];
        for (int i = 0; i < y.length; i++) {
            y[i] = x[(2 * i) + 1];
        }
        return y;
    }

    public static double[] upsample(double x[]) {
        double y[] = new double[2 * x.length];
        for (int i = 0; i < x.length; i++) {
            y[ 2 * i     ] = x[i];
            y[(2 * i) + 1] = 0;
        }
        return y;
    }
}
