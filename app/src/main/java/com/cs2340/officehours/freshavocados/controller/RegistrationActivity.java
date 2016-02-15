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
import com.cs2340.officehours.freshavocados.model.UserManager;

public class RegistrationActivity extends Activity {

    UserManager uM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        uM = new UserManager();
    }

    public void createUser(View view) {
        EditText fname = (EditText) findViewById(R.id.f_name);
        EditText lname = (EditText) findViewById(R.id.l_name);
        EditText uname = (EditText) findViewById(R.id.u_name);
        EditText pass = (EditText) findViewById(R.id.pass);
        EditText email = (EditText) findViewById(R.id.email);
        EditText confirm_password = (EditText) findViewById(R.id.confirm_password);

        boolean passWasWrong = false;
        Toast wrongPass = Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT);
        if (!(pass.getText().toString().equals(confirm_password.getText().toString()))) {
            wrongPass.show();
            passWasWrong = true;
        }
        if (!passWasWrong) {
            boolean isTrue = uM.addUser(fname.getText().toString(), lname.getText().toString(), uname.getText().toString(), pass.getText().toString());
            if (isTrue) {
                Toast toast = Toast.makeText(getApplicationContext(), "Profile created successfully", Toast.LENGTH_SHORT);
                toast.show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                a.vibrate(50);
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "Username already exists", Toast.LENGTH_SHORT);
                toast.show();
                Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                a.vibrate(50);
            }
        }
    }
    public void onClickCancelRegistration(View v) {
        startActivity(new Intent(getApplicationContext(), SplashActivity.class));
        Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        a.vibrate(50);
    }

    Toast easterEgg;

    public void onClickIcon(View v) {
        if (easterEgg == null) {
            easterEgg = Toast.makeText(getApplicationContext(), "You found the easter egg!", Toast.LENGTH_SHORT);
        }
        easterEgg.show();
        Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        a.vibrate(50);
    }

}
