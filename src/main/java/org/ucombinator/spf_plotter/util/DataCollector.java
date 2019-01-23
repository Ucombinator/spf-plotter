package org.ucombinator.spf_plotter.util;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class DataCollector {
    private int numSeries;
    private ArrayList<Double> rawData;
    private TreeMap<String, ArrayList<Double>> data;
    private TreeMap<String, String> functions;

    DataCollector() {
        this.numSeries = 0;
        this.rawData = new ArrayList<>();
        this.data = new TreeMap<>();
        this.functions = new TreeMap<>();
    }

    public void addRawDataPoint(Double dataPoint) {
        rawData.add(dataPoint);
    }

    public void addSeries(String seriesName) {
        numSeries += 1;
        data.put(seriesName, new ArrayList<>());
        functions.put(seriesName, null);
    }

    public void addSeriesDataPoint(String seriesName, Double dataPoint) {
        data.get(seriesName).add(dataPoint);
    }

    public void setSeriesFunction(String seriesName, String function) {
        functions.put(seriesName, function);
    }

    public int getNumSeries() {
        return numSeries;
    }

    public int getRawSeriesLength() {
        return rawData.size();
    }

    public int getSeriesLength(String seriesName) {
        return data.get(seriesName).size();
    }

    public List<Double> getRawSeries() {
        return new ArrayList<>(rawData);
    }

    public List<String> getSortedSeriesNames() {
        return new ArrayList<>(data.keySet());
    }

    public List<Double> getDataSeries(String seriesName) {
        return new ArrayList<>(data.get(seriesName));
    }

    public String getSeriesFunction(String seriesName) {
        return this.functions.get(seriesName);
    }
}
