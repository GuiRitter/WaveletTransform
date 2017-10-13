package io.github.guiritter.wavelet.gui;

import static io.github.guiritter.wavelet.gui.MainFrame.SPACE_DIMENSION;
import java.awt.Component;
import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import java.io.IOException;
import static javax.swing.Box.createRigidArea;
import javax.swing.BoxLayout;
import static javax.swing.BoxLayout.PAGE_AXIS;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import static org.jfree.chart.plot.PlotOrientation.VERTICAL;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public final class TransformView1D implements TransformView{

    private final JPanel panel;

    @Override
    public Component getComponent() {
        return panel;
    }

    @Override
    public BufferedImage getImage() {
        BufferedImage image = new BufferedImage(panel.getWidth(), panel.getHeight(), TYPE_INT_ARGB);
        panel.paint(image.getGraphics());
        return image;
    }

    @Override
    public void setView(double[][] signalArray) {
        panel.removeAll();
        XYSeries series;
        XYSeriesCollection collection;
        JFreeChart chart;
        ChartPanel chartPanel;
        int j;
        for (int i = 0; i < signalArray.length; i++) {
            series = new XYSeries(i == 0 ? "Smooth" : ("Detail " + ((signalArray.length) - i)));
            for (j = 0; j < signalArray[i].length; j++) {
                series.add(j, signalArray[i][j]);
            }
            collection = new XYSeriesCollection(series);
            chart = ChartFactory.createXYLineChart(series.getKey().toString(), "samples", "value", collection, VERTICAL, false, false, false);
            chartPanel = new ChartPanel(chart);
            chartPanel.setFillZoomRectangle(true);
            chartPanel.setMouseWheelEnabled(true);
            panel.add(chartPanel);
            if (i < (signalArray.length - 1)) {
                panel.add(createRigidArea(SPACE_DIMENSION));
            }
        }
    }

    @Override
    public void setView(double[][][][][] componentArray) {
        throw new UnsupportedOperationException("Not supported for this version.");
    }

    public TransformView1D(double signalArray[][]) {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, PAGE_AXIS));
        setView(signalArray);
    }

    public static void main(String args[]) throws IOException, InterruptedException {
        double data[][] = new double[][]{{2.7485402499999987, -2.345725749999999, 2.4213027499999993, -2.487711749999999, 2.481301749999999, -2.408404749999999, 2.393958249999999, -2.4317604999999993}, {0.4542902499999999, -0.27854774999999976, 0.1953297500000004, -0.019794250000000346, -0.12159074999999975, 0.016417749999999787, -0.05300024999999975, 0.33893550000000006}, {0.7461362470809199, -0.9320448729031903, -0.8858845886739422, 0.6138850051354281, 0.8765762350064021, -0.7541834758042136, -0.8445778853907581, 0.7513094402920808, 0.8192599270903739, -0.7603830345082665, -0.8352313479570341, 0.9546457733968656, 0.8421447309566952, -0.8863229948782778, -0.8060543543983245, 0.6782617742616044}, {0.18576499999999999, 0.22640899999999975, -0.35221250000000004, -0.35649, -0.29819699999999993, -0.028709000000000096, 0.07324799999999998, 0.36487450000000005, 0.38668149999999984, 0.16857250000000001, -0.08857249999999983, -0.2893100000000007, -0.5141019999999998, 0.03735849999999996, 0.21088299999999982, 0.49652199999999996, 0.167838, 0.08856049999999993, -0.2984764999999999, -0.3366235, -0.3534664999999999, -0.1023989999999999, 0.15950199999999992, 0.17869299999999996, 0.3423099999999995, 0.10841650000000003, -0.09657499999999986, -0.596016, -0.30873249999999997, -0.16313950000000021, 0.010890000000000066, 0.452258}, {0.23067803101292503, 0.3436425819481631, -0.027133101407690208, 0.09409611358605618, -0.0727209826975681, 0.24220528575982814, -0.1654389451670918, -0.19959785955265152, -0.024521756064768283, -0.12069817780107536, -0.21802576937715407, 0.08678129724582835, 0.0575422285326177, -0.0020074761517886452, 0.035343318244047184, -0.008476796092864336, 0.15650960073426803, 0.4294238268942467, 0.006019600028240979, -0.18538784167792666, -0.032209420989828375, -0.07425328309239931, -0.0479936132839508, -0.08021277904423957, -0.13648433669106502, -0.05115776140528433, -0.07246571714955985, 0.07969871241431703, 0.10645492590763461, 0.17167562697715716, 0.19510985281246052, 0.17573371279438668, 0.033861929537461394, 0.1687821602854175, 0.3179830910724657, 0.1221972441771908, 0.047281402030819764, -0.12465231892147055, 0.017401190778219777, -0.13893658300821995, -0.21599920134227346, -0.015550692331854576, 0.03233882153078549, -0.09864988125689755, -0.065158475672778, 0.1709807654515668, 0.2512732231217644, 0.19857114050636865, 0.0046004367183996725, 0.01592970156657053, -0.05136918633285914, -0.02663246980661016, -0.09979185870851381, -0.06752657628297165, -0.2560504365354608, -0.04680056941961283, -0.306162387011701, -0.1688641704151594, -0.3080942027375717, -0.08694726402826025, -0.10806712936874002, -0.08445824815848363, 0.13682304083925334, 0.04170303663403901}};

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), PAGE_AXIS));

        TransformView1D view = new TransformView1D(data);
        JScrollPane pane = new JScrollPane(view.getComponent());

        frame.getContentPane().add(pane);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

//        Thread.sleep(10000);
//        ImageIO.write(view.getImage(), "png", new File("C:\\Users\\GuiR\\Documents/test out.png"));
    }
}
