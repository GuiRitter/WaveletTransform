package io.github.guiritter.wavelet;

import io.github.guiritter.wavelet.gui.TransformFrame;
import java.util.Arrays;

/**
 *
 * @author Guilherme Alan Ritter
 */
public final class Transform {

    private final TransformData data[];

    private final TransformFrame frame;

    public Transform(TransformData data[], int J) {
        this.data = Arrays.copyOf(data, data.length);
        if (data[0] instanceof Transform2D) {
            double componentArray[][][][][] = new double[data.length][][][][];
            for (int i = 0; i < componentArray.length; i++) {
                componentArray[i] = ((Transform2D) data[i]).transformInverse(J);
            }
            frame = new TransformFrame(componentArray);
        } else {
            frame = null;
        }
    }
}
