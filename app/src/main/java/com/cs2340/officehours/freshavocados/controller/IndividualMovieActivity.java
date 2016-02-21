package com.cs2340.officehours.freshavocados.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cs2340.officehours.freshavocados.R;
import com.cs2340.officehours.freshavocados.model.Movie;
import com.cs2340.officehours.freshavocados.model.Movies;

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

    }

    public void onClickBack(View v) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}
