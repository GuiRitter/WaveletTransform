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

    private final String ERROR_INSTANCE = "Object doesn't represent a valid 1D or 2D transform.";

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

    public int getLevel() {
        return command.getLevel();
    }

    public final String getSoftThreshold() {
        return command.getSoftThreshold();
    }

    public final void levelIncrease() {
        command.levelIncrease();
    }

    private CommandPanel newCommandPanel(int level) {
        return new CommandPanel(level) {

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

            @Override
            public void onRefreshButtonPressed() {
                TransformFrame.this.onRefreshButtonPressed();
            }
        };
    }

    public abstract void onDataButtonPressed(int level);

    public abstract void onImageButtonPressed();

    public abstract void onIncreaseButtonPressed();

    public abstract void onLevelChanged(int level);

    public abstract void onRefreshButtonPressed();

    public void setView(Object matrix) {
        if (matrix instanceof double[][]) {
            view.setView((double[][]) matrix);

        } else if (matrix instanceof double[][][][][]) {
            view.setView((double[][][][][]) matrix);

        } else {
            throw new IllegalArgumentException(ERROR_INSTANCE);
        }
    }

    public TransformFrame(String name, Object doubleMatrix) {
        chooser = new JFileChooser();
        chooser.setFileSelectionMode(FILES_ONLY);
        frame = new JFrame(name);
        int level;

        if (doubleMatrix instanceof double[][]) {
            view = new TransformView1D((double[][]) doubleMatrix);
            level = ((double[][]) doubleMatrix).length - 1;
            /* TODO test custom constructor
            level = 1;
            view = new TransformView1D((double[][]) doubleMatrix, null);
            /**/

        } else if (doubleMatrix instanceof double[][][][][]) {
            view = new TransformView2D((double[][][][][]) doubleMatrix);
            level = ((double[][][][][]) doubleMatrix)[0].length - 1;

        } else {
            throw new IllegalArgumentException(ERROR_INSTANCE);
        }
        command = newCommandPanel(level);
        constructorPostamble(level);
    }
}
