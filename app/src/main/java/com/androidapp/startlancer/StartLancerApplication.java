package com.androidapp.startlancer;

import android.app.Application;

import com.batch.android.Batch;
import com.batch.android.Config;
import com.firebase.client.Firebase;

/**
 * Created by ankit on 29/1/16.
 */
public class StartLancerApplication extends Application {
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        Batch.setConfig(new Config("DEV56BF33538D87EEEDA6F379945DE"));
        Batch.Push.setGCMSenderId("938371195570");
    }
}
