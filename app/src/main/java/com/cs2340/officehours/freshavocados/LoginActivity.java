package com.cs2340.officehours.freshavocados;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Toast;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onClickConfirmLogin(View v) {
        Toast toast = Toast.makeText(getApplicationContext(), "Temporary toast!", Toast.LENGTH_SHORT);
        toast.show();

    }

}
