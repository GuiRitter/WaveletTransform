package io.github.guiritter.wavelet;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 *
 * @author Guilherme Alan Ritter
 */
public final class DoubleMatrixParser {

    public static String encode(double matrix[]) {
        return Arrays.toString(matrix).replace("[", "").replace("]", "").replace(",", "");
    }

    public static String encode(double matrix[][]) {
        StringBuilder builder = new StringBuilder();
        for (double[] array : matrix) {
            builder.append(encode(array)).append("A");
        }
        builder.setLength(builder.length() - 1);
        return builder.toString();
    }

    public static String encode(double matrix[][][]) {
        StringBuilder builder = new StringBuilder();
        for (double[][] array : matrix) {
            builder.append(encode(array)).append("B");
        }
        builder.setLength(builder.length() - 1);
        return builder.toString();
    }

    public static String encode(double matrix[][][][]) {
        StringBuilder builder = new StringBuilder();
        for (double[][][] array : matrix) {
            builder.append(encode(array)).append("C");
        }
        builder.setLength(builder.length() - 1);
        return builder.toString();
    }

    public static String encode(double matrix[][][][][]) {
        StringBuilder builder = new StringBuilder();
        for (double[][][][] array : matrix) {
            builder.append(encode(array)).append("D");
        }
        builder.setLength(builder.length() - 1);
        return builder.toString();
    }

    public static double[] decode1D(String string) {
        return decode1D(string.split(" "));
    }

    public static double[] decode1D(String stringArray[]) {
        return Stream.of(stringArray).mapToDouble(Double::parseDouble).toArray();
    }

    public static double[][] decode2D(String string) {
        String fields[] = string.split("A");
        double matrix[][] = new double[fields.length][];
        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = decode1D(fields[i]);
        }
        return matrix;
    }

    public static double[][][] decode3D(String string) {
        String fields[] = string.split("B");
        double matrix[][][] = new double[fields.length][][];
        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = decode2D(fields[i]);
        }
        return matrix;
    }

    public static double[][][][] decode4D(String string) {
        String fields[] = string.split("C");
        double matrix[][][][] = new double[fields.length][][][];
        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = decode3D(fields[i]);
        }
        return matrix;
    }

    public static double[][][][][] decode5D(String string) {
        String fields[] = string.split("D");
        double matrix[][][][][] = new double[fields.length][][][][];
        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = decode4D(fields[i]);
        }
        return matrix;
    }

    // skip E because of exponent format
}
