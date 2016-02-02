package com.cs2340.officehours.freshavocados;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickLogout(View v) {
        startActivity(new Intent(getApplicationContext(), SplashActivity.class));
    }

}
