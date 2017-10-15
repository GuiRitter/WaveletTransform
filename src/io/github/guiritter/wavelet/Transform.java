package io.github.guiritter.wavelet;

import static io.github.guiritter.wavelet.DoubleMatrixParser.encode;
import static io.github.guiritter.wavelet.Main.getJMaximumWarning;
import static io.github.guiritter.wavelet.gui.MainFrame.showError;
import static io.github.guiritter.wavelet.gui.MainFrame.showWarning;
import io.github.guiritter.wavelet.gui.TransformFrame;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import java.util.Arrays;
import javax.imageio.ImageIO;

/**
 *
 * @author Guilherme Alan Ritter
 */
public final class Transform {

    private final TransformData data[];

    private final boolean dimension1;

    private final TransformFrame frame;

    public final double[][] getInverseView1D(int J, Double softThreshold) {
        return ((Transform1D) data[0]).transformInverse(J, softThreshold);
        /* TODO test array with different soft threshold values
        double returnArray[][] = new double[11][];
        returnArray[ 0] = ((Transform1D) data[0]).transformInverse(0, 0.0d)[0];
        returnArray[ 1] = ((Transform1D) data[0]).transformInverse(0, 0.1d)[0];
        returnArray[ 2] = ((Transform1D) data[0]).transformInverse(0, 0.2d)[0];
        returnArray[ 3] = ((Transform1D) data[0]).transformInverse(0, 0.3d)[0];
        returnArray[ 4] = ((Transform1D) data[0]).transformInverse(0, 0.4d)[0];
        returnArray[ 5] = ((Transform1D) data[0]).transformInverse(0, 0.5d)[0];
        returnArray[ 6] = ((Transform1D) data[0]).transformInverse(0, 0.6d)[0];
        returnArray[ 7] = ((Transform1D) data[0]).transformInverse(0, 0.7d)[0];
        returnArray[ 8] = ((Transform1D) data[0]).transformInverse(0, 0.8d)[0];
        returnArray[ 9] = ((Transform1D) data[0]).transformInverse(0, 0.9d)[0];
        returnArray[10] = ((Transform1D) data[0]).transformInverse(0, 1.00d)[0];
        return returnArray;
        /**/
    }

    public final double[][][][][] getInverseView2D(int J, Double softThreshold) {
        double componentArray[][][][][] = new double[data.length][][][][];
        for (int i = 0; i < componentArray.length; i++) {
            componentArray[i] = ((Transform2D) data[i]).transformInverse(J, softThreshold);
        }
        return componentArray;
    }

    private Double getSoftThreshold() {
        String softThresholdString = frame.getSoftThreshold().trim();
        if (softThresholdString.isEmpty()) {
            return null;
        }
        Double softThresholdDouble = Double.parseDouble(softThresholdString);
        if (softThresholdDouble == 0d) {
            return null;
        }
        return softThresholdDouble;
    }

    public Transform(String name, TransformData data[], int J) {
        this.data = Arrays.copyOf(data, data.length);
        dimension1 = data[0] instanceof Transform1D;
        frame = new TransformFrame(name, dimension1 ? getInverseView1D(J, null) : getInverseView2D(J, null)) {

            @Override
            public void onDataButtonPressed(int level) {
                Double softThreshold = Transform.this.getSoftThreshold();
                String data = encode(dimension1 ? getInverseView1D(level, softThreshold) : getInverseView2D(level, softThreshold));
                File file = fileSave();
                if (file == null) {
                    return;
                }
                try (BufferedWriter writer = Files.newBufferedWriter(file.toPath(), CREATE, TRUNCATE_EXISTING)) {
                    writer.write(data);
                    writer.flush();
                } catch (IOException ex) {
                    showError(frame, ex.getLocalizedMessage(), ex);
                }
            }

            @Override
            public void onImageButtonPressed() {
                BufferedImage image = getImage();
                File file = fileSave();
                if (file == null) {
                    return;
                }
                try {
                    ImageIO.write(image, "png", file);
                } catch (IOException ex) {
                    showError(frame, ex.getLocalizedMessage(), ex);
                }
            }

            @Override
            public void onIncreaseButtonPressed() {
                if (data[0].getJ() == data[0].getJMaximum()) {
                    showWarning(frame, getJMaximumWarning(data[0].getJ() + data[0].getJMaximum(), 0), null);
                } else {
                    levelIncrease();
                }
            }

            @Override
            public void onLevelChanged(int level) {
                Double softThreshold = Transform.this.getSoftThreshold();
                setView(dimension1 ? getInverseView1D(level, softThreshold) : getInverseView2D(level, softThreshold));
            }

            @Override
            public void onRefreshButtonPressed() {
                Double softThreshold = Transform.this.getSoftThreshold();
                int level = getLevel();
                setView(dimension1 ? getInverseView1D(level, softThreshold) : getInverseView2D(level, softThreshold));
            }
        };
    }
}
