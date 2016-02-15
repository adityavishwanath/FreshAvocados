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
import com.cs2340.officehours.freshavocados.model.User;
import com.cs2340.officehours.freshavocados.model.UserManager;

public class LoginActivity extends Activity {
    public static User currentUser;
    Toast loginSuccess;
    Toast loginFailure;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    /**
     * Checks the user's username and password and attempts to log them into the system
     * @param v the default param for onClick methods
     */
    public void onClickConfirmLogin(View v) {
        //Log.d("LoginActivity", "Login Button Pressed!");
        AuthenticationFacade aF = new UserManager();
        EditText userNameBox = (EditText) findViewById(R.id.loginUsername);
        EditText passwordBox = (EditText) findViewById(R.id.loginPassword);
        boolean success = false;
        if (aF.handleLoginRequest(userNameBox.getText().toString(), passwordBox.getText().toString())) {
            success = true;

        }
        if (success) {
            currentUser = UserManager.users.get(userNameBox.getText().toString());
            if (loginSuccess == null) {
                loginSuccess = Toast.makeText(getApplicationContext(), "Login Success!", Toast.LENGTH_SHORT);
            }
            loginSuccess.show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            a.vibrate(50);
        } else {
            if (loginFailure == null) {
                loginFailure = Toast.makeText(getApplicationContext(), "Login failed. Please review login information and try again.", Toast.LENGTH_SHORT);
            }
            loginFailure.show();
            Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            a.vibrate(50);
        }
    }

    /**
     * Cancels the login process and returns to the splash screen
     * @param v the default param for onClick methods
     */
    public void onClickCancelLogin(View v) {
        startActivity(new Intent(getApplicationContext(), SplashActivity.class));
        Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        a.vibrate(50);
    }

}
