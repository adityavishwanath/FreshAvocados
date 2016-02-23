package com.cs2340.officehours.freshavocados.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
//            new DownloadImageTask((ImageView) findViewById(R.id.thumbnail)).execute(url);

            return view;
        }

        public MyAdapter(Context context, int resource, int textViewResourceId, ArrayList<Movie> objects) {
            super(context, resource, textViewResourceId, objects);
        }

        @Override
        public boolean isEnabled(int position) {
            return true;
        }
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent i = new Intent(DisplayMovieListActivity.this, IndividualMovieActivity.class);

        i.putExtra("position", position);
        i.putExtra("movies", movies);
        startActivity(i);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

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

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}
