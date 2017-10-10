package io.github.guiritter.wavelet.gui;

import io.github.guiritter.imagecomponent.ImageComponent;
import java.awt.Component;
import java.awt.image.BufferedImage;

/**
 *
 * @author Guilherme Alan Ritter
 */
public final class TransformView2D implements TransformView{

    private final ImageComponent imageComponent;

    public TransformView2D(BufferedImage image) {
        this.imageComponent = new ImageComponent(image);
    }

    @Override
    public Component getComponent() {
        return imageComponent;
    }
}
