package com.androidapp.startlancer;

import com.firebase.client.Firebase;

/**
 * Created by ankit on 29/1/16.
 */
public class StartLancerApplication extends android.app.Application {
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
