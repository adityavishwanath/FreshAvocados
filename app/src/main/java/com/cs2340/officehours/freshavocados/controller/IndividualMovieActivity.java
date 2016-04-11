package com.cs2340.officehours.freshavocados.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cs2340.officehours.freshavocados.R;
import com.cs2340.officehours.freshavocados.model.Movie;
import com.cs2340.officehours.freshavocados.model.Review;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class IndividualMovieActivity extends Activity implements AdapterView.OnItemClickListener {

    /**
     * Movie that will be displayed
     */
    private Movie m;
    /**
     * Int for vibrator
     */
    private static final int VIBRATE_TIME = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_movie);
        //code for stuff concerning the movie

        final ArrayList<Movie> movies = (ArrayList<Movie>) getIntent().getSerializableExtra("movies");
        final int position = getIntent().getIntExtra("position", 1);
        m = movies.get(position);

        final TextView movieTitleYear = (TextView) findViewById(R.id.movie_title_year);
        movieTitleYear.setText(m.getTitleYear());

        final TextView actorActor = (TextView) findViewById(R.id.actor_actor);
        actorActor.setText(m.getActors());

        final TextView shortSynop = (TextView) findViewById(R.id.short_synop);
        shortSynop.setText(m.getSynopsis());

        //RatingBar overallRating = (RatingBar) findViewById(R.id.overallRating);
        //overallRating.setRating(Review.getOverallRating(m.getTitleYear()));

        final String url = m.getThumbnailLink();
        new DownloadImageTask((ImageView) findViewById(R.id.movie_img)).execute(url);

        //populate the review list
        new GetReviewsTask().execute(m.getTitleYear());
        Log.d("IndividualMovieActivity", "Movie name is " + m.getTitleYear());

//        //code for the reviews
//        ListView list_view = (ListView) findViewById(R.id.review_list);
//        list_view.setOnItemClickListener(this);
//
//        if (Review.REVIEW_MAP.get(m.getTitleYear()) == null) {
//            adapt = new MyAdapter(this, R.layout.review_item, R.id.movie_title_year,
//                    new LinkedList<Review>());
//            list_view.setAdapter(adapt);
//        } else {
//            rev = Review.REVIEW_MAP.get(m.getTitleYear());
//            adapt = new MyAdapter(this, R.layout.review_item, R.id.reviewer,
//                    rev);
//            list_view.setAdapter(adapt);
//        }
    }

    /**
     * Gets all of the reviews for the on-screen movie
     * @param v the default view param
     */
    public void onClickGenerateReviewList(View v) {
        //code for the reviews
        final ListView listView = (ListView) findViewById(R.id.review_list);
        listView.setOnItemClickListener(this);

        final RatingBar overallRating = (RatingBar) findViewById(R.id.overallRating);
        overallRating.setRating(Review.getOverallRating(m.getTitleYear()));

        MyAdapter adapt;
        if (Review.REVIEW_MAP.get(m.getTitleYear()) == null) {
            Log.d("Reviews in movie?", "NO");
            adapt = new MyAdapter(this, R.id.movie_title_year,
                    new LinkedList<Review>());
            listView.setAdapter(adapt);
        } else {
            Log.d("Reviews in movie?", "YES");
            final LinkedList<Review> rev = Review.REVIEW_MAP.get(m.getTitleYear());
            adapt = new MyAdapter(this, R.id.reviewer,
                    rev);
            listView.setAdapter(adapt);
        }
    }
    /**
     * Returns the User back to the list of movies queried
     * @param v default param for an app's View
     */
    public void onClickBack(View v) {
        final Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        a.vibrate(VIBRATE_TIME);
        finish();
    }

    /**
     * Loads the review activity so that the User can leave a review for the movie
     * @param v default param for an app's View
     */
    public void onClickAddReview(View v) {
        final Intent intent = new Intent(getApplicationContext(), ReviewActivity.class);
        intent.putExtra("movie", m);
        startActivity(intent);
        final Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        a.vibrate(VIBRATE_TIME);
    }

    /**
     * Handles clicking an individual review so that it can be viewed in its entirety
     * @param parent the adapter used
     * @param view the system View
     * @param position the position in the list that the user tapped
     * @param id the id of the review
     */
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("Clicking indiv reviews", "success");
    }

    /**
     * The adapter used to display all reviews for the currently displayed movie
     */
    private class MyAdapter extends ArrayAdapter<Review> {

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final View view = super.getView(position, convertView, parent);

            final Review rev = getItem(position);

            final TextView reviewer = (TextView) view.findViewById(R.id.reviewer);
            reviewer.setText(rev.getUsername());

            final TextView major = (TextView) view.findViewById(R.id.user_major);
            major.setText(rev.getMajor());

            final RatingBar rating = (RatingBar) view.findViewById(R.id.user_rating);
            rating.setRating(rev.getRating());

            final TextView reviewText = (TextView) view.findViewById(R.id.review_text);
            reviewText.setText(rev.getReviewText());

            return view;

        }

        /**
         * Constructor for our custom adapter
         * @param context the system context
         * @param textViewResourceId one of the on-screen widgets to be edited
         * @param objects the list of Reviews to be displayed
         */
        public MyAdapter(Context context, int textViewResourceId,
                         List<Review> objects) {
            super(context, R.layout.review_item, textViewResourceId, objects);
        }
    }

    /**
     * Class that handles setting the tiny icon for an individual movie view
     */
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        /**
         * ImageView for bitmap image that will be shown
         */
        private final ImageView bmImage;

        /**
         * Constructor for our class
         * @param bmImage1 the image to be set on the screen
         */
        public DownloadImageTask(ImageView bmImage1) {
            this.bmImage = bmImage1;
        }

        /**
         * Standard doInBackground method for our AsyncTask
         * @param urls the url of the picture we are trying to set
         * @return the resulting bitmap
         */
        protected Bitmap doInBackground(String ... urls) {
            final String urlDisplay = urls[0];
            Bitmap mIcon = null;
            try {
                final InputStream in = new java.net.URL(urlDisplay).openStream();
                mIcon = BitmapFactory.decodeStream(in);
            } catch (java.net.MalformedURLException e) {
                Log.v("EXCEPTION", e.getMessage());
            } catch (java.io.IOException e) {
                Log.v("AN EXCEPTION OCCURRED", e.getMessage());
            }
            return mIcon;
        }

        /**
         * Defines how the execute method will operate
         * @param result the bitmap that will be used to set the picture
         */
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }


    /**
    * Asynchronous call to GetReviewsTask.
    * Takes in inputs: movie
    * Connects to remote database to retrieve reviews and display them accordingly.
    */
    private class GetReviewsTask extends AsyncTask<String, Void, String> {

    /**
     * This process immediately starts running when execute() is called.
     * It will fetch from php database to see if data exists. It will pass return message
     * to onPostExecute() method.
     * @param args movie
     * @return sql database query in json format
     */
        @Override
        protected String doInBackground(String... args) {
            final String movie = args[0];

            String link;
            BufferedReader bufferedReader;
            String result;
            try {
                final String data = "?movie=" + URLEncoder.encode(movie, "UTF-8");
                link = "http://officehours.netau.net/getreviews.php" + data;
                Log.d("DATA SENT", data);
                final URL url = new URL(link);
                final HttpURLConnection con = (HttpURLConnection) url.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                result = bufferedReader.readLine();
                result = result.substring(0, result.length() - 1);
                result = "{ Reviews: [" + result + "] }";
                Log.d("RESULT", result);
                return result;
            } catch (java.io.IOException e) {
                Log.d("IndividualMovieActivity", e.getMessage());
                return e.getMessage();
            }
        }

        /**
         *This method runs after doInBackground.
         * It will process the JSON result to determine any errors or if it was successful.
         * @param result JSON object retrieved from php response
         */
        @Override
        protected void onPostExecute(String result) {
            Log.d("JSONSTR", result);
            if (result != null) {
                if (result.contains("EMPTY") && result.contains("query_result")) {
                    return;
                }
                try {
                    final JSONObject jsnObject = new JSONObject(result);
                    final JSONArray array = jsnObject.getJSONArray("Reviews");
                    for (int i = 0; i < array.length(); i++) {
                        final JSONObject review = array.getJSONObject(i);
                        final String username = review.getString("Username");
                        final String comment = review.getString("Comment");
                        final String major = review.getString("Major");
                        final String ratS = review.getString("Rating");
                        final float rating = Float.parseFloat(ratS);
                        final Review r = new Review(username, major, rating, comment, m); //RATING BAR IS WRONG! (Not sure how to pass RatingBar value into database)
                        Review.addReview(m.getTitleYear(), r);
                    }
                } catch (JSONException e) {
                    Log.d("IndividualMovieActivity", "Some fatal error occurred");
                    Toast.makeText(getApplicationContext(), "Error parsing JSON data.",
                        Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Couldn't get any JSON data.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

}