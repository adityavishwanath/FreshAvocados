package com.cs2340.officehours.freshavocados.controller;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.ExecutorDelivery;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cs2340.officehours.freshavocados.R;
import com.cs2340.officehours.freshavocados.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends Activity {

    private RequestQueue queue;
    private Toast emptySearch;
    private Toast JSONFailure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queue = Volley.newRequestQueue(this);
    }

    /**
     * Method that handles the clicking of "Search" after entering the search query.
     * @param v the view
     */
    public void onClickSearch(View v) {
        // check if corresponding text field R.id.searchField == null
        EditText searchField = (EditText) findViewById(R.id.searchField);
        searchField.setHorizontallyScrolling(false);
        searchField.setMaxLines(1);
        if (searchField.getText().toString().equals("")) {
            if (emptySearch == null) {
                emptySearch = Toast.makeText(getApplicationContext(),
                        "The search field is empty", Toast.LENGTH_SHORT);
            }
            emptySearch.show();
            return;
        }
        final String BASE_URL = "http://api.rottentomatoes.com/api/public/v1.0/movies.json?";
        final String QUERY_PARAM = "q";
        final String PAGE_LIMIT_PARAM = "page_limit";
        final String PAGE_PARAM = "page";
        final String API_KEY_PARAM = "apikey";

        final String PAGE_LIMIT = "25";
        final String PAGE = "1";
        final String API_KEY = "yedukp76ffytfuy24zsqk7f5";


        String query = searchField.getText().toString();
        String encodedQuery = "";
        try {
            encodedQuery = URLEncoder.encode(query, "utf-8");
        } catch(Exception e) {

        }
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM, encodedQuery)
                .appendQueryParameter(PAGE_LIMIT_PARAM, PAGE_LIMIT)
                .appendQueryParameter(PAGE_PARAM, PAGE)
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
                .build();

        String url = builtUri.toString();
        Log.d("MAINACTIVITY", url);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject resp) {
                        //handle a valid response coming back.  Getting this string mainly for debug
                        String response = resp.toString();
                        Log.d("MAINACTIVITY", response);
                        JSONArray array = resp.optJSONArray("movies");
                        ArrayList<Movie> movies = new ArrayList<>();
                        for(int i = 0; i < array.length(); i++) {
                            try {
                                //for each array element, we have to create an object
                                JSONObject jsonObject = array.getJSONObject(i);
                                Movie m = new Movie();
                                assert jsonObject != null;
                                String title = jsonObject.optString("title");
                                String year = jsonObject.optString("year");
                                String actor1 = jsonObject.optJSONArray("abridged_cast").getJSONObject(0).optString("name");
                                String actor2 = jsonObject.optJSONArray("abridged_cast").getJSONObject(1).optString("name");
                                String synopsis = jsonObject.optString("synopsis");
                                JSONObject links = jsonObject.getJSONObject("posters");
                                String thumbnailLink = links.getString("thumbnail");

                                Log.d("MAINACTIVITY", title);
                                Log.d("MAINACTIVITY", year);
                                Log.d("MAINACTIVITY", actor1);
                                Log.d("MAINACTIVITY", actor2);
                                Log.d("MAINACTIVITY", synopsis);
                                m.setData(title, year, actor1, actor2, synopsis, thumbnailLink);
                                movies.add(m);
                            } catch (JSONException e) {
                                Log.d("VolleyApp", "Failed to get JSON object");
                                e.printStackTrace();
                            }
                        }
                        //once we have all data, then go to list screen
                        changeView(movies, "Search Results");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String response = "JSON Request Failed!";
                        if (JSONFailure == null) {
                            JSONFailure = Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT);
                        }
                        JSONFailure.show();
                    }
                });
        //this actually queues up the async response with Volley
        queue.add(jsObjRequest);
    }

    /**
     * This method changes to our new list view of the states, but we have to pass the
     * state array into the intent so the new screen gets the data.
     *
     * @param movies the list of Movie objects we created from the JSON response
     */
    private void changeView(ArrayList<Movie> movies, String title) {
        Intent intent = new Intent(this, DisplayMovieListActivity.class);
        intent.putExtra("movies", movies);
        intent.putExtra("title", title);
        /*
        Bundle extras = new Bundle();
        extras.putSerializable("MOVIES", movies);
        extras.putString("TITLE", title);
        intent.putExtras(extras);
        */
        startActivity(intent);
    }


    public void onClickTopRentals(View V) {
        final String BASE_URL = "http://api.rottentomatoes.com/api/public/v1.0/lists/dvds/top_rentals.json?limit=25&country=us&apikey=yedukp76ffytfuy24zsqk7f5";
        try {
            String url = BASE_URL.toString();
            Log.d("MAINACTIVITY", url);

            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject resp) {
                            //handle a valid response coming back.  Getting this string mainly for debug
                            String response = resp.toString();
                            Log.d("MAINACTIVITY", response);
                            JSONArray array = resp.optJSONArray("movies");
                            ArrayList<Movie> movies = new ArrayList<>();
                            for(int i = 0; i < array.length(); i++) {
                                try {
                                    //for each array element, we have to create an object
                                    JSONObject jsonObject = array.getJSONObject(i);
                                    Movie m = new Movie();
                                    assert jsonObject != null;
                                    String title = jsonObject.optString("title");
                                    String year = jsonObject.optString("year");
                                    String actor1 = jsonObject.optJSONArray("abridged_cast").getJSONObject(0).optString("name");
                                    String actor2 = jsonObject.optJSONArray("abridged_cast").getJSONObject(1).optString("name");
                                    String synopsis = jsonObject.optString("synopsis");
                                    JSONObject links = jsonObject.getJSONObject("posters");
                                    String thumbnailLink = links.getString("thumbnail");
                                    Log.d("MAINACTIVITY", title);
                                    Log.d("MAINACTIVITY", year);
                                    Log.d("MAINACTIVITY", actor1);
                                    Log.d("MAINACTIVITY", actor2);
                                    Log.d("MAINACTIVITY", synopsis);
                                    Log.d("MAINACTIVITY", thumbnailLink);
                                    m.setData(title, year, actor1, actor2, synopsis, thumbnailLink);
                                    movies.add(m);
                                } catch (JSONException e) {
                                    Log.d("VolleyApp", "Failed to get JSON object");
                                    e.printStackTrace();
                                }
                            }
                            //once we have all data, then go to list screen
                            changeView(movies, "Top Rentals");
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            String response = "JSON Request Failed!";
                            if (JSONFailure == null) {
                                JSONFailure = Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT);
                            }
                            JSONFailure.show();
                        }
                    });
            //this actually queues up the async response with Volley
            queue.add(jsObjRequest);

        } catch (Exception e) {
            Log.d("MainActivity", e.getMessage());
        }
    }

    public void onClickNewTheatres(View v) {
        final String BASE_URL = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/in_theaters.json?page_limit=25&page=1&country=us&apikey=yedukp76ffytfuy24zsqk7f5";
        try {
            String url = BASE_URL.toString();
            Log.d("MAINACTIVITY", url);

            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject resp) {
                            //handle a valid response coming back.  Getting this string mainly for debug
                            String response = resp.toString();
                            Log.d("MAINACTIVITY", response);
                            JSONArray array = resp.optJSONArray("movies");
                            ArrayList<Movie> movies = new ArrayList<>();
                            for(int i = 0; i < array.length(); i++) {
                                try {
                                    //for each array element, we have to create an object
                                    JSONObject jsonObject = array.getJSONObject(i);
                                    Movie m = new Movie();
                                    assert jsonObject != null;
                                    String title = jsonObject.optString("title");
                                    String year = jsonObject.optString("year");
                                    String actor1 = jsonObject.optJSONArray("abridged_cast").getJSONObject(0).optString("name");
                                    String actor2 = jsonObject.optJSONArray("abridged_cast").getJSONObject(1).optString("name");
                                    String synopsis = jsonObject.optString("synopsis");
                                    JSONObject links = jsonObject.getJSONObject("posters");
                                    String thumbnailLink = links.getString("thumbnail");
                                    Log.d("MAINACTIVITY", title);
                                    Log.d("MAINACTIVITY", year);
                                    Log.d("MAINACTIVITY", actor1);
                                    Log.d("MAINACTIVITY", actor2);
                                    Log.d("MAINACTIVITY", synopsis);
                                    m.setData(title, year, actor1, actor2, synopsis, thumbnailLink);
                                    movies.add(m);
                                } catch (JSONException e) {
                                    Log.d("VolleyApp", "Failed to get JSON object");
                                    e.printStackTrace();
                                }
                            }
                            //once we have all data, then go to list screen
                            changeView(movies, "In Theatres Now");
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            String response = "JSON Request Failed!";
                            if (JSONFailure == null) {
                                JSONFailure = Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT);
                            }
                            JSONFailure.show();
                        }
                    });
            //this actually queues up the async response with Volley
            queue.add(jsObjRequest);

        } catch (Exception e) {
            Log.d("MainActivity", e.getMessage());
        }
    }

    public void onClickNewDVD(View v) {
        final String BASE_URL = "http://api.rottentomatoes.com/api/public/v1.0/lists/dvds/new_releases.json?page_limit=25&page=1&country=us&apikey=yedukp76ffytfuy24zsqk7f5";
        try {
            String url = BASE_URL.toString();
            Log.d("MAINACTIVITY", url);

            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject resp) {
                            //handle a valid response coming back.  Getting this string mainly for debug
                            String response = resp.toString();
                            Log.d("MAINACTIVITY", response);
                            JSONArray array = resp.optJSONArray("movies");
                            ArrayList<Movie> movies = new ArrayList<>();
                            for(int i = 0; i < array.length(); i++) {
                                try {
                                    //for each array element, we have to create an object
                                    JSONObject jsonObject = array.getJSONObject(i);
                                    Movie m = new Movie();
                                    assert jsonObject != null;
                                    String title = jsonObject.optString("title");
                                    String year = jsonObject.optString("year");
                                    String actor1 = jsonObject.optJSONArray("abridged_cast").getJSONObject(0).optString("name");
                                    String actor2 = jsonObject.optJSONArray("abridged_cast").getJSONObject(1).optString("name");
                                    String synopsis = jsonObject.optString("synopsis");
                                    JSONObject links = jsonObject.getJSONObject("posters");
                                    String thumbnailLink = links.getString("thumbnail");
                                    Log.d("MAINACTIVITY", title);
                                    Log.d("MAINACTIVITY", year);
                                    Log.d("MAINACTIVITY", actor1);
                                    Log.d("MAINACTIVITY", actor2);
                                    Log.d("MAINACTIVITY", synopsis);
                                    m.setData(title, year, actor1, actor2, synopsis, thumbnailLink);
                                    movies.add(m);
                                } catch (JSONException e) {
                                    Log.d("VolleyApp", "Failed to get JSON object");
                                    e.printStackTrace();
                                }
                            }
                            //once we have all data, then go to list screen
                            changeView(movies, "New DVD Releases");
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            String response = "JSON Request Failed!";
                            if (JSONFailure == null) {
                                JSONFailure = Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT);
                            }
                            JSONFailure.show();
                        }
                    });
            //this actually queues up the async response with Volley
            queue.add(jsObjRequest);

        } catch (Exception e) {
            Log.d("MainActivity", e.getMessage());
        }
    }

    public void onClickRecommendedMajor(View v) {
        //TODO
    }

    public void onClickRecommendedAll(View v) {
        //TODO
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
