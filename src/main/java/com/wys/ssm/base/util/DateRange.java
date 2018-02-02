package com.wys.ssm.base.util;

import java.util.Date;

public class DateRange {
    private Date from;
    private Date to;

    private DateRange(Date from, Date to) {
        this.from = from;
        this.to = to;
    }

    public Date getFrom() {
        return from;
    }

    public Date getTo() {
        return to;
    }

    public static DateRange create(long from, long to) {
        return new DateRange(new Date(from), consolidateToDate(to));
    }

    public static DateRange create() {
        return create(0, 0);
    }

    private static Date consolidateToDate(long to) {
        return to == 0 ? new Date() : new Date(to);
    }
}
