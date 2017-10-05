package io.github.guiritter.wavelet.gui;

/**
 *
 * @author Guilherme Alan Ritter
 */
public final class FilterItem {

    private final double c[];

    private final double d[];

    private final double f[];

    private final double g[];

    public final String name;

    @Override
    public String toString() {
        return name;
    }

    public FilterItem(String name, double[] c, double[] d, double[] f, double[] g) {
        this.name = name;
        this.c = new double[c.length]; System.arraycopy(c, 0, this.c, 0, c.length);
        this.d = new double[d.length]; System.arraycopy(d, 0, this.d, 0, d.length);
        this.f = new double[f.length]; System.arraycopy(f, 0, this.f, 0, f.length);
        this.g = new double[g.length]; System.arraycopy(g, 0, this.g, 0, g.length);
    }
}
