package com.squadfinder.brend.squadandroidcalculator.domain.calc;

/**
 * Created by brend on 3/11/2018.
 */

public class MapGridUtil {
    private static final int MAJOR_GRID_METERS = 300;
    private static final int MINOR_GRID_METERS = 100;
    private static final double SUB_GRID_METERS = 33.3;

    private static final double RATIO = .3333;

    public static double getMajorGridPixelDistance(double imageScalePixelsPerMeter) {
        return imageScalePixelsPerMeter * MAJOR_GRID_METERS;
    }

    public static double getMinorGridPixelDistanceFromMajorGridPixelScale(double majorGridDistancePx) {
        return majorGridDistancePx * RATIO;
    }

    public static String getHorizontalGridMajor(double imageScalePixelsPerMeter, float x) {
        return getCharForNumber((int)getGrid(imageScalePixelsPerMeter, x));
    }

    public static String getVerticalGridMajor(double imageScalePixelsPerMeter, float y) {
        return Integer.toString((int)getGrid(imageScalePixelsPerMeter, y));
    }

    public static String getGridKeypad(double imageScalePixelsPerMeter, float x, float y) {
        return getKeyPad(MAJOR_GRID_METERS, MINOR_GRID_METERS, imageScalePixelsPerMeter, x, y);
    }

    public static String getGridSubKeypad(double imageScalePixelsPerMeter, float x, float y) {
        return getKeyPad(MINOR_GRID_METERS, SUB_GRID_METERS, imageScalePixelsPerMeter, x, y);
    }

    private static double getGrid(double imageScale, float value) {
        double meters = value / imageScale;
        return Math.ceil(meters /  MAJOR_GRID_METERS);
    }

    private static String getKeyPad(double majorScale, double minorScale, double imageScale, float x, float y) {
        double xMeters = x / imageScale;
        double xKey = (xMeters % majorScale) / minorScale;
        double yMeters = y / imageScale;
        double yKey = (yMeters % majorScale) / minorScale;
        return keyPads[(int)yKey][(int)xKey];
    }

    private static String getCharForNumber(int i) {
        return i > 0 && i < 27 ? String.valueOf((char)(i + 64)) : null;
    }

    private static final String[][] keyPads = {
        {"7", "8", "9"},
        {"4", "5", "6"},
        {"1", "2", "3"}
    };
}
