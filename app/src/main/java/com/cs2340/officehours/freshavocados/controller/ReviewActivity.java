package com.cs2340.officehours.freshavocados.controller;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cs2340.officehours.freshavocados.R;
import com.cs2340.officehours.freshavocados.model.Movie;
import com.cs2340.officehours.freshavocados.model.Review;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ReviewActivity extends Activity {

    /**
     * EditText with review text
     */
    private EditText reviewText;
    /**
     * RatingBar for movie's rating in stars out of 5
     */
    private RatingBar rating;
    /**
     * Movie for the movie being reviewed
     */
    private Movie m;
    /**
     * Toast with message when making review
     */
    private Toast t;
    /**
     * String for rating text
     */
    private String rat;
    /**
     * Int with max number of lines for review
     */
    private final static int MAX_LINES = 8;
    /**
     * Int for vibrator
     */
    private final static int VIBRATE_TIME = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        reviewText = (EditText) findViewById(R.id.review_text);
        reviewText.setHorizontallyScrolling(false);
        reviewText.setMaxLines(MAX_LINES);

        m = (Movie) getIntent().getSerializableExtra("movie");
        final TextView title = (TextView) findViewById(R.id.movie_name);
        title.setText(m.getTitleYear());

        rating = (RatingBar) findViewById(R.id.ratingBar);
        rating.setStepSize(1);
    }

    /**
     * Returns the user to the previous screen
     * @param v default param for the app's View
     */
    public void onClickBackToIndiv(View v) {
        final Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        a.vibrate(VIBRATE_TIME);
        finish();
    }

    /**
     * Submits the user's review if it is valid
     * @param v default param for the app's View
     */
    public void onClickSubmitReview(View v) {
        if ("".equals(reviewText.getText().toString())) {
            if (t == null) {
                t = Toast.makeText(getApplicationContext(), "Review text cannot be empty.", Toast.LENGTH_SHORT);
                t.show();
            }
        } else {
//            Review review = new Review(LoginActivity.currentUser.getUsername(), LoginActivity.currentUser.getMajor(),
//                    rating, review_text.getText().toString(), m);
//            Review.addReview(m.getTitleYear(), review);
            rat = Float.toString(rating.getRating());
            new AddReviewTask().execute(LoginActivity.currentUser.getUsername(), m.getTitleYear(), reviewText.getText().toString());
            final Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            a.vibrate(VIBRATE_TIME);
            finish();
        }

    }

    /**
     * Handles adding a review to the multi-user database so that reviews are persistent
     */
    private class AddReviewTask extends AsyncTask<String, Void, String> {

        /**
         * This method immediately starts running when execute() is called.
         * Inputs are username and password.
         * It will retrieve data from php database and pass the return message to onPostExecute().
         * @param args username and password
         * @return sql database query in JSON format
         */
        @Override
        protected String doInBackground(String... args) {
            final String username = args[0];
            final String movie = args[1];
            final String comment = args[2];
            String link;
            BufferedReader bufferedReader;
            String result;
            try {
                String data = "?username=" + URLEncoder.encode(username, "UTF-8");
                data += "&movie=" + URLEncoder.encode(movie, "UTF-8");
                data += "&comment=" + URLEncoder.encode(comment, "UTF-8");
                data += "&rating=" + URLEncoder.encode(rat, "UTF-8");
                link = "http://officehours.netau.net/insertreview.php" + data;
                final URL url = new URL(link);
                final HttpURLConnection con = (HttpURLConnection) url.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                result = bufferedReader.readLine();
                return result;
            } catch (java.io.IOException e) {
                Log.d("ReviewActivity", e.getMessage());
                return e.getMessage();
            }
        }

        /**
         * This method runs after doInBackground.
         * It will process the JSON result to determine any errors or if it was successful.
         * This uses a bit of a hack to process the result. When we query the database there are two
         * possible results:
         * 1) the actual object with user details (name, major, bio, etc)
         * 2) An error message starting with "query_result".
         * If there are no error messages, then we use the catch exception to retrieve the JSON object
         * containing the user information and update CurrentUser accordingly.
         * @param result JSON object retrieved from php response
         */
        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    final JSONObject jsonObj = new JSONObject(result);
                    String query_result;
                    try {
                        query_result = jsonObj.getString("query_result");
                        if ("SUCCESS".equals(query_result)) {
                            final Review review = new Review(LoginActivity.currentUser.getUsername(), LoginActivity.currentUser.getMajor(),
                                    rating.getRating(), reviewText.getText().toString(), m);
                            final Float f = rating.getRating();
                            final String s = f.toString();
                            Log.d("Rating: ", s);
                            Review.addReview(m.getTitleYear(), review);
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Couldn't connect to remote database.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } catch (org.json.JSONException e) {
                        Log.d("ReviewActivity", "Some major error occurred");
                    }
                } catch (JSONException e) {
                    Log.d("ReviewActivity", "Some fatal error occurred");
                    Log.d("ReviewActivity", "Exception: " + e.getMessage());
                    Log.v("EXCEPTION", e.getMessage());
                    Toast.makeText(getApplicationContext(), "Error parsing JSON data.",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.d("ReviewActivity", "Some major error occurred");
                Toast.makeText(getApplicationContext(), "Couldn't get any JSON data.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

}

