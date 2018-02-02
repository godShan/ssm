package com.wys.ssm.base.util;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

public class DateTimeHelper {
    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormat.forPattern("yyyyMMddHHmmss");

    public static DateTime toDateTime(Date date) {
        return new DateTime(date);
    }

    public static int daysFromToday(DateTime date) {
        return Days.daysBetween(today(), date).getDays();
    }

    public static int daysUntilToday(DateTime date) {
        return Days.daysBetween(date, today()).getDays();
    }

    public static DateTime startOfDay(DateTime date) {
        return date.withTimeAtStartOfDay();
    }

    public static DateTime startOfDay(Date date) {
        return new DateTime(date).withTimeAtStartOfDay();
    }

    public static DateTime now() {
        return DateTime.now();
    }

    public static DateTime today() {
        return DateTime.now().withTimeAtStartOfDay();
    }

    public static int daysBetween(DateTime from, DateTime to) {
        return Days.daysBetween(from, to).getDays();
    }

    public static boolean isBeforeOrEqualNow(DateTime date) {
        final DateTime now = DateTime.now();
        return date.isBefore(now) || date.isEqual(now);
    }

    public static boolean isAfterOrEqualNow(DateTime date) {
        final DateTime now = DateTime.now();
        return date.isAfter(now) || date.isEqual(now);
    }

    public static boolean isAfterNow(DateTime date) {
        return date.isAfter(DateTime.now());
    }

    public static String getDefaultDateTimeStr() {
        return DEFAULT_FORMATTER.print(DateTime.now());
    }

    public static String appendDateTimeStr(String prefix, String separator) {
        StringBuilder sb = new StringBuilder();
        sb.append(prefix).append(separator).append(getDefaultDateTimeStr());
        return sb.toString();
    }
}
