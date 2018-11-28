package com.example.rkjc.news_app_2;

import android.content.Context;
import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

public class NewsAppBackgroupUpdaterUtilities {
    private static final int REMINDER_INTERVAL_SECONDS = 10;
    private static final int SYNC_FLEXTIME_SECONDS = REMINDER_INTERVAL_SECONDS;

    private static final String NEWSAPP_BACKGROUD_JOB_TAG = "newsapp_updater_tag";

    //private static Driver mDriver;

    private static boolean sInitialized;

    synchronized public static void schedulerBackgroudUpdater(@NonNull final Context context) {
        if (sInitialized) return;

        Driver mDriver = new GooglePlayDriver(context);

        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(mDriver);

        Job constraintReminderJob = dispatcher.newJobBuilder()
                .setService(NewsAppFirebaseJobService.class)

                .setTag(NEWSAPP_BACKGROUD_JOB_TAG)
                /*
                 * Network constraints on which this Job should run. In this app, we're using the
                 * device charging constraint so that the job only executes if the device is
                 * charging.
                 */
                .setConstraints(Constraint.DEVICE_CHARGING)

                /*
                 * setLifetime sets how long this job should persist. The options are to keep the
                 * Job "forever" or to have it die the next time the device boots up.
                 */
                .setLifetime(Lifetime.FOREVER)
                /*
                 * We want these reminders to continuously happen, so we tell this Job to recur.
                 */
                .setRecurring(true)
                /*
                 * We want the reminders to happen every 15 minutes or so. The first argument for
                 * Trigger class's static executionWindow method is the start of the time frame
                 * when the
                 * job should be performed. The second argument is the latest point in time at
                 * which the data should be synced. Please note that this end time is not
                 * guaranteed, but is more of a guideline for FirebaseJobDispatcher to go off of.
                 */
                .setTrigger(Trigger.executionWindow(
                        REMINDER_INTERVAL_SECONDS,
                        REMINDER_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
                /*
                 * If a Job with the tag with provided already exists, this new job will replace
                 * the old one.
                 */
                .setReplaceCurrent(true)
                /* Once the Job is ready, call the builder's build method to return the Job */
                .build();

        // COMPLETED (21) Use dispatcher's schedule method to schedule the job
        /* Schedule the Job with the dispatcher */
        dispatcher.schedule(constraintReminderJob);

        // COMPLETED (22) Set sInitialized to true to mark that we're done setting up the job
        /* The job has been initialized */
        sInitialized = true;


    }

/*    synchronized public static void cancelBackgroudUpdater() {
        if(sInitialized) {
            mDriver.cancelAll();
            mDriver = null;
            sInitialized = false;
        }
    }*/

}
