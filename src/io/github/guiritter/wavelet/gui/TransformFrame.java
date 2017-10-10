package io.github.guiritter.wavelet.gui;

import io.github.guiritter.imagecomponent.ImageComponent;
import java.awt.image.BufferedImage;
import javax.swing.BoxLayout;
import static javax.swing.BoxLayout.LINE_AXIS;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 *
 * @author Guilherme Alan Ritter
 */
public class TransformFrame {

    final JFrame frame;

    public TransformFrame(BufferedImage image) {
        frame = new JFrame();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), LINE_AXIS));

        ImageComponent imageComponent = new ImageComponent(image);
        frame.getContentPane().add(imageComponent);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
