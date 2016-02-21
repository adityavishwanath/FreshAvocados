package com.cs2340.officehours.freshavocados.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by brodyjohnstone on 2/21/16.
 */
public class Movies {

    public static ArrayList<Movie> MOVIES = new ArrayList<>();

    public static Map<String, Movie> MOVIE_MAP = new HashMap<>();

    public static void addItem(Movie movie) {
        MOVIES.add(movie);
        MOVIE_MAP.put(movie.getTitleYear(), movie);
    }

    public static Movie getMovieAt(int position) {
        return MOVIES.get(position);
    }

    public static void clear() {
        MOVIES = new ArrayList<>();
    }
}
