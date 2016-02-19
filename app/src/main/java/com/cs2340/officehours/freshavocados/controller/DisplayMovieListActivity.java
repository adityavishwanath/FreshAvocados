package com.cs2340.officehours.freshavocados.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cs2340.officehours.freshavocados.R;
import com.cs2340.officehours.freshavocados.model.Movie;

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

        TextView titleText = (TextView) findViewById(R.id.pageTitle);
        titleText.setText(pageTitle);

    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent i = new Intent(DisplayMovieListActivity.this, IndividualMovieActivity.class);

        i.putExtra("position", position);
        startActivity(i);
    }

}
