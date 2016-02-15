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

public class EditProfileActivity extends Activity {

    Toast profileUpdatedToast;
    Toast passwordsNotMatched;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
    }

    public void onClickUpdate(View v) {
        EditText newPass = (EditText) findViewById(R.id.new_password);
        EditText confirmPass = (EditText) findViewById(R.id.confirm_new_password);

        if (newPass.getText().toString().equals(confirmPass.getText().toString())) {
            LoginActivity.currentUser.setPass(newPass.getText().toString());
            if (profileUpdatedToast == null) {
                profileUpdatedToast = Toast.makeText(getApplicationContext(),
                        "Profile Updated!", Toast.LENGTH_SHORT);
            }
            profileUpdatedToast.show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        } else {
            if (passwordsNotMatched == null) {
                passwordsNotMatched = Toast.makeText(getApplicationContext(),
                        "Passwords did not match. Please try again.", Toast.LENGTH_SHORT);
            }
            passwordsNotMatched.show();
        }
        Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        a.vibrate(50);
    }

    public void onClickCancelEditProfile(View v) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        a.vibrate(50);
    }
}
