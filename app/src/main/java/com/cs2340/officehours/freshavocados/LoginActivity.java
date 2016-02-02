package com.cs2340.officehours.freshavocados;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Toast;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onClickConfirmLogin(View v) {
//        Toast toast = Toast.makeText(getApplicationContext(), "Temporary toast!", Toast.LENGTH_SHORT);
//        toast.show();
        // the above code can be used to tell them the login info was wrong. The below
        // statement can be put into a conditional that only activates on correct login info
        // For now, it always happens
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    public void onClickCancelLogin(View v) {
        startActivity(new Intent(getApplicationContext(), SplashActivity.class));
    }

}
