package io.github.guiritter.wavelet.gui;

import javax.swing.BoxLayout;
import static javax.swing.BoxLayout.LINE_AXIS;
import javax.swing.JFrame;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 *
 * @author Guilherme Alan Ritter
 */
public class TransformFrame {

    private final JFrame frame;

    private final TransformView view;

    public TransformFrame(Object data) {
        frame = new JFrame();
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), LINE_AXIS));

        if (data instanceof double[][][][][]) {
            view = new TransformView2D((double[][][][][]) data);
        } else {
            view = null;
        }
        frame.getContentPane().add(view.getComponent());

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
