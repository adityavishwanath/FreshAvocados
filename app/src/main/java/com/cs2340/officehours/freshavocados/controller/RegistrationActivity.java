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

public class RegistrationActivity extends Activity {

    UserManager uM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        uM = new UserManager();
    }

    protected void createUser(View view) {
        EditText fname = (EditText) findViewById(R.id.f_name);
        EditText lname = (EditText) findViewById(R.id.l_name);
        EditText uname = (EditText) findViewById(R.id.u_name);
        EditText pass = (EditText) findViewById(R.id.pass);

        boolean isTrue = uM.addUser(fname.getText().toString(), lname.getText().toString(), uname.getText().toString(), pass.getText().toString());
        if (isTrue) {
            Toast toast = Toast.makeText(getApplicationContext(), "Profile created successfully", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Username already exists", Toast.LENGTH_SHORT);
            toast.show();
            Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            a.vibrate(50);
        }
    }

}
