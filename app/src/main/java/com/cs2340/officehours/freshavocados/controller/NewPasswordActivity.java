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

public class NewPasswordActivity extends Activity {

    Toast passwordChangedToast;
    Toast passwordsNotMatched;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
    }

    /**
     * Checks to see if the new passwords match and sets the user's password accordingly
     * @param v the default param for onClick methods
     */
    public void onClickChangePass(View v) {
        EditText newPass = (EditText) findViewById(R.id.new_pass);
        EditText confirmPass = (EditText) findViewById(R.id.confirm_new_pass);

        if (newPass.getText().toString().equals(confirmPass.getText().toString())) {
            LoginActivity.currentUser.setPass(newPass.getText().toString());
            if (passwordChangedToast == null) {
                passwordChangedToast = Toast.makeText(getApplicationContext(),
                        "Password Changed!", Toast.LENGTH_SHORT);
            }
            passwordChangedToast.show();
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

    /**
     * Cancels the process of changing the password and returns to the view profile screen
     * @param v the default param for onClick methods
     */
    public void onClickCancelChangePassword(View v) {
        startActivity(new Intent(getApplicationContext(), ViewProfileActivity.class));
        Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        a.vibrate(50);
    }

}
