package com.levelmoney.client.util;

/**
 * Basic Utility class for converting and extracting things.
 * <p>
 * Created by chris on 11/27/16.
 */
public class FormatUtils {
    /**
     * Extracts year-month from levelmoney server time format.
     * Assumes that the timestamp looks like "2014-10-24T07:20:00.000Z" and returns year and month so "2014-10" for this case.
     * I'm assuming that this is always seven characters so it's going to break for the year 10000 or for BC dates.
     *
     * @param timestamp
     * @return the year-month string
     */
    public static String extractMonth(String timestamp) {
        int yearMonthLength = 7;
        if (timestamp == null) {
            return "unknownMonth";
        } else if (timestamp.length() < yearMonthLength) {
            return timestamp;
        }
        return timestamp.substring(0, yearMonthLength);
    }

    /**
     * Converts long representing centi-cents into human USD readable format.
     * <p>
     * There's all sorts of refactoring that would eventually be done here. Displaying currency accurately is hard.
     *
     * @param l 10049
     * @return "$1.00"
     */
    public static String convertLongToCurrency(Long l) {
        long dollars = l / 10000;
        int cents = (int) (l % 10000);
        int centiCents = cents % 100;

        int displayCents = cents / 100;
        if (centiCents >= 50) { //probably sufficient rounding strategy
            displayCents += 1;
        }
        String displayCentsString = String.valueOf(displayCents);
        if (displayCents < 10) {
            displayCentsString = "0" + displayCentsString;
        }

        return new StringBuilder().append("$")
                .append(dollars)
                .append(".")
                .append(displayCentsString)
                .toString();
    }
}
