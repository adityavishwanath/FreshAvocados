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

public class IndividualMovieActivity extends Activity implements AdapterView.OnItemClickListener {

    String data;
    private Movie m;
    private LinkedList<Review> rev;
    private MyAdapter adapt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_movie);
        //code for stuff concerning the movie

        ArrayList<Movie> movies = (ArrayList<Movie>) getIntent().getSerializableExtra("movies");
        int position = getIntent().getIntExtra("position", 1);
        m = movies.get(position);

        TextView movie_title_year = (TextView) findViewById(R.id.movie_title_year);
        movie_title_year.setText(m.getTitleYear());

        TextView actor_actor = (TextView) findViewById(R.id.actor_actor);
        actor_actor.setText(m.getActors());

        TextView short_synop = (TextView) findViewById(R.id.short_synop);
        short_synop.setText(m.getSynopsis());

        //RatingBar overallRating = (RatingBar) findViewById(R.id.overallRating);
        //overallRating.setRating(Review.getOverallRating(m.getTitleYear()));

        String url = m.getThumbnailLink();
        new DownloadImageTask((ImageView) findViewById(R.id.movie_img)).execute(url);

        //populate the review list
        new GetReviewsTask().execute(m.getTitleYear());
        Log.d("IndividualMovieActivity", "Movie name is " + m.getTitleYear());

//        //code for the reviews
//        ListView list_view = (ListView) findViewById(R.id.review_list);
//        list_view.setOnItemClickListener(this);
//
//        if (Review.reviewMap.get(m.getTitleYear()) == null) {
//            adapt = new MyAdapter(this, R.layout.review_item, R.id.movie_title_year,
//                    new LinkedList<Review>());
//            list_view.setAdapter(adapt);
//        } else {
//            rev = Review.reviewMap.get(m.getTitleYear());
//            adapt = new MyAdapter(this, R.layout.review_item, R.id.reviewer,
//                    rev);
//            list_view.setAdapter(adapt);
//        }
    }

    public void onClickGenerateReviewList(View v) {
        //code for the reviews
        ListView list_view = (ListView) findViewById(R.id.review_list);
        list_view.setOnItemClickListener(this);

        RatingBar overallRating = (RatingBar) findViewById(R.id.overallRating);
        overallRating.setRating(Review.getOverallRating(m.getTitleYear()));

        if (Review.reviewMap.get(m.getTitleYear()) == null) {
            Log.d("Reviews in movie?", "NO");
            adapt = new MyAdapter(this, R.layout.review_item, R.id.movie_title_year,
                    new LinkedList<Review>());
            list_view.setAdapter(adapt);
        } else {
            Log.d("Reviews in movie?", "YES");
            rev = Review.reviewMap.get(m.getTitleYear());
            adapt = new MyAdapter(this, R.layout.review_item, R.id.reviewer,
                    rev);
            list_view.setAdapter(adapt);
        }
    }
    /**
     * Returns the User back to the list of movies queried
     * @param v default param for an app's View
     */
    public void onClickBack(View v) {
        Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        a.vibrate(50);
        finish();
    }

    /**
     * Loads the review activity so that the User can leave a review for the movie
     * @param v default param for an app's View
     */
    public void onClickAddReview(View v) {
        Intent intent = new Intent(getApplicationContext(), ReviewActivity.class);
        intent.putExtra("movie", m);
        startActivity(intent);
        Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        a.vibrate(50);
    }

    /**
     * The adapter used to display all reviews for the currently displayed movie
     */
    private class MyAdapter extends ArrayAdapter<Review> {

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);

            Review rev = (Review) getItem(position);

            TextView reviewer = (TextView) view.findViewById(R.id.reviewer);
            reviewer.setText(rev.getUsername());

            TextView major = (TextView) view.findViewById(R.id.user_major);
            major.setText(rev.getMajor());

            RatingBar rating = (RatingBar) view.findViewById(R.id.user_rating);
            rating.setRating(rev.getRating());

            TextView review_text = (TextView) view.findViewById(R.id.review_text);
            review_text.setText(rev.getReviewText());

            return view;

        }

        /**
         * Constructor for our custom adapter
         * @param context the system context
         * @param resource the indexing location of our list
         * @param textViewResourceId one of the on-screen widgets to be edited
         * @param objects the list of Reviews to be displayed
         */
        public MyAdapter(Context context, int resource, int textViewResourceId,
                         LinkedList<Review> objects) {
            super(context, resource, textViewResourceId, objects);
        }
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
     * Class that handles setting the tiny icon for an individual movie view
     */
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        /**
         * Constructor for our class
         * @param bmImage the image to be set on the screen
         */
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        /**
         * Standard doInBackground method for our AsyncTask
         * @param urls the url of the picture we are trying to set
         * @return the resulting bitmap
         */
        protected Bitmap doInBackground(String ... urls) {
            String urlDisplay = urls[0];
            Bitmap mIcon = null;
            try {
                InputStream in = new java.net.URL(urlDisplay).openStream();
                mIcon = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
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
        String movie = args[0];

        String link;
        BufferedReader bufferedReader;
        String result = "";
        try {
            data = "?movie=" + URLEncoder.encode(movie, "UTF-8");
           link = "http://officehours.netau.net/getreviews.php" + data;
            Log.d("DATA SENT", data);
            URL url = new URL(link);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            result = bufferedReader.readLine();
            result = result.substring(0, result.length() - 1);
            result = "{ Reviews: [" + result + "] }";
            Log.d("RESULT", result);
            return result;
        } catch (Exception e) {
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
        String jsonStr = result;
        Log.d("JSONSTR", jsonStr);
        if (jsonStr != null) {
            if (jsonStr.contains("EMPTY") && jsonStr.contains("query_result")) return;
            try {
                JSONObject jsnObject = new JSONObject(jsonStr);
                JSONArray array = jsnObject.getJSONArray("Reviews");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject review = array.getJSONObject(i);
                    String username = review.getString("Username");
                    String comment = review.getString("Comment");
                    String major = review.getString("Major");
                    String ratS = review.getString("Rating");
                    float rating = Float.parseFloat(ratS);
                    Review r = new Review(username, major, rating, comment, m); //RATING BAR IS WRONG! (Not sure how to pass RatingBar value into database)
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