package io.github.guiritter.wavelet.gui;

import static io.github.guiritter.wavelet.gui.MainFrame.SPACE_DIMENSION;
import javax.swing.Box;
import javax.swing.BoxLayout;
import static javax.swing.BoxLayout.LINE_AXIS;
import static javax.swing.BoxLayout.PAGE_AXIS;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 *
 * @author Guilherme Alan Ritter
 */
public abstract class TransformFrame {

    private final JFrame frame;

    private final TransformView view;

    public abstract void onLevelChanged(int level);

    private void constructorPostamble(int level) {
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), LINE_AXIS));

        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel, PAGE_AXIS));
        JScrollPane scrollPane = new JScrollPane(view.getComponent());

        frame.getContentPane().add(Box.createRigidArea(SPACE_DIMENSION));
        frame.getContentPane().add(innerPanel);
        frame.getContentPane().add(Box.createRigidArea(SPACE_DIMENSION));

        innerPanel.add(Box.createRigidArea(SPACE_DIMENSION));
        innerPanel.add(scrollPane);
        innerPanel.add(Box.createRigidArea(SPACE_DIMENSION));
        innerPanel.add((new CommandPanel(level) {

            @Override
            public void onIncreaseButtonPressed() {}

            @Override
            public void onLevelChanged(int level) {
                TransformFrame.this.onLevelChanged(level);
            }
        }).panel);
        innerPanel.add(Box.createRigidArea(SPACE_DIMENSION));

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public TransformFrame(double componentArray[][][][][]) {
        frame = new JFrame();
        view = new TransformView2D(componentArray);
        constructorPostamble(componentArray[0].length - 1);
    }
}
