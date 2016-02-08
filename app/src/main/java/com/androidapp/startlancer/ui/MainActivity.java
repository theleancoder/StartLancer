package com.androidapp.startlancer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.androidapp.startlancer.R;
import com.androidapp.startlancer.ui.freelancer.LoginFreelancerActivity;
import com.androidapp.startlancer.ui.startup.LoginStartupActivity;
import com.firebase.client.Firebase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
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
}
