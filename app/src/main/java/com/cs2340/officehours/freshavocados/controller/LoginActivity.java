package com.cs2340.officehours.freshavocados.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cs2340.officehours.freshavocados.R;
import com.cs2340.officehours.freshavocados.model.AuthenticationFacade;
import com.cs2340.officehours.freshavocados.model.UserManager;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onClickConfirmLogin(View v) {
        Log.d("LoginActivity", "Login Button Pressed!");
        AuthenticationFacade aF = new UserManager();
        EditText userNameBox = (EditText) findViewById(R.id.loginUsername);
        EditText passwordBox = (EditText) findViewById(R.id.loginPassword);
        CharSequence text;
        boolean success = false;
        if (aF.handleLoginRequest(userNameBox.getText().toString(), passwordBox.getText().toString())) {
            text = "Login Success!";
            success = true;
        } else {
            text = "Login Failure! Please try again!";
        }
        if (success) {
            Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
            toast.show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            a.vibrate(50);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
            toast.show();
            Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            a.vibrate(50);
        }
    }

    public void onClickCancelLogin(View v) {
        startActivity(new Intent(getApplicationContext(), SplashActivity.class));
        Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        a.vibrate(50);
    }

}
