package io.github.guiritter.wavelet.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import static java.awt.GridBagConstraints.HORIZONTAL;
import static java.awt.GridBagConstraints.VERTICAL;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Guilherme Alan Ritter
 */
public abstract class GUI {

    final JPanel panel;

    public static final int SPACE_INT;

    public static final Dimension SPACE_DIMENSION;

    public static final int SPACE_HALF_INT;

    public static final Dimension SPACE_HALF_DIMENSION;

    static {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);

        JLabel label = new JLabel("—");
        {
            SPACE_INT = Math.min(
             label.getPreferredSize().width,
             label.getPreferredSize().height);
            SPACE_HALF_INT = SPACE_INT / 2;
            SPACE_DIMENSION = new Dimension(SPACE_INT, SPACE_INT);
            SPACE_HALF_DIMENSION = new Dimension(SPACE_HALF_INT, SPACE_HALF_INT);
        }
    }

    public GUI() {
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints;

        JButton dataButton = new JButton();
        dataButton.setText("Data");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = VERTICAL;
        gridBagConstraints.insets = new Insets(SPACE_INT, SPACE_INT, SPACE_HALF_INT, SPACE_HALF_INT);
        panel.add(dataButton, gridBagConstraints);

        JButton imageButton = new JButton();
        imageButton.setText("Image");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = VERTICAL;
        gridBagConstraints.insets = new Insets(SPACE_INT, SPACE_HALF_INT, SPACE_HALF_INT, SPACE_HALF_INT);
        panel.add(imageButton, gridBagConstraints);

        JComboBox<FilterItem> filterComboBox = new JComboBox<>();
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = VERTICAL;
        gridBagConstraints.insets = new Insets(SPACE_INT, 0, SPACE_HALF_INT, SPACE_HALF_INT);
        panel.add(filterComboBox, gridBagConstraints);

        JLabel filterLabel = new JLabel();
        filterLabel.setText("Filter: ");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = VERTICAL;
        gridBagConstraints.insets = new Insets(SPACE_INT, SPACE_HALF_INT, SPACE_HALF_INT, 0);
        panel.add(filterLabel, gridBagConstraints);

        JTextField levelFilter = new JTextField();
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = VERTICAL;
        gridBagConstraints.insets = new Insets(SPACE_INT, 0, SPACE_HALF_INT, SPACE_INT);
        panel.add(levelFilter, gridBagConstraints);

        JLabel levelLabel = new JLabel();
        levelLabel.setText("Level: ");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = VERTICAL;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.insets = new Insets(SPACE_INT, SPACE_HALF_INT, SPACE_HALF_INT, 0);
        panel.add(levelLabel, gridBagConstraints);

        JLabel fLabel = new JLabel();
        fLabel.setText("F:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(SPACE_HALF_INT, SPACE_INT, 0, SPACE_INT);
        panel.add(fLabel, gridBagConstraints);

        JTextField fField = new JTextField();
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = HORIZONTAL;
        gridBagConstraints.insets = new Insets(0, SPACE_INT, SPACE_HALF_INT, SPACE_INT);
        panel.add(fField, gridBagConstraints);

        JLabel gLabel = new JLabel();
        gLabel.setText("G:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(SPACE_HALF_INT, SPACE_INT, 0, SPACE_INT);
        panel.add(gLabel, gridBagConstraints);

        JTextField gField = new JTextField();
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = HORIZONTAL;
        gridBagConstraints.insets = new Insets(0, SPACE_INT, SPACE_INT, SPACE_INT);
        panel.add(gField, gridBagConstraints);

        JLabel cLabel = new JLabel();
        cLabel.setText("C:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(SPACE_HALF_INT, SPACE_INT, 0, SPACE_INT);
        panel.add(cLabel, gridBagConstraints);

        JTextField cField = new JTextField();
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = HORIZONTAL;
        gridBagConstraints.insets = new Insets(0, SPACE_INT, SPACE_HALF_INT, SPACE_INT);
        panel.add(cField, gridBagConstraints);

        JLabel dLabel = new JLabel();
        dLabel.setText("D:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(SPACE_HALF_INT, SPACE_INT, 0, SPACE_INT);
        panel.add(dLabel, gridBagConstraints);

        JTextField dField = new JTextField();
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = HORIZONTAL;
        gridBagConstraints.insets = new Insets(0, SPACE_INT, SPACE_HALF_INT, SPACE_INT);
        panel.add(dField, gridBagConstraints);
    }

    public static void main(String args[]) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
        frame.getContentPane().add((new GUI() {}).panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
