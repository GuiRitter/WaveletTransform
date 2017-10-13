package io.github.guiritter.wavelet.gui;

import java.awt.Component;
import java.awt.image.BufferedImage;

public interface TransformView {

    public Component getComponent();

    public BufferedImage getImage();

    public void setView(double[][] signalArray);

    public void setView(double componentArray[][][][][]);
}
