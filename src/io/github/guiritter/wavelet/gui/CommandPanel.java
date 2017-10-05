package io.github.guiritter.wavelet.gui;

import java.awt.event.ActionEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import static javax.swing.BoxLayout.LINE_AXIS;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import static io.github.guiritter.wavelet.gui.GUI.SPACE_DIMENSION;

/**
 *
 * @author Guilherme Alan Ritter
 */
public abstract class CommandPanel {

    final JPanel panel;

    public abstract void onIncreaseButtonPressed();

    public CommandPanel() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, LINE_AXIS));

        JLabel label = new JLabel("level: ");
        panel.add(label);

        JComboBox<Integer> comboBox = new JComboBox<>();
        panel.add(comboBox);

        panel.add(Box.createRigidArea(SPACE_DIMENSION));

        JButton button = new JButton("increase");
        button.addActionListener((ActionEvent e) -> {
            onIncreaseButtonPressed();
        });
        panel.add(button);

        panel.add(Box.createRigidArea(SPACE_DIMENSION));

        label = new JLabel("threshold: ");
        panel.add(label);

        JTextField field = new JTextField();
        panel.add(field);

        panel.add(Box.createRigidArea(SPACE_DIMENSION));

        button = new JButton("refresh");
        panel.add(button);

        panel.add(Box.createRigidArea(SPACE_DIMENSION));

        button = new JButton("data");
        panel.add(button);

        panel.add(Box.createRigidArea(SPACE_DIMENSION));

        button = new JButton("image");
        panel.add(button);
    }

    public static void main(String args[]) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
        frame.getContentPane().add((new CommandPanel() {

            @Override
            public void onIncreaseButtonPressed() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        }).panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
