package com.cs2340.officehours.freshavocados.controller;

import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.os.Vibrator;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cs2340.officehours.freshavocados.R;
import com.cs2340.officehours.freshavocados.model.UserManager;

public class EditProfileActivity extends Activity {

    UserManager uM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        uM = new UserManager();
    }

    public void editPassword(View view) {
        EditText newpass = (EditText) findViewById(R.id.newPass);
        EditText confirmpass = (EditText) findViewById(R.id.confirmPass);
        EditText username = (EditText) findViewById(R.id.confirmUser);
        EditText pass = (EditText) findViewById(R.id.oldPassword);

        if (!UserManager.users.containsKey(username.getText().toString())) {
            Toast toast = Toast.makeText(getApplicationContext(), "Username does not exist", Toast.LENGTH_SHORT);
            toast.show();
            Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            a.vibrate(50);
        } else if (!UserManager.users.get(username.getText().toString()).checkPassword(pass.getText().toString())) {
            Toast toast = Toast.makeText(getApplicationContext(), "Incorrect password", Toast.LENGTH_SHORT);
            toast.show();
            Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            a.vibrate(50);
        } else if (!newpass.equals(confirmpass)){
            Toast toast = Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT);
            toast.show();
            Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            a.vibrate(50);
        }  else {
            UserManager.users.get(username.getText().toString()).setPass(newpass.getText().toString());
            Toast toast = Toast.makeText(getApplicationContext(), "Password updated successfully", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
