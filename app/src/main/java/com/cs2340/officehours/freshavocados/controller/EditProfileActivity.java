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

public class EditProfileActivity extends Activity {

    Toast profileUpdatedToast;
    Toast emptyFieldsToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        EditText major = (EditText) findViewById(R.id.majorEdit);
        EditText bio = (EditText) findViewById(R.id.bioEdit);
        major.setText(LoginActivity.currentUser.getMajor());
        bio.setText(LoginActivity.currentUser.getBio());
    }

    public void onClickUpdate(View v) {
        EditText newMajor = (EditText) findViewById(R.id.majorEdit);
        EditText newBio = (EditText) findViewById(R.id.bioEdit);

        Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        a.vibrate(50);

        if (newMajor.getText().toString().length() != 0 && newBio.getText().toString().length() != 0) {
            LoginActivity.currentUser.setMajor(newMajor.getText().toString());
            LoginActivity.currentUser.setBio(newBio.getText().toString());
            if (profileUpdatedToast == null) {
                profileUpdatedToast = Toast.makeText(getApplicationContext(),
                        "Profile Updated!", Toast.LENGTH_SHORT);
            }
            profileUpdatedToast.show();
            startActivity(new Intent(getApplicationContext(), ViewProfileActivity.class));
        } else {
            if (emptyFieldsToast == null) {
                emptyFieldsToast = Toast.makeText(getApplicationContext(),
                        "One or more of the fields was left empty. Profile was not updated.", Toast.LENGTH_SHORT);
            }
            emptyFieldsToast.show();
        }
    }

    public void onClickCancelEditProfile(View v) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        a.vibrate(50);
    }
}
