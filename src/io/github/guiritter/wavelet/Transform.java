package io.github.guiritter.wavelet;

import static io.github.guiritter.wavelet.DoubleMatrixParser.encode;
import static io.github.guiritter.wavelet.gui.MainFrame.showError;
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

    private final TransformFrame frame;

    public final double[][][][][] getInverseView(int J) {
        double componentArray[][][][][] = new double[data.length][][][][];
        for (int i = 0; i < componentArray.length; i++) {
            componentArray[i] = ((Transform2D) data[i]).transformInverse(J);
        }
        return componentArray;
    }

    public Transform(String name, TransformData data[], int J) {
        this.data = Arrays.copyOf(data, data.length);
        frame = new TransformFrame(name, getInverseView(J)) {

            @Override
            public void onDataButtonPressed(int level) {
                String data = encode(getInverseView(level));
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
                levelIncrease();
            }

            @Override
            public void onLevelChanged(int level) {
                setView(getInverseView(level));
            }
        };
    }
}
