package com.example.rkjc.news_app_2;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

public class NewsAppIntentService extends IntentService {
    public NewsAppIntentService() {
        super("NewsAppIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String action = intent.getAction();
        NewsAppTasks.executeTask(this, action);
    }
}
