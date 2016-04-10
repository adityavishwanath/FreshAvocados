package com.cs2340.officehours.freshavocados.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Vibrator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cs2340.officehours.freshavocados.R;
import com.cs2340.officehours.freshavocados.model.Movie;
import com.cs2340.officehours.freshavocados.model.Movies;

import java.util.ArrayList;

public class DisplayMovieListActivity extends Activity implements AdapterView.OnItemClickListener {

    /**
     * ArrayList of movies
     */
    private ArrayList<Movie> movies;
    /**
     * Int with time for vibrator
     */
    private final static int VIBRATE_TIME = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movie_list);
        final ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setOnItemClickListener(this);

        final String pageTitle = (String) getIntent().getSerializableExtra("title");
        final TextView titleText = (TextView) findViewById(R.id.pageTitle);
        titleText.setText(pageTitle);
        movies = (ArrayList<Movie>) getIntent().getSerializableExtra("movies");
        for (Movie m : movies) {
            Movies.addItem(m);
        }
        listView.setAdapter(new MyAdapter(this, movies));

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
     * Allows clicking an individual movie in the list, loading a new screen with more info
     * on that movie.
     * @param parent the adapter used for the list
     * @param view the system view
     * @param position the position on the list that was touched
     * @param id the id of the movie
     */
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        final Intent i = new Intent(DisplayMovieListActivity.this, IndividualMovieActivity.class);

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
        final Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        a.vibrate(VIBRATE_TIME);
        finish();
    }

    /**
     * Class that sets our custom adapter for the listview of movies
     */
    private class MyAdapter extends ArrayAdapter<Movie> {

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final View view = super.getView(position, convertView, parent);

            final Movie mov = getItem(position);
            assert mov != null;

            final TextView titleYear = (TextView) view.findViewById(R.id.title_year);
            titleYear.setText(mov.getTitleYear());

            final TextView actors = (TextView) view.findViewById(R.id.actors);
            actors.setText(mov.getActors());

//            String url = mov.getThumbnailLink();
//            ImageView thumbnail = (ImageView) findViewById(R.id.thumbnail);
//            new DownloadImageTask(thumbnail).execute(url);

            return view;
        }

        /**
         * Constructor for our custom adapter
         * @param context the system's current context
         * @param objects the list of movies to be displayed
         */
        public MyAdapter(Context context, ArrayList<Movie> objects) {
            super(context, R.layout.list_item, R.id.title_year, objects);
        }

        @Override
        public boolean isEnabled(int position) {
            return true;
        }
    }

}
