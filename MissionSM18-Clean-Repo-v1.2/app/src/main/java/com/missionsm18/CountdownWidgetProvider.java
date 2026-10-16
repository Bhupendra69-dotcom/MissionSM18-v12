package com.missionsm18;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CountdownWidgetProvider extends AppWidgetProvider {
    private static final int GREEN = 0xFF2E7D32;
    private static final int YELLOW = 0xFFF9A825;
    private static final int ORANGE = 0xFFEF6C00;
    private static final int RED = 0xFFC62828;

    @Override public void onUpdate(Context context, AppWidgetManager manager, int[] ids) {
        updateWidgets(context, manager, ids);
        Intent service = new Intent(context, CountdownService.class);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) context.startForegroundService(service);
        else context.startService(service);
    }

    static void updateWidgets(Context context, AppWidgetManager manager, int[] ids) {
        Instant now = Instant.now();
        Countdown.Parts p = Countdown.parts(now);
        int mainProgress = (int) Math.round(Countdown.progress(now) * 1000.0);
        int mainColor = Countdown.color(now);
        int period = backgroundPeriod(now);
        int background = backgroundFor(period);
        int textColor = period == 3 ? 0xFFFFFFFF : 0xFF000000;
        int dailyProgress = dailyRemainingProgress(now);
        int dailyColor = progressColor(dailyProgress);
        String today = now.atZone(Countdown.ZONE).format(DateTimeFormatter.ofPattern("EEEE, d MMM yyyy", Locale.ENGLISH));
        String mainPercent = Math.round(mainProgress / 10.0) + "%";
        String dailyPercent = Math.round(dailyProgress / 10.0) + "%";

        for (int id : ids) {
            RemoteViews v = new RemoteViews(context.getPackageName(), R.layout.countdown_widget);
            v.setTextViewText(R.id.widget_title, "Mission SM18");
            v.setTextViewText(R.id.widget_today, today);
            v.setTextViewText(R.id.widget_date, p.finished ? "Mission complete" : p.dateLine());
            v.setTextViewText(R.id.widget_time, p.finished ? "Target date reached" : p.timeLine());
            v.setTextViewText(R.id.widget_main_percent, mainPercent);
            v.setTextViewText(R.id.widget_day_percent, dailyPercent);
            v.setTextColor(R.id.widget_title, textColor);
            v.setTextColor(R.id.widget_today, textColor);
            v.setTextColor(R.id.widget_date, textColor);
            v.setTextColor(R.id.widget_time, textColor);
            v.setTextColor(R.id.widget_main_percent, textColor);
            v.setTextColor(R.id.widget_day_percent, textColor);
            v.setInt(R.id.widget_root, "setBackgroundResource", background);

            v.setProgressBar(R.id.widget_progress_green, 1000, mainProgress, false);
            v.setProgressBar(R.id.widget_progress_yellow, 1000, mainProgress, false);
            v.setProgressBar(R.id.widget_progress_orange, 1000, mainProgress, false);
            v.setProgressBar(R.id.widget_progress_red, 1000, mainProgress, false);
            v.setViewVisibility(R.id.widget_progress_green, mainColor == GREEN ? View.VISIBLE : View.GONE);
            v.setViewVisibility(R.id.widget_progress_yellow, mainColor == YELLOW ? View.VISIBLE : View.GONE);
            v.setViewVisibility(R.id.widget_progress_orange, mainColor == ORANGE ? View.VISIBLE : View.GONE);
            v.setViewVisibility(R.id.widget_progress_red, mainColor == RED ? View.VISIBLE : View.GONE);

            v.setProgressBar(R.id.widget_day_track, 1000, 1000, false);
            v.setProgressBar(R.id.widget_day_green, 1000, dailyProgress, false);
            v.setProgressBar(R.id.widget_day_yellow, 1000, dailyProgress, false);
            v.setProgressBar(R.id.widget_day_orange, 1000, dailyProgress, false);
            v.setProgressBar(R.id.widget_day_red, 1000, dailyProgress, false);
            v.setViewVisibility(R.id.widget_day_green, dailyColor == GREEN ? View.VISIBLE : View.GONE);
            v.setViewVisibility(R.id.widget_day_yellow, dailyColor == YELLOW ? View.VISIBLE : View.GONE);
            v.setViewVisibility(R.id.widget_day_orange, dailyColor == ORANGE ? View.VISIBLE : View.GONE);
            v.setViewVisibility(R.id.widget_day_red, dailyColor == RED ? View.VISIBLE : View.GONE);

            Intent open = new Intent(context, MainActivity.class);
            PendingIntent pi = PendingIntent.getActivity(context, 0, open,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            v.setOnClickPendingIntent(R.id.widget_root, pi);
            manager.updateAppWidget(id, v);
        }
    }

    private static int progressColor(int progress) {
        if (progress > 750) return GREEN;
        if (progress > 500) return YELLOW;
        if (progress > 250) return ORANGE;
        return RED;
    }

    private static int dailyRemainingProgress(Instant now) {
        ZonedDateTime localNow = now.atZone(Countdown.ZONE);
        ZonedDateTime start = localNow.toLocalDate().atStartOfDay(Countdown.ZONE);
        ZonedDateTime nextStart = start.plusDays(1);
        long total = Duration.between(start.toInstant(), nextStart.toInstant()).toMillis();
        long remaining = Math.max(0L, Duration.between(now, nextStart.toInstant()).toMillis());
        if (total <= 0L) return 0;
        return (int) Math.max(0, Math.min(1000, Math.round(1000.0 * remaining / total)));
    }

    private static int backgroundPeriod(Instant now) {
        int hour = now.atZone(Countdown.ZONE).getHour();
        if (hour >= 5 && hour < 9) return 0;
        if (hour >= 9 && hour < 16) return 1;
        if (hour >= 16 && hour < 19) return 2;
        return 3;
    }

    private static int backgroundFor(int period) {
        switch (period) {
            case 0: return R.drawable.widget_background_sunrise;
            case 1: return R.drawable.widget_background_day;
            case 2: return R.drawable.widget_background_sunset;
            default: return R.drawable.widget_background_night;
        }
    }
}
