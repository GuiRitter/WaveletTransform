package io.github.guiritter.wavelet.gui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;

public final class wfilters {

    public static void main(String args[]) throws IOException {
        List<String> lineList = Files.readAllLines(Paths.get("/home/guir/NetBeansProjects/WaveletTransform/src/io/github/guiritter/wavelet/gui/filters.txt"));
        int i = 0;
        LinkedList<Double> filterArray[] = new LinkedList[4];
        filterArray[0] = new LinkedList<>();
        filterArray[1] = new LinkedList<>();
        filterArray[2] = new LinkedList<>();
        filterArray[3] = new LinkedList<>();
        String fieldArray[];
        for (String line : lineList) {
            if (line.contains("wfilters")) {
                System.out.println(Arrays.toString(filterArray[0].toArray()).replace("[", "").replace("]", "").replace(",", ""));
                System.out.println(Arrays.toString(filterArray[1].toArray()).replace("[", "").replace("]", "").replace(",", ""));
                System.out.println(Arrays.toString(filterArray[2].toArray()).replace("[", "").replace("]", "").replace(",", ""));
                System.out.println(Arrays.toString(filterArray[3].toArray()).replace("[", "").replace("]", "").replace(",", ""));
                filterArray[0].clear();
                filterArray[1].clear();
                filterArray[2].clear();
                filterArray[3].clear();
                System.out.println("\n" + line.split("'")[1]);
                continue;
            }
            if (line.contains("Columns") || (line.trim().isEmpty())) {
                continue;
            }
            if (line.contains("c =")) {
                i = 0;
                continue;
            } else if (line.contains("d =")) {
                i = 1;
                continue;
            } else if (line.contains("f =")) {
                i = 2;
                continue;
            } else if (line.contains("g =")) {
                i = 3;
                continue;
            }
            fieldArray = line.split(" ");
            for (String field : fieldArray) {
                try {
                    filterArray[i].add(Double.parseDouble(field));
                } catch (Exception ex) {}
            }
        }
        System.out.println(Arrays.toString(filterArray[0].toArray()).replace("[", "").replace("]", "").replace(",", ""));
        System.out.println(Arrays.toString(filterArray[1].toArray()).replace("[", "").replace("]", "").replace(",", ""));
        System.out.println(Arrays.toString(filterArray[2].toArray()).replace("[", "").replace("]", "").replace(",", ""));
        System.out.println(Arrays.toString(filterArray[3].toArray()).replace("[", "").replace("]", "").replace(",", ""));
        JOptionPane.showMessageDialog(null, "");
    }
}
