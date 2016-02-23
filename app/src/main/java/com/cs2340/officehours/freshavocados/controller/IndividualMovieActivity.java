package com.cs2340.officehours.freshavocados.controller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs2340.officehours.freshavocados.R;
import com.cs2340.officehours.freshavocados.model.Movie;
import com.cs2340.officehours.freshavocados.model.Movies;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class IndividualMovieActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_movie);

        ArrayList<Movie> movies = (ArrayList<Movie>) getIntent().getSerializableExtra("movies");
        int position = getIntent().getIntExtra("position", 1);
        Movie m = movies.get(position);

        TextView movie_title_year = (TextView) findViewById(R.id.movie_title_year);
        movie_title_year.setText(m.getTitleYear());

        TextView actor_actor = (TextView) findViewById(R.id.actor_actor);
        actor_actor.setText(m.getActors());

        TextView short_synop = (TextView) findViewById(R.id.short_synop);
        short_synop.setText(m.getSynopsis());

        String url = m.getThumbnailLink();
        ImageView movie_img = (ImageView) findViewById(R.id.movie_img);
        new DownloadImageTask((ImageView) findViewById(R.id.movie_img)).execute(url);
    }

    public void onClickBack(View v) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
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
