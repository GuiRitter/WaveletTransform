package io.github.guiritter.wavelet.gui;

import static io.github.guiritter.wavelet.gui.MainFrame.SPACE_DIMENSION;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.Box;
import javax.swing.BoxLayout;
import static javax.swing.BoxLayout.LINE_AXIS;
import static javax.swing.BoxLayout.PAGE_AXIS;
import javax.swing.JFileChooser;
import static javax.swing.JFileChooser.FILES_ONLY;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 *
 * @author Guilherme Alan Ritter
 */
public abstract class TransformFrame {

    protected final JFrame frame;

    private final JFileChooser chooser;

    private final CommandPanel command;

    private final TransformView view;

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

        innerPanel.add(command.panel);
        innerPanel.add(Box.createRigidArea(SPACE_DIMENSION));

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public final File fileSave() {
        chooser.setSelectedFile(null);
        if (chooser.showSaveDialog(frame) != JFileChooser.APPROVE_OPTION) {
            return null;
        }
        return chooser.getSelectedFile();
    }

    public final BufferedImage getImage() {
        return view.getImage();
    }

    public final void levelIncrease() {
        command.levelIncrease();
    }

    public abstract void onDataButtonPressed(int level);

    public abstract void onImageButtonPressed();

    public abstract void onIncreaseButtonPressed();

    public abstract void onLevelChanged(int level);

    public void setView(double componentArray[][][][][]) {
        view.setView(componentArray);
    }

    public TransformFrame(String name, double componentArray[][][][][]) {
        chooser = new JFileChooser();
        chooser.setFileSelectionMode(FILES_ONLY);
        frame = new JFrame(name);
        view = new TransformView2D(componentArray);

        int level = componentArray[0].length - 1;

        command = new CommandPanel(level) {

            @Override
            public void onDataButtonPressed(int level) {
                TransformFrame.this.onDataButtonPressed(level);
            }

            @Override
            public void onImageButtonPressed() {
                TransformFrame.this.onImageButtonPressed();
            }

            @Override
            public void onIncreaseButtonPressed() {
                TransformFrame.this.onIncreaseButtonPressed();
            }

            @Override
            public void onLevelChanged(int level) {
                TransformFrame.this.onLevelChanged(level);
            }
        };

        constructorPostamble(level);
    }
}
