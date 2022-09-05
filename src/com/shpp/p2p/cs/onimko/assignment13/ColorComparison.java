package com.shpp.p2p.cs.onimko.assignment13;

import java.awt.*;

/**
 * The class for checks two colors.
 */
public class ColorComparison implements Const{

    /**
     * Method returns true if first color is similar to second color.
     * @param first the first color
     * @param second the second color
     * @return true or false
     */
    public static boolean isSimilarColor(Color first, Color second) {
        double result = (getRelativeBrightness(first) + 0.05)
                /(getRelativeBrightness(second) + 0.05);
        result = result > 1 ? result : 1/result;
        return result < CONTRAST_RATIO;
    }

    /**
     * Method for calculating a relative Brightness for a color.
     * @param color the input color.
     * @return the relative brightness.
     */
    private static double getRelativeBrightness(Color color) {
        return color != null ? 0.2126 * getRelativeColor(color.getRed())
                + 0.7152 * getRelativeColor(color.getGreen())
                + 0.0722 * getRelativeColor(color.getBlue())
                : 0.0;
    }

    /**
     * Method for decomposition calculating relative brightness.
     * @param value the brightness for color
     * @return the relative value.
     */
    private static double getRelativeColor(int value) {
        double sRGB = (double) value / 255;
        return sRGB <= 0.03928 ? sRGB/12.92 : Math.pow((sRGB + 0.055) / 1.055,2.4);
    }
}
