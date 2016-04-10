package com.cs2340.officehours.freshavocados.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Vibrator;
import android.view.View;
import android.widget.TextView;

import com.cs2340.officehours.freshavocados.R;

public class ViewProfileActivity extends Activity {
    /**
     * Int for vibrator
     */
    private final static int VIBRATE_TIME = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        //UserManager uM = new UserManager();
        final TextView viewusername = (TextView) findViewById(R.id.viewProfileShowUsername);
        viewusername.setText(LoginActivity.currentUser.getUsername());

        final TextView viewFirstName = (TextView) findViewById(R.id.viewProfileShowFirstName);
        viewFirstName.setText(LoginActivity.currentUser.getFirstName());

        final TextView viewLastName = (TextView) findViewById(R.id.viewProfileShowLastName);
        viewLastName.setText(LoginActivity.currentUser.getLastName());

        final TextView viewEmailAddress = (TextView) findViewById(R.id.viewProfileShowEmailAddress);
        viewEmailAddress.setText(LoginActivity.currentUser.getEmailAddress());

        final TextView viewMajor = (TextView) findViewById(R.id.viewProfileShowMajor);
        viewMajor.setText(LoginActivity.currentUser.getMajor());

        final TextView viewBio = (TextView) findViewById(R.id.viewProfileShowBio);
        viewBio.setText(LoginActivity.currentUser.getBio());
    }

    /**
     * The user can cancel the view profile screen and return to the main screen
     * @param v the default param for onClick methods
     */
    public void onClickCancelViewProfile(View v) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        final Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        a.vibrate(VIBRATE_TIME);
    }

    /**
     * The user can click this button to edit their profile
     * @param v the default param for onClick methods
     */
    public void onClickEditProfile(View v) {
        startActivity(new Intent(getApplicationContext(), EditProfileActivity.class));
        final Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        a.vibrate(VIBRATE_TIME);
        finish();
    }

    /**
     * The user can click this button to change their password
     * @param v the default param for onClick methods
     */
    public void onClickChangePassword(View v) {
        startActivity(new Intent(getApplicationContext(), ConfirmPasswordActivity.class));
        final Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        a.vibrate(VIBRATE_TIME);
    }
//
//    public void editPassword(View view) {
//        EditText newpass = (EditText) findViewById(R.id.newPass);
//        EditText confirmpass = (EditText) findViewById(R.id.confirmPass);
//        EditText username = (EditText) findViewById(R.id.confirmUser);
//        EditText pass = (EditText) findViewById(R.id.oldPassword);
//
//        if (!UserManager.users.containsKey(username.getText().toString())) {
//            Toast toast = Toast.makeText(getApplicationContext(), "Username does not exist", Toast.LENGTH_SHORT);
//            toast.show();
//            Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//            a.vibrate(50);
//        } else if (!UserManager.users.get(username.getText().toString()).checkPassword(pass.getText().toString())) {
//            Toast toast = Toast.makeText(getApplicationContext(), "Incorrect password", Toast.LENGTH_SHORT);
//            toast.show();
//            Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//            a.vibrate(50);
//        } else if (!newpass.equals(confirmpass)){
//            Toast toast = Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT);
//            toast.show();
//            Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//            a.vibrate(50);
//        }  else {
//            UserManager.users.get(username.getText().toString()).setPass(newpass.getText().toString());
//            Toast toast = Toast.makeText(getApplicationContext(), "Password updated successfully", Toast.LENGTH_SHORT);
//            toast.show();
//        }
//    }
}
