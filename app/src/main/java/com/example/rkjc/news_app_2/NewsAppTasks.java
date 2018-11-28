package com.example.rkjc.news_app_2;


import android.content.Context;
import android.app.*;


class NewsAppTasks {

    public static final String ACTION_CANCEL_NOTIFICATION = "cancel-notification";
    public static final String ACTION_SEND_NOTIFICATION = "send-notification";

    public static void executeTask(Context context, String action) {
        if(ACTION_CANCEL_NOTIFICATION.equals(action)) {
            NotifcationUtils.clearAllNotifications(context);
            //NewsAppBackgroupUpdaterUtilities.cancelBackgroudUpdater();
        } else if (ACTION_SEND_NOTIFICATION.equals(action)) {
            NotifcationUtils.sendNotification(context);
        }
    }
}
