package com.example.rkjc.news_app_2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class NewsAppFirebaseJobService extends JobService {

    private AsyncTask mBackgroundTask;

    @Override
    public boolean onStartJob(final JobParameters job) {
        mBackgroundTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                Context context = NewsAppFirebaseJobService.this;
                NewsItemRepository mRespoitory = new NewsItemRepository(getApplication());
                mRespoitory.sync(NetworkUtils.buildUrl());
                NewsAppTasks.executeTask(context, NewsAppTasks.ACTION_SEND_NOTIFICATION);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                jobFinished(job, false);
            }
        };

        mBackgroundTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        if(mBackgroundTask != null) {
            mBackgroundTask.cancel(true);
        }
        return true;
    }
}
