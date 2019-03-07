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

    public final String c;

    public final String d;

    public final String f;

    public static final LinkedList<FilterItem> filterItemList = new LinkedList<>();

    public final String g;

    public final String name;

    @Override
    public String toString() {
        return name;
    }

    static {
        BufferedReader reader = new BufferedReader(new InputStreamReader(FilterItem.class.getClassLoader().getResourceAsStream("filter list.txt")));
        int stateI = 0;
        final int STATE_NAME = stateI++;
        final int STATE_C = stateI++;
        final int STATE_D = stateI++;
        final int STATE_F = stateI++;
        final int STATE_G = stateI++;
        final int STATE_TEST = stateI++;
        int state = STATE_NAME;
        String line = null;
        String name = null;
        String c = null;
        String d = null;
        String f = null;
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

    public FilterItem(String name, String c, String d, String f, String g) {
        this.name = name;
        this.c = c;
        this.d = d;
        this.f = f;
        this.g = g;
    }

    public static void main(String args[]) {
        System.out.println(filterItemList.getFirst().name);
        System.out.println(filterItemList.getLast().name);
    }
}
