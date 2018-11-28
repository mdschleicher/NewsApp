package com.example.rkjc.news_app_2;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Action;
import android.support.v4.content.ContextCompat;

class NotifcationUtils {

    private static final int NEWS_APP_BACKGROUND_UPDATER_NOTIFICATAION_ID = 1337;

    private static final int NEWS_APP_BACKGROUND_UPDATER_PENDING_INTENT_ID = 9367;

    private static final int ACTION_CANCEL_PENDING_INTENT_ID = 3978;

    private static final String NEWS_APP_NOTIFCATION_CHANNEL_ID = "newsapp_notification_channel";

    public static void clearAllNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public static void sendNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    NEWS_APP_NOTIFCATION_CHANNEL_ID,
                    context.getString(R.string.main_notification_channel_name),
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context,NEWS_APP_NOTIFCATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_background_notification)
                .setLargeIcon(largeIcon(context))
                .setContentTitle(context.getString(R.string.background_updater_notification_title))
                .setContentText(context.getString(R.string.background_updater_notification_body))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        context.getString(R.string.background_updater_notification_body)))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                .addAction(cancelBackgroundUpdaterAction(context))
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }
        notificationManager.notify(NEWS_APP_BACKGROUND_UPDATER_NOTIFICATAION_ID, notificationBuilder.build());


    }

    private static Action cancelBackgroundUpdaterAction(Context context) {
        Intent cancelBackgroundUpdaterIntent = new Intent(context, NewsAppIntentService.class);
        cancelBackgroundUpdaterIntent.setAction(NewsAppTasks.ACTION_CANCEL_NOTIFICATION);

        PendingIntent cancelBackgroundUpdaterPendingIntent = PendingIntent.getService(
                context,
                ACTION_CANCEL_PENDING_INTENT_ID,
                cancelBackgroundUpdaterIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        Action cancelBackgroundUpdaterAction = new Action(R.drawable.ic_stop_black_24dp,
                "Cancel",
                cancelBackgroundUpdaterPendingIntent);

        return cancelBackgroundUpdaterAction;
    }

    private static PendingIntent contentIntent(Context context) {
        Intent startActivityIntent = new Intent(context, MainActivity.class);
        return PendingIntent.getActivity(
                context,
                NEWS_APP_BACKGROUND_UPDATER_PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static Bitmap largeIcon(Context context) {
        Resources res = context.getResources();
        Bitmap largeIcon = BitmapFactory.decodeResource(res, R.drawable.ic_fiber_new_black_24dp);
        return largeIcon;
    }
}
