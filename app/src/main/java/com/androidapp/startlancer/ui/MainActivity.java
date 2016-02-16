package com.androidapp.startlancer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.ui.freelancer.LoginFreelancerActivity;
import com.androidapp.startlancer.ui.startup.LoginStartupActivity;
import com.batch.android.Batch;
import com.batch.android.Config;
import com.firebase.client.Firebase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        Batch.setConfig(new Config("DEV56BF33538D87EEEDA6F379945DE"));
        Batch.Push.setGCMSenderId("938371195570");
        setContentView(R.layout.activity_main);
    }

    public void goToFreelancerLogin(View view) {
        Intent intent = new Intent(MainActivity.this, LoginFreelancerActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void goToStartupLogin(View view) {
        Intent intent = new Intent(MainActivity.this, LoginStartupActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        Batch.onStart(this);
    }

    @Override
    protected void onStop() {
        Batch.onStop(this);

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Batch.onDestroy(this);

        super.onDestroy();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Batch.onNewIntent(this, intent);

        super.onNewIntent(intent);
    }

}
