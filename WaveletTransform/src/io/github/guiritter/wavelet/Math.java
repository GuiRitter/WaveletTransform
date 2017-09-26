package io.github.guiritter.wavelet;

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
}
