package com.cs2340.officehours.freshavocados.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cs2340.officehours.freshavocados.R;

import org.json.JSONObject;

import java.net.URLEncoder;

public class MainActivity extends Activity {

    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queue = Volley.newRequestQueue(this);
    }

    public void onClickSearch(View v) {
        // check if corresponding text field R.id.searchField == null

        String url = "http://api.rottentomatoes.com/api/public/v1.0/movies.json?q=";

        try {
            EditText searchField = (EditText) findViewById(R.id.searchField);
            String query = searchField.getText().toString();
            String encodedQuery = URLEncoder.encode(query, "utf-8");

            url = url + encodedQuery;
            url = url + "&page_limit=10&page=1&apikey=yedukp76ffytfuy24zsqk7f5";

        } catch (Exception e) {
            Log.d("Main Activity", e.getMessage());
        }
    }

    /**
     * Log's the user out and sets the current user of the app to null
     * @param v the default param for onClick methods
     */
    public void onClickLogout(View v) {
        LoginActivity.currentUser = null;
        startActivity(new Intent(getApplicationContext(), SplashActivity.class));
        Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        a.vibrate(50);
    }

    /**
     * Brings the user to a page where they can view and edit their profile
     * @param v the default param for onClick methods
     */
    public void onClickEdit(View v) {
        startActivity(new Intent(getApplicationContext(), ViewProfileActivity.class));
        Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        a.vibrate(50);
    }
}
