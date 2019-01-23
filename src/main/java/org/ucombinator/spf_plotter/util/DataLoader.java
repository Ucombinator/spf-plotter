package org.ucombinator.spf_plotter.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DataLoader {
    private String dataFile;
    private String functionsFile;
    private DataCollector collector;

    public DataLoader(String dataFile, String functionsFile) throws FileNotFoundException {
        this.dataFile = dataFile;
        this.functionsFile = functionsFile;
        this.collector = new DataCollector();
        loadDataFromFiles();
    }

    private void loadDataFromFiles() throws FileNotFoundException {
        loadDataFromFile();
        loadFunctionsFromFile();
    }

    private void loadDataFromFile() throws FileNotFoundException {
        Scanner dataScanner = new Scanner(new File(this.dataFile));
        dataScanner.useDelimiter(",");
        String[] headers = dataScanner.nextLine().split(",");
        int numCols = headers.length;
        assert numCols >= 2;
        assert headers[0].toLowerCase().equals("size");
        assert headers[1].toLowerCase().equals("raw");
        int sizeCol = 0;
        int rawCol = 1;
        for (String seriesName : headers) {
            this.collector.addSeries(seriesName);
        }
        while (dataScanner.hasNextLine()) {
            String[] cols = dataScanner.nextLine().split(",");
            for (int c = 0; c < numCols; ++c) {
                if (c == sizeCol) {
                    continue;
                } else if (c == rawCol) {
                    this.collector.addRawDataPoint(Double.parseDouble(cols[c]));
                } else {
                    this.collector.addSeriesDataPoint(headers[c], Double.parseDouble(cols[c]));
                }
            }
        }
    }

    private void loadFunctionsFromFile() throws FileNotFoundException {
        Scanner functionsScanner = new Scanner(new File(this.functionsFile));
        functionsScanner.useDelimiter(",");
        String[] headers = functionsScanner.nextLine().split(",");
        int numCols = headers.length;
        assert numCols == this.collector.getNumSeries();
        String[] functions = functionsScanner.nextLine().split(",");
        for (int c = 0; c < numCols; ++c) {
            this.collector.setSeriesFunction(headers[c], functions[c]);
        }
    }

    public DataCollector getCollector() {
        return this.collector;
    }
}
