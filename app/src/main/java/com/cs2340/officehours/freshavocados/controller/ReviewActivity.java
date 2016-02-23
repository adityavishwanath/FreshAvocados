package com.cs2340.officehours.freshavocados.controller;

import android.os.Bundle;
import android.app.Activity;
import android.widget.EditText;

import com.cs2340.officehours.freshavocados.R;

public class ReviewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        EditText review_text = (EditText) findViewById(R.id.review_text);
        review_text.setHorizontallyScrolling(false);
        review_text.setMaxLines(8);
    }

}
