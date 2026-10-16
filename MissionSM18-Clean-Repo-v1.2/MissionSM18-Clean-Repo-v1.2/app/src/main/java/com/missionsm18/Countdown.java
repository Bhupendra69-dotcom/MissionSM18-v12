package com.missionsm18;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

final class Countdown {
    static final ZoneId ZONE = ZoneId.of("Asia/Kolkata");
    static final Instant START = LocalDateTime.of(2026, 10, 15, 0, 0).atZone(ZONE).toInstant();
    static final Instant END = LocalDateTime.of(2031, 10, 15, 0, 0).atZone(ZONE).toInstant();

    static Parts parts(Instant now) {
        Instant clamped = now.isBefore(START) ? START : now;
        if (!clamped.isBefore(END)) return new Parts(0,0,0,0,0,0,true);
        ZonedDateTime a = clamped.atZone(ZONE), b = END.atZone(ZONE);
        int years = b.getYear() - a.getYear();
        ZonedDateTime cursor = a.plusYears(years);
        if (cursor.isAfter(b)) { years--; cursor = a.plusYears(years); }
        long months = ChronoUnit.MONTHS.between(cursor, b);
        cursor = cursor.plusMonths(months);
        if (cursor.isAfter(b)) { months--; cursor = cursor.minusMonths(1); }
        long days = ChronoUnit.DAYS.between(cursor, b);
        cursor = cursor.plusDays(days);
        long secs = Math.max(0, Duration.between(cursor.toInstant(), END).getSeconds());
        return new Parts(years, (int)months, (int)days, secs/3600, (secs%3600)/60, secs%60, false);
    }
    static double progress(Instant now) {
        if (!now.isAfter(START)) return 1.0;
        if (!now.isBefore(END)) return 0.0;
        return Math.max(0, Math.min(1, (double)Duration.between(now, END).toMillis() /
                (double)Duration.between(START, END).toMillis()));
    }
    static int color(Instant now) {
        double r = progress(now);
        if (r > .75) return 0xFF2E7D32;
        if (r > .50) return 0xFFEF6C00;
        if (r > .25) return 0xFFF9A825;
        return 0xFFC62828;
    }
    static final class Parts {
        final int years, months, days; final long hours, minutes, seconds; final boolean finished;
        Parts(int y,int m,int d,long h,long mi,long s,boolean f) {
            years=y; months=m; days=d; hours=h; minutes=mi; seconds=s; finished=f;
        }
        String dateLine() { return years+"y   "+months+"mo   "+days+"d"; }
        String timeLine() { return String.format(java.util.Locale.US,"%02dh   %02dm   %02ds",hours,minutes,seconds); }
    }
}
