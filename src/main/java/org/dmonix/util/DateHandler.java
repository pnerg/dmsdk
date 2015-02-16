package org.dmonix.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for managing dates and times. <br>
 * Primarily used for formatting string into dates and vice versa.
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: dmonix
 * </p>
 * 
 * @author Peter Nerg
 * @since 1.0
 */
public abstract class DateHandler {
    /** The logger instance for this class */
    private static final Logger log = Logger.getLogger(DateHandler.class.getName());

    /** The built in default supported date formats. */
    private static final SimpleDateFormat[] DATE_FORMATS = new SimpleDateFormat[] { new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH),
            new SimpleDateFormat("EEEE, dd-MMM-yy HH:mm:ss z", Locale.ENGLISH), new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy", Locale.ENGLISH) };

    private DateHandler() {
    }

    /**
     * Formats the given time in milliseconds to a chosen format ex. yyyyy.MMMMM.dd kk:mm ex. yyyyy-MM-dd
     * 
     * @param millis
     *            milliseconds since January 1, 1970, 00:00:00 GMT
     * @param dateFormat
     *            The format to return the string in
     * @return String
     */
    public static String getDate(long millis, String dateFormat) {
        Date date = new Date(millis);
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(date);
    }

    /**
     * Parse a Date object from a date string. <br>
     * The method will attempt to use one of the default date formats
     * 
     * @param dateString
     *            The date string
     * @return The Date object or null if unable to parse
     * @see #DATE_FORMATS
     */
    public static Date getDate(String dateString) {
        Date fileDate;

        for (int i = 0; i < DATE_FORMATS.length; i++) {
            try {
                fileDate = DATE_FORMATS[i].parse(dateString);
                return fileDate;
            } catch (ParseException ex1) {
            }
        }
        log.log(Level.WARNING, "Failed to parse the date string: " + dateString);
        return null;
    }

    /**
     * Parse a Date object from a date string.
     * 
     * @param dateString
     *            The date string
     * @param dateFormat
     *            The date format to use for the parse
     * @return The Date object or null if unable to parse
     */
    public static Date getDate(String dateString, String dateFormat) {
        Date fileDate;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            fileDate = sdf.parse(dateString);
            return fileDate;
        } catch (ParseException ex) {
            log.log(Level.WARNING, "Failed to parse the date string: " + dateString);
            return null;
        }
    }

    /**
     * Returns the number of days between two dates.
     * <ul>
     * <li>e.g. start 2003-12-31, stop 2004-01-01 returns 1</li>
     * <li>The method returns zero if the start/stop date are the same. <br>
     * e.g. start 2004-01-01, stop 2004-01-01 returns 0</li>
     * <li>The method returns a negative value if the start date is after the stop date.<br>
     * e.g. start 2004-01-02, stop 2004-01-01 returns -1</li>
     * </ul>
     * 
     * @param startYear
     *            start year
     * @param startMonth
     *            start month [1-12]
     * @param startDay
     *            start day
     * @param endYear
     *            end year
     * @param endMonth
     *            end month [1-12]
     * @param endDay
     *            end day
     * @return number of days
     */
    public static int getDaysBetweenDates(int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, startYear);
        calendar.set(Calendar.MONTH, startMonth - 1);
        calendar.set(Calendar.DAY_OF_MONTH, startDay);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.YEAR, endYear);
        calendar2.set(Calendar.MONTH, endMonth - 1);
        calendar2.set(Calendar.DAY_OF_MONTH, endDay);

        return (int) ((calendar2.getTimeInMillis() - calendar.getTimeInMillis()) / (1000 * 60 * 60 * 24));
    }

    /**
     * Returns the number of milliseconds since January 1, 1970, 00:00:00 GMT for the given date.
     * 
     * @param year
     *            The year
     * @param month
     *            The month
     * @param day
     *            Day of the month
     * @return long
     */
    public static long getMillis(int year, int month, int day) {
        GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
        return calendar.getTime().getTime();
    }

    /**
     * Returns the number of milliseconds since January 1, 1970, 00:00:00 GMT for the given date.
     * 
     * @param year
     *            The year
     * @param month
     *            The month
     * @param day
     *            Day of the month
     * @param hour
     *            The hour
     * @param minute
     *            The minute of the hour
     * @return long
     */
    public static long getMillis(int year, int month, int day, int hour, int minute) {
        GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day, hour, minute);
        return calendar.getTime().getTime();
    }

    /**
     * Returns the number of milliseconds since January 1, 1970, 00:00:00 GMT for the given date.
     * 
     * @param year
     *            The year
     * @param month
     *            The month
     * @param day
     *            Day of the month
     * @param hour
     *            The hour
     * @param minute
     *            The minutes of the hour
     * @param second
     *            The seconds of the minute
     * @return long
     */
    public static long getMillis(int year, int month, int day, int hour, int minute, int second) {
        GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day, hour, minute, second);
        return calendar.getTime().getTime();
    }

    /**
     * Returns the number of milliseconds since January 1, 1970, 00:00:00 GMT for the given date.
     * 
     * @param year
     *            The year
     * @param month
     *            The month
     * @param day
     *            Day of the month
     * @param hour
     *            The hour
     * @param minute
     *            The minutes of the hour
     * @param second
     *            The seconds of the minute
     * @param millis
     *            The milliseconds of the second
     * @return long
     */
    public static long getMillis(int year, int month, int day, int hour, int minute, int second, int millis) {
        GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day, hour, minute, second);
        return calendar.getTime().getTime() + millis;
    }

    /**
     * Returns today in a formatted string ex. yyyy.MMMMM.dd kk:mm ex. yyyy-MM-dd
     * 
     * @param dateFormat
     *            The format to return the string in
     * @return String
     */
    public static String getToday(String dateFormat) {
        return getDate(System.currentTimeMillis(), dateFormat);
    }

}