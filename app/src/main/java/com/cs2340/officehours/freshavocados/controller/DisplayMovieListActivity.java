package com.cs2340.officehours.freshavocados.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cs2340.officehours.freshavocados.R;
import com.cs2340.officehours.freshavocados.model.Movie;
import com.cs2340.officehours.freshavocados.model.Movies;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DisplayMovieListActivity extends Activity implements AdapterView.OnItemClickListener {

    private ArrayList<Movie> movies;
    private String pageTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movie_list);
        ListView list_view = (ListView) findViewById(R.id.list_view);
        list_view.setOnItemClickListener(this);

        pageTitle = (String) getIntent().getSerializableExtra("title");
        TextView titleText = (TextView) findViewById(R.id.pageTitle);
        titleText.setText(pageTitle);
        movies = (ArrayList<Movie>) getIntent().getSerializableExtra("movies");
        for (Movie m : movies) {
            Movies.addItem(m);
        }
        list_view.setAdapter(new MyAdapter(this, R.layout.list_item, R.id.title_year, movies));

        /*
        Bundle extras = getIntent().getExtras();
        movies = (ArrayList<Movie>) extras.get("movies");
        pageTitle = (String) extras.get("title");
        ArrayList<String> movieTitles = new ArrayList<>();
        for (Movie movie: movies) {
            movieTitles.add(movie.getTitleYear());
        }
        ArrayAdapter movieAdapter = new ArrayAdapter(this, 0, movieTitles);
        // Attach the adapter to a ListView
        moviesList.setAdapter(movieAdapter);
        */

    }

    /**
     * Class that sets our custom adapter for the listview of movies
     */
    private class MyAdapter extends ArrayAdapter<Movie> {

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);

            Movie mov = (Movie) getItem(position);
            assert mov != null;

            TextView title_year = (TextView) view.findViewById(R.id.title_year);
            title_year.setText(mov.getTitleYear());

            TextView actors = (TextView) view.findViewById(R.id.actors);
            actors.setText(mov.getActors());

//            String url = mov.getThumbnailLink();
//            ImageView thumbnail = (ImageView) findViewById(R.id.thumbnail);
//            new DownloadImageTask(thumbnail).execute(url);

            return view;
        }

        /**
         * Constructor for our custom adapter
         * @param context the system's current context
         * @param resource the indexing location of the movies
         * @param textViewResourceId one of the on-screen widgets to be set
         * @param objects the list of movies to be displayed
         */
        public MyAdapter(Context context, int resource, int textViewResourceId, ArrayList<Movie> objects) {
            super(context, resource, textViewResourceId, objects);
        }

        @Override
        public boolean isEnabled(int position) {
            return true;
        }
    }

    /**
     * Allows clicking an individual movie in the list, loading a new screen with more info
     * on that movie.
     * @param parent the adapter used for the list
     * @param view the system view
     * @param position the position on the list that was touched
     * @param id the id of the movie
     */
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent i = new Intent(DisplayMovieListActivity.this, IndividualMovieActivity.class);

        i.putExtra("position", position);
        i.putExtra("movies", movies);
        startActivity(i);
    }

//    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
//        ImageView thumbnail;
//
//        public DownloadImageTask(ImageView thumbnail) {
//            this.thumbnail = thumbnail;
//        }
//
//        protected Bitmap doInBackground(String ... urls) {
//            String urlDisplay = urls[0];
//            Bitmap mIcon = null;
//            try {
//                InputStream in = new java.net.URL(urlDisplay).openStream();
//                mIcon = BitmapFactory.decodeStream(in);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return mIcon;
//        }
//
//        protected void onPostExecute(Bitmap result) {
//            if (thumbnail != null) {
//                thumbnail.setImageBitmap(result);
//            } else {
//                Log.d("DisplayListActivity", "wtf is going on");
//            }
//
//        }
//    }

    /**
     * Returns the user back to the main screen of the application
     * @param v default param for an app's View
     */
    public void onClickBackButton(View v) {
        Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        a.vibrate(50);
        finish();
    }

}
