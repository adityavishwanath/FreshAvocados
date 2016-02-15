package com.cs2340.officehours.freshavocados.controller;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;

import com.cs2340.officehours.freshavocados.R;

public class EditProfileActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
    }

    public void onClickUpdate(View v) {
        EditText newPass = (EditText) findViewById(R.id.new_password);
        EditText confirmPass = (EditText) findViewById(R.id.confirm_password);
    }

}
