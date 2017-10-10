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

    public Transform(TransformData data[]) {
        this.data = Arrays.copyOf(data, data.length);
        frame = null;
    }
}
