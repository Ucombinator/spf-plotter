package org.ucombinator.spf_plotter;

import org.ucombinator.spf_plotter.util.DataLoader;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        assert args.length == 2;
        String dataFile = args[0];
        String functionsFile = args[1];
        DataLoader loader;
        try {
            loader = new DataLoader(dataFile, functionsFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
