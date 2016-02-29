package com.cs2340.officehours.freshavocados.controller;

import android.content.Context;
import android.media.Rating;
import android.os.Bundle;
import android.app.Activity;
import android.os.Vibrator;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cs2340.officehours.freshavocados.R;
import com.cs2340.officehours.freshavocados.model.Movie;
import com.cs2340.officehours.freshavocados.model.Review;

public class ReviewActivity extends Activity {

    EditText review_text;
    RatingBar rating;
    Movie m;
    Toast t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        review_text = (EditText) findViewById(R.id.review_text);
        review_text.setHorizontallyScrolling(false);
        review_text.setMaxLines(8);

        m = (Movie) getIntent().getSerializableExtra("movie");
        TextView title = (TextView) findViewById(R.id.movie_name);
        title.setText(m.getTitleYear());

        rating = (RatingBar) findViewById(R.id.ratingBar);
        rating.setStepSize(1);
    }

    /**
     * Returns the user to the previous screen
     * @param v default param for the app's View
     */
    public void onClickBackToIndiv(View v) {
        Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        a.vibrate(50);
        finish();
    }

    /**
     * Submits the user's review if it is valid
     * @param v default param for the app's View
     */
    public void onClickSubmitReview(View v) {
        if (review_text.getText().toString().equals("")) {
            if (t == null) {
                t = Toast.makeText(getApplicationContext(), "Review text cannot be empty.", Toast.LENGTH_SHORT);
                t.show();
            }
        } else {
            Review review = new Review(LoginActivity.currentUser.getUsername(), LoginActivity.currentUser.getMajor(),
                    rating, review_text.getText().toString(), m);
            Review.addReview(m.getTitleYear(), review);
            Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            a.vibrate(50);
            finish();
        }

    }

}
