package com.cs2340.officehours.freshavocados.controller;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cs2340.officehours.freshavocados.R;

public class DisplayMovieListActivity extends Activity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movie_list);

        ListView list_view = (ListView) findViewById(R.id.list_view);

        list_view.setOnItemClickListener(this);


    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent i = new Intent(DisplayMovieListActivity.this, IndividualMovieActivity.class);

        i.putExtra("position", position);
        startActivity(i);
    }

}
