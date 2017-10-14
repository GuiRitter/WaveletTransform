package io.github.guiritter.wavelet.gui;

import static io.github.guiritter.wavelet.gui.FilterItem.filterItemList;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import static java.awt.GridBagConstraints.BOTH;
import static java.awt.GridBagConstraints.HORIZONTAL;
import static java.awt.GridBagConstraints.VERTICAL;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import static javax.swing.JFileChooser.FILES_ONLY;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author Guilherme Alan Ritter
 */
@SuppressWarnings("CallToPrintStackTrace")
public abstract class MainFrame {

    private final JTextField cField;

    private final JFileChooser chooser;

    private final JTextField dField;

    private final JTextField fField;

    protected final JFrame frame;

    private final JTextField gField;

    private final JSpinner levelSpinner;

    public static final int SPACE_INT;

    public static final Dimension SPACE_DIMENSION;

    public static final int SPACE_HALF_INT;

    public static final Dimension SPACE_HALF_DIMENSION;

    public abstract void onDataButtonPressed();

    public abstract void onImageButtonPressed();

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

    public final File[] fileOpen() {
        chooser.setSelectedFiles(null);
        if (chooser.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION) {
            return null;
        }
        return chooser.getSelectedFiles();
    }

    public final String getFilterC() {
        return cField.getText();
    }

    public final String getFilterD() {
        return dField.getText();
    }

    public final String getFilterF() {
        return fField.getText();
    }

    public final String getFilterG() {
        return gField.getText();
    }

    public final int getLevel() {
        return (int) levelSpinner.getModel().getValue();
    }

    public static final void showDialog(Component parent, String message, Exception ex, int messageType) {
        if (ex != null) {
            ex.printStackTrace();
        }
        JOptionPane.showMessageDialog(parent, message, "Error", messageType);
    }

    public static final void showError(Component parent, String message, Exception ex) {
        showDialog(parent, message, ex, ERROR_MESSAGE);
    }

    public static final void showWarning(Component parent, String message, Exception ex) {
        showDialog(parent, message, ex, WARNING_MESSAGE);
    }

    public MainFrame() {
        chooser = new JFileChooser();
        chooser.setFileSelectionMode(FILES_ONLY);
        chooser.setMultiSelectionEnabled(true);

        frame = new JFrame("Wavelet Transform");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints;

        JButton dataButton = new JButton("Data");
        dataButton.addActionListener((ActionEvent e) -> {

            onDataButtonPressed();
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = VERTICAL;
        gridBagConstraints.insets = new Insets(SPACE_INT, SPACE_INT, SPACE_HALF_INT, SPACE_HALF_INT);
        frame.getContentPane().add(dataButton, gridBagConstraints);

        JButton imageButton = new JButton("Image");
        imageButton.addActionListener((ActionEvent e) -> {

            onImageButtonPressed();
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = VERTICAL;
        gridBagConstraints.insets = new Insets(SPACE_INT, SPACE_HALF_INT, SPACE_HALF_INT, SPACE_HALF_INT);
        frame.getContentPane().add(imageButton, gridBagConstraints);

        cField = new JTextField(filterItemList.getFirst().c);
        dField = new JTextField(filterItemList.getFirst().d);
        fField = new JTextField(filterItemList.getFirst().f);
        gField = new JTextField(filterItemList.getFirst().g);

        JComboBox<FilterItem> filterComboBox = new JComboBox<>();
        filterItemList.forEach((item) -> {
            filterComboBox.addItem(item);
        });
        filterComboBox.addItemListener((ItemEvent e) -> {

            cField.setText(filterComboBox.getItemAt(filterComboBox.getSelectedIndex()).c);
            dField.setText(filterComboBox.getItemAt(filterComboBox.getSelectedIndex()).d);
            fField.setText(filterComboBox.getItemAt(filterComboBox.getSelectedIndex()).f);
            gField.setText(filterComboBox.getItemAt(filterComboBox.getSelectedIndex()).g);
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = VERTICAL;
        gridBagConstraints.insets = new Insets(SPACE_INT, 0, SPACE_HALF_INT, SPACE_HALF_INT);
        frame.getContentPane().add(filterComboBox, gridBagConstraints);

        JLabel filterLabel = new JLabel();
        filterLabel.setText("Filter: ");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = VERTICAL;
        gridBagConstraints.insets = new Insets(SPACE_INT, SPACE_HALF_INT, SPACE_HALF_INT, 0);
        frame.getContentPane().add(filterLabel, gridBagConstraints);

        levelSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 127, 1));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = BOTH;
        gridBagConstraints.insets = new Insets(SPACE_INT, 0, SPACE_HALF_INT, SPACE_INT);
        gridBagConstraints.weightx = 1;
        frame.getContentPane().add(levelSpinner, gridBagConstraints);

        JLabel levelLabel = new JLabel();
        levelLabel.setText("Level: ");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = VERTICAL;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.insets = new Insets(SPACE_INT, SPACE_HALF_INT, SPACE_HALF_INT, 0);
        frame.getContentPane().add(levelLabel, gridBagConstraints);

        JLabel fLabel = new JLabel();
        fLabel.setText("F:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(SPACE_HALF_INT, SPACE_INT, 0, SPACE_INT);
        gridBagConstraints.weightx = 1;
        frame.getContentPane().add(fLabel, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = HORIZONTAL;
        gridBagConstraints.insets = new Insets(0, SPACE_INT, SPACE_HALF_INT, SPACE_INT);
        gridBagConstraints.weightx = 1;
        frame.getContentPane().add(fField, gridBagConstraints);

        JLabel gLabel = new JLabel();
        gLabel.setText("G:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(SPACE_HALF_INT, SPACE_INT, 0, SPACE_INT);
        gridBagConstraints.weightx = 1;
        frame.getContentPane().add(gLabel, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = HORIZONTAL;
        gridBagConstraints.insets = new Insets(0, SPACE_INT, SPACE_INT, SPACE_INT);
        gridBagConstraints.weightx = 1;
        frame.getContentPane().add(gField, gridBagConstraints);

        JLabel cLabel = new JLabel();
        cLabel.setText("C:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(SPACE_HALF_INT, SPACE_INT, 0, SPACE_INT);
        gridBagConstraints.weightx = 1;
        frame.getContentPane().add(cLabel, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = HORIZONTAL;
        gridBagConstraints.insets = new Insets(0, SPACE_INT, SPACE_HALF_INT, SPACE_INT);
        gridBagConstraints.weightx = 1;
        frame.getContentPane().add(cField, gridBagConstraints);

        JLabel dLabel = new JLabel();
        dLabel.setText("D:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(SPACE_HALF_INT, SPACE_INT, 0, SPACE_INT);
        gridBagConstraints.weightx = 1;
        frame.getContentPane().add(dLabel, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = HORIZONTAL;
        gridBagConstraints.insets = new Insets(0, SPACE_INT, SPACE_HALF_INT, SPACE_INT);
        gridBagConstraints.weightx = 1;
        frame.getContentPane().add(dField, gridBagConstraints);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
