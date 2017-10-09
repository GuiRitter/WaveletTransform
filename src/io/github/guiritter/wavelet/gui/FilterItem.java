package io.github.guiritter.wavelet.gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

/**
 *
 * @author Guilherme Alan Ritter
 */
public final class FilterItem {

    private final double c[];

    private final double d[];

    private final double f[];

    public static final LinkedList<FilterItem> filterItemList = new LinkedList<>();

    private final double g[];

    public final String name;

    @Override
    public String toString() {
        return name;
    }

    static {
        BufferedReader reader = new BufferedReader(new InputStreamReader(FilterItem.class.getResourceAsStream("filter list.txt")));
        int stateI = 0;
        final int STATE_NAME = stateI++;
        final int STATE_C = stateI++;
        final int STATE_D = stateI++;
        final int STATE_F = stateI++;
        final int STATE_G = stateI++;
        final int STATE_TEST = stateI++;
        int state = STATE_NAME;
        String line = null;
        String name;
        String c;
        String d;
        String f;
        String g;
        while (true) {
            try {
                line = reader.readLine();
            } catch (IOException ex) {}
            if (state == STATE_NAME) {
                name = line;
                state = STATE_C;
            } else if (state == STATE_C) {
                c = line;
                state = STATE_D;
            } else if (state == STATE_D) {
                d = line;
                state = STATE_F;
            } else if (state == STATE_F) {
                f = line;
                state = STATE_G;
            } else if (state == STATE_G) {
                g = line;
                state = STATE_TEST;
                filterItemList.add(new FilterItem(name, c, d, f, g));
            } else if (state == STATE_TEST) {
                if (line == null) {
                    break;
                } else {
                    state = STATE_NAME;
                }
            }
        }
    }

    public FilterItem(String name, double[] c, double[] d, double[] f, double[] g) {
        this.name = name;
        this.c = new double[c.length]; System.arraycopy(c, 0, this.c, 0, c.length);
        this.d = new double[d.length]; System.arraycopy(d, 0, this.d, 0, d.length);
        this.f = new double[f.length]; System.arraycopy(f, 0, this.f, 0, f.length);
        this.g = new double[g.length]; System.arraycopy(g, 0, this.g, 0, g.length);
    }

    public static void main(String args[]) {
        System.out.println("yo");
    }
}
