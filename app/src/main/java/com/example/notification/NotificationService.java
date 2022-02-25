package com.example.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

public class NotificationService extends Service {

    NotificationManager notificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        PendingIntent pendingIntentActivity1 =
                PendingIntent.getActivity(
                        this,
                        0,
                        new Intent(this, NotificationActivity1.class),
                        PendingIntent.FLAG_CANCEL_CURRENT
                );

        PendingIntent pendingIntentActivity2 =
                PendingIntent.getActivity(
                        this,
                        0,
                        new Intent(this, NotificationActivity2.class),
                        PendingIntent.FLAG_CANCEL_CURRENT
                );

        Notification notification =
                new NotificationCompat.Builder(this, "111")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Уведомление")
                        .setContentText("Уведомление из сервиса")
                        .setContentIntent(pendingIntentActivity1)
                        .addAction(R.mipmap.ic_launcher, "Activity 1", pendingIntentActivity1)
                        .addAction(R.mipmap.ic_launcher, "Activity 2", pendingIntentActivity2)
                        .build();

        startForeground(1, notification);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel("111", "Example Service Channel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(serviceChannel);
        }
    }
}
