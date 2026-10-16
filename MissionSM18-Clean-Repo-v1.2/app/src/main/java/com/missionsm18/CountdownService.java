package com.missionsm18;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.RemoteViews;

public class CountdownService extends Service {
    private static final String CHANNEL = "mission_sm18_countdown";
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable updater = new Runnable() {
        @Override public void run() {
            refreshWidgets();
            handler.postDelayed(this, 1000);
        }
    };

    @Override public void onCreate() {
        super.onCreate();
        createChannel();
        Intent open = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this,0,open,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        Notification.Builder builder = Build.VERSION.SDK_INT >= 26
                ? new Notification.Builder(this, CHANNEL) : new Notification.Builder(this);
        Notification notification = builder.setContentTitle("Mission SM18")
                .setContentText("Keeping your countdown widget updated")
                .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
                .setContentIntent(pi).setOngoing(true).build();
        startForeground(18, notification);
        handler.removeCallbacks(updater);
        handler.post(updater);
    }

    @Override public int onStartCommand(Intent intent,int flags,int startId) {
        refreshWidgets();
        return START_STICKY;
    }
    private void refreshWidgets() {
        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        int[] ids = manager.getAppWidgetIds(new ComponentName(this, CountdownWidgetProvider.class));
        if (ids.length > 0) CountdownWidgetProvider.updateWidgets(this, manager, ids);
    }
    private void createChannel() {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel = new NotificationChannel(CHANNEL,"Mission SM18 countdown",NotificationManager.IMPORTANCE_LOW);
            channel.setDescription("Keeps the Mission SM18 home-screen countdown current.");
            NotificationManager nm=getSystemService(NotificationManager.class);
            if(nm!=null)nm.createNotificationChannel(channel);
        }
    }
    @Override public void onDestroy() { handler.removeCallbacks(updater); super.onDestroy(); }
    @Override public IBinder onBind(Intent intent) { return null; }
}
