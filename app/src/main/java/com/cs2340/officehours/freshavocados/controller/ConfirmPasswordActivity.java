package com.cs2340.officehours.freshavocados.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Vibrator;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cs2340.officehours.freshavocados.R;

public class ConfirmPasswordActivity extends Activity {

    Toast wrongUsername;
    Toast correctUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_password);
    }

    public void onClickCancelEditProfile(View v) {
        startActivity(new Intent(getApplicationContext(), ViewProfileActivity.class));
        Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        a.vibrate(50);
    }

    public void onClickConfirmPassword(View v) {
        EditText password = (EditText) findViewById(R.id.passwordConfirm);

        if (!(password.getText().toString().equals(LoginActivity.currentUser.getPassword()))) {
            if (wrongUsername == null) {
                wrongUsername = Toast.makeText(getApplicationContext(), "Incorrect password. Please try again.", Toast.LENGTH_SHORT);
            }
            wrongUsername.show();
        } else {
            if (correctUsername == null) {
                correctUsername = Toast.makeText(getApplicationContext(), "Password Confirmed", Toast.LENGTH_SHORT);
            }
            correctUsername.show();
            startActivity(new Intent(getApplicationContext(), EditProfileActivity.class));
        }
    }

}
