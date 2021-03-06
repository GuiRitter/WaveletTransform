package io.github.guiritter.wavelet.gui;

import static io.github.guiritter.wavelet.gui.MainFrame.SPACE_DIMENSION;
import java.awt.event.ActionEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import static javax.swing.BoxLayout.LINE_AXIS;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;

/**
 *
 * @author Guilherme Alan Ritter
 */
public abstract class CommandPanel {

    private final JSpinner levelSpinner;

    final JPanel panel;

    private final JTextField softThresholdField;

    public int getLevel() {
        return (int) levelSpinner.getModel().getValue();
    }

    public String getSoftThreshold() {
        return softThresholdField.getText();
    }

    public final void levelIncrease() {
        ((SpinnerNumberModel) levelSpinner.getModel()).setMaximum(((int) ((SpinnerNumberModel) levelSpinner.getModel()).getMaximum()) + 1);
        levelSpinner.setValue(((SpinnerNumberModel) levelSpinner.getModel()).getMaximum());
    }

    public abstract void onDataButtonPressed(int level);

    public abstract void onImageButtonPressed();

    public abstract void onIncreaseButtonPressed();

    public abstract void onLevelChanged(int level);

    public abstract void onRefreshButtonPressed();

    public CommandPanel(int level) {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, LINE_AXIS));

        JLabel label = new JLabel("Level: ");
        panel.add(label);

        levelSpinner = new JSpinner(new SpinnerNumberModel(level, 0, level, 1));
        levelSpinner.addChangeListener((ChangeEvent e) -> {

            for (StackTraceElement element : (new Throwable()).getStackTrace()) {
                if (element.getMethodName().equals("setMaximum")) {
                    return;
                }
            }
            onLevelChanged(getLevel());
        });
        panel.add(levelSpinner);

        panel.add(Box.createRigidArea(SPACE_DIMENSION));

        JButton button = new JButton("Increase");
        button.addActionListener((ActionEvent e) -> {

            onIncreaseButtonPressed();
        });
        panel.add(button);

        panel.add(Box.createRigidArea(SPACE_DIMENSION));

        label = new JLabel("Threshold: ");
        panel.add(label);

        softThresholdField = new JTextField();
        panel.add(softThresholdField);

        panel.add(Box.createRigidArea(SPACE_DIMENSION));

        button = new JButton("Refresh");
        button.addActionListener((ActionEvent e) -> {

            onRefreshButtonPressed();
        });
        panel.add(button);

        panel.add(Box.createRigidArea(SPACE_DIMENSION));

        button = new JButton("Data");
        button.addActionListener((ActionEvent e) -> {

            onDataButtonPressed((int) levelSpinner.getModel().getValue());
        });
        panel.add(button);

        panel.add(Box.createRigidArea(SPACE_DIMENSION));

        button = new JButton("Image");
        button.addActionListener((ActionEvent e) -> {

            onImageButtonPressed();
        });
        panel.add(button);
    }

    public static void main(String args[]) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
        frame.getContentPane().add((new CommandPanel(0) {

            @Override
            public void onDataButtonPressed(int level) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onImageButtonPressed() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onIncreaseButtonPressed() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onLevelChanged(int level) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onRefreshButtonPressed() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        }).panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
