package com.squadfinder.brend.squadandroidcalculator.domain.calc;

/**
 * Created by brend on 3/11/2018.
 */

public class MortarMathUtil {
    // The math for the Mortar Calculation 6th order polynomial
    private static final int baseTerm = 1584;
    private static final double firstOrder = -0.0167;
    private static final double secondOrder = -0.00303;
    private static final double thirdOrder = 0.00000995;
    private static final double fourthOrder = -0.000000016;
    private static final double fifthOrder = 0.0000000000122;
    private static final double sixthOrder = -0.00000000000000357;

    public static double getMilsFromMeters(double meters) {
        double one = firstOrder * meters;
        double two = secondOrder * Math.pow(meters, 2);
        double three = thirdOrder * Math.pow(meters, 3);
        double four = fourthOrder * Math.pow(meters, 4);
        double five = fifthOrder * Math.pow(meters, 5);
        double six = sixthOrder * Math.pow(meters, 6);
        return baseTerm + one + two + three + four + five + six;
    }
}
