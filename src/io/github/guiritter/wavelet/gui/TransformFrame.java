package io.github.guiritter.wavelet.gui;

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

    private final JFrame frame;

    private final TransformView view;

    public TransformFrame(Object data) {
        frame = new JFrame();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), LINE_AXIS));

        if (data instanceof BufferedImage) {
            view = new TransformView2D((BufferedImage) data);
        } else {
            view = null;
        }
        frame.getContentPane().add(view.getComponent());

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
