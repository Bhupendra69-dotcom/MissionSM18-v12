package com.missionsm18;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import java.time.Instant;
import java.time.ZonedDateTime;

public class CountdownWidgetProvider extends AppWidgetProvider {
    @Override public void onUpdate(Context context, AppWidgetManager manager, int[] ids) {
        updateWidgets(context, manager, ids);
        Intent service = new Intent(context, CountdownService.class);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) context.startForegroundService(service);
        else context.startService(service);
    }

    static void updateWidgets(Context context, AppWidgetManager manager, int[] ids) {
        Instant now = Instant.now();
        Countdown.Parts p = Countdown.parts(now);
        int progress = (int) Math.round(Countdown.progress(now) * 1000);
        int color = Countdown.color(now);
        int period = backgroundPeriod(now);
        int background = backgroundFor(period);
        int textColor = period == 3 ? 0xFFFFFFFF : 0xFF000000;

        for (int id : ids) {
            RemoteViews v = new RemoteViews(context.getPackageName(), R.layout.countdown_widget);
            v.setTextViewText(R.id.widget_title, "Mission SM18");
            v.setTextViewText(R.id.widget_date, p.finished ? "Mission complete" : p.dateLine());
            v.setTextViewText(R.id.widget_time, p.finished ? "Target date reached" : p.timeLine());
            v.setTextColor(R.id.widget_title, textColor);
            v.setTextColor(R.id.widget_date, textColor);
            v.setTextColor(R.id.widget_time, textColor);
            v.setInt(R.id.widget_root, "setBackgroundResource", background);

            v.setProgressBar(R.id.widget_progress_green, 1000, progress, false);
            v.setProgressBar(R.id.widget_progress_orange, 1000, progress, false);
            v.setProgressBar(R.id.widget_progress_yellow, 1000, progress, false);
            v.setProgressBar(R.id.widget_progress_red, 1000, progress, false);
            v.setViewVisibility(R.id.widget_progress_green, color == 0xFF2E7D32 ? android.view.View.VISIBLE : android.view.View.GONE);
            v.setViewVisibility(R.id.widget_progress_orange, color == 0xFFEF6C00 ? android.view.View.VISIBLE : android.view.View.GONE);
            v.setViewVisibility(R.id.widget_progress_yellow, color == 0xFFF9A825 ? android.view.View.VISIBLE : android.view.View.GONE);
            v.setViewVisibility(R.id.widget_progress_red, color == 0xFFC62828 ? android.view.View.VISIBLE : android.view.View.GONE);

            Intent open = new Intent(context, MainActivity.class);
            PendingIntent pi = PendingIntent.getActivity(context, 0, open,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            v.setOnClickPendingIntent(R.id.widget_root, pi);
            manager.updateAppWidget(id, v);
        }
    }

    // Four subtle palettes make the widget follow the local day without changing its layout.
    private static int backgroundPeriod(Instant now) {
        int hour = ZonedDateTime.ofInstant(now, Countdown.ZONE).getHour();
        if (hour >= 5 && hour < 9) return 0;       // sunrise
        if (hour >= 9 && hour < 16) return 1;      // daylight
        if (hour >= 16 && hour < 19) return 2;     // sunset / twilight
        return 3;                                  // night
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
