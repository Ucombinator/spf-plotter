package org.ucombinator.spf_plotter.util;

import java.util.ArrayList;
import java.util.HashMap;

public class DataCollector {
    private int numSeries;
    private ArrayList<Double> rawData;
    private HashMap<String, ArrayList<Double>> data;
    private HashMap<String, String> functions;

    DataCollector() {
        this.numSeries = 0;
        this.rawData = new ArrayList<>();
        this.data = new HashMap<>();
        this.functions = new HashMap<>();
    }

    public void addRawDataPoint(Double dataPoint) {
        this.rawData.add(dataPoint);
    }

    public void addSeries(String seriesName) {
        this.numSeries += 1;
        this.data.put(seriesName, new ArrayList<>());
        this.functions.put(seriesName, null);
    }

    public void addSeriesDataPoint(String seriesName, Double dataPoint) {
        this.data.get(seriesName).add(dataPoint);
    }

    public void setSeriesFunction(String seriesName, String function) {
        this.functions.put(seriesName, function);
    }

    public int getNumSeries() {
        return numSeries;
    }
}
