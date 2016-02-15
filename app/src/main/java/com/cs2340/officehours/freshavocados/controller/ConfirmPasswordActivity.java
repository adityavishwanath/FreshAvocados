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

    Toast wrongPasswordToast;
    Toast correctPasswordToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_password);
    }

    /**
     * Cancels the activity and returns to a view of the user's profile
     * @param v the default param for onClick methods
     */
    public void onClickCancelEditProfile(View v) {
        startActivity(new Intent(getApplicationContext(), ViewProfileActivity.class));
        Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        a.vibrate(50);
    }

    /**
     * Checks to make sure the user knows their current password before allowing
     * them to change to a new one
     * @param v the default param for onClick methods
     */
    public void onClickConfirmPassword(View v) {
        EditText password = (EditText) findViewById(R.id.passwordConfirm);

        if (!(password.getText().toString().equals(LoginActivity.currentUser.getPassword()))) {
            if (wrongPasswordToast == null) {
                wrongPasswordToast = Toast.makeText(getApplicationContext(), "Incorrect password. Please try again.", Toast.LENGTH_SHORT);
            }
            wrongPasswordToast.show();
        } else {
            if (correctPasswordToast == null) {
                correctPasswordToast = Toast.makeText(getApplicationContext(), "Password Confirmed", Toast.LENGTH_SHORT);
            }
            correctPasswordToast.show();
            startActivity(new Intent(getApplicationContext(), NewPasswordActivity.class));
        }
        Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        a.vibrate(50);
    }

}
