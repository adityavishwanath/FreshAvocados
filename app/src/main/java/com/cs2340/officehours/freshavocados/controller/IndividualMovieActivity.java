package com.cs2340.officehours.freshavocados.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.cs2340.officehours.freshavocados.R;
import com.cs2340.officehours.freshavocados.model.Movie;
import com.cs2340.officehours.freshavocados.model.Movies;
import com.cs2340.officehours.freshavocados.model.Review;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;

public class IndividualMovieActivity extends Activity implements AdapterView.OnItemClickListener {

    private Movie m;
    private LinkedList<Review> rev;

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

        String url = m.getThumbnailLink();
        new DownloadImageTask((ImageView) findViewById(R.id.movie_img)).execute(url);

        //code for the reviews
        ListView list_view = (ListView) findViewById(R.id.review_list);
        list_view.setOnItemClickListener(this);
        if (Review.reviewMap.get(m.getTitleYear()) == null) {
            list_view.setAdapter(new MyAdapter(this, R.layout.review_item, R.id.movie_title_year,
                    new LinkedList<Review>()));
        } else {
            rev = Review.reviewMap.get(m.getTitleYear());
            list_view.setAdapter(new MyAdapter(this, R.layout.review_item, R.id.reviewer,
                    rev));
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
            rating.setRating(rev.getRating().getRating());

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
}
