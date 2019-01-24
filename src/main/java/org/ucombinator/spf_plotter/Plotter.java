package org.ucombinator.spf_plotter;

import org.ucombinator.spf_plotter.util.DataCollector;

import java.awt.*;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import javax.swing.*;

/**
 * Much of the implementation of this class was essentially copy/pasted from:
 *   https://github.com/isstac/spf-wca/blob/master/src/main/wcanalysis/charting/WorstCaseChart.java
 */

public class Plotter extends ApplicationFrame {
    private DataCollector collector;
    private String chartTitle = "Chart Title";
    private String xAxisLabel = "X-Axis";
    private String yAxisLabel = "Y-Axis";

    public Plotter(DataCollector collector) {
        super(Plotter.class.getName());
        this.collector = collector;
        makePlot();
    }

    private void makePlot() {
        final int SERIES_ID = 0;
        XYSeriesCollection ccDataset = new XYSeriesCollection();
        XYPlot plot = new XYPlot();
        plot.setDataset(SERIES_ID, ccDataset);

        XYLineAndShapeRenderer xyLineRenderer1 = new XYLineAndShapeRenderer();
        xyLineRenderer1.setSeriesShapesVisible(SERIES_ID, true);
        plot.setRenderer(SERIES_ID, xyLineRenderer1);

        XYLineAndShapeRenderer xyLineRenderer2 = new XYLineAndShapeRenderer();
        xyLineRenderer2.setSeriesShapesVisible(SERIES_ID, false);

        plot.setRangeAxis(SERIES_ID, new NumberAxis(this.yAxisLabel));
        plot.setDomainAxis(new NumberAxis(this.xAxisLabel));

        plot.mapDatasetToRangeAxis(SERIES_ID, SERIES_ID);

        JFreeChart timeSeriesChart = new JFreeChart(this.chartTitle, getFont(), plot, true);
        timeSeriesChart.setBorderPaint(Color.white);

        ChartPanel chartPanel = new ChartPanel(timeSeriesChart);

        final Map<JCheckBox, XYSeries> box2series = new HashMap<>();

        ItemListener listener = e -> {
            if (e.getItem() instanceof JCheckBox) {
                JCheckBox checkbox = (JCheckBox) e.getItem();
                if (!checkbox.isSelected()) {
                    XYSeries series1 = box2series.get(checkbox);
                    ccDataset.removeSeries(series1);
                } else {
                    XYSeries series1 = box2series.get(checkbox);
                    ccDataset.addSeries(series1);
                }
            }
        };

        JFrame frame = new JFrame("Worst Case Prediction Model");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel checkPanel = new JPanel(new GridLayout(0, 1));

        List<Double> rawSeries = collector.getRawSeries();

        if (!rawSeries.isEmpty()) {
            JPanel rawPanel = new JPanel(new BorderLayout());
            rawPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(Color.BLUE, Color.BLACK), "raw"));
            JCheckBox rawCheckbox = new JCheckBox("Toggle Plot");
            rawCheckbox.setSelected(true);
            rawPanel.add(rawCheckbox);
            rawCheckbox.addItemListener(listener);

            XYSeries rawXYSeries = new XYSeries("Raw");
            for (int i = 0; i < rawSeries.size(); ++i) {
                rawXYSeries.add(i, rawSeries.get(i));
            }
            ccDataset.addSeries(rawXYSeries);
            box2series.put(rawCheckbox, rawXYSeries);
            checkPanel.add(rawPanel);
        }

        for (String seriesName : collector.getSortedSeriesNames()) {
            List<Double> series = collector.getDataSeries(seriesName);
            JPanel boxPanel = new JPanel(new GridLayout(0, 1));
            boxPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), seriesName));
            JCheckBox checkbox = new JCheckBox("Toggle Plot");
            checkbox.setSelected(true);
            checkbox.addItemListener(listener);
            boxPanel.add(checkbox);
            boxPanel.add(new JLabel("Function: " + collector.getSeriesFunction(seriesName)));

            XYSeries xySeries = new XYSeries(seriesName);
            for (int i = 0; i < series.size(); ++i) {
                xySeries.add(i, series.get(i));
            }
            ccDataset.addSeries(xySeries);
            checkPanel.add(boxPanel);
            box2series.put(checkbox, xySeries);
        }

        JPanel mainPanel = new JPanel(new BorderLayout());

        mainPanel.add(checkPanel, BorderLayout.LINE_START);
        mainPanel.add(chartPanel, BorderLayout.CENTER);
        frame.add(mainPanel);

        setContentPane(mainPanel);
    }
}
