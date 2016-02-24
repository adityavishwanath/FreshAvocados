package com.cs2340.officehours.freshavocados.controller;

import android.media.Rating;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.cs2340.officehours.freshavocados.R;
import com.cs2340.officehours.freshavocados.model.Movie;

public class ReviewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        EditText review_text = (EditText) findViewById(R.id.review_text);
        review_text.setHorizontallyScrolling(false);
        review_text.setMaxLines(8);

        Movie mov = (Movie) getIntent().getSerializableExtra("movie");
        TextView title = (TextView) findViewById(R.id.movie_name);
        title.setText(mov.getTitleYear());

        RatingBar rating = (RatingBar) findViewById(R.id.ratingBar);
        rating.setStepSize(1);
    }

    public void onClickBackToIndiv(View v) {
        finish();
    }

    public void onClickSubmitReview(View v) {

    }

}
