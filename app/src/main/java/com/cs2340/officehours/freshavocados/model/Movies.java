package com.cs2340.officehours.freshavocados.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by brodyjohnstone on 2/21/16.
 * Potentially useless class ayylmao
 */
public final class Movies {

    /**
     * Super call for constructor
     */
    private Movies() {
        super();
    }

    /**
     * ArrayList with all of the movies
     */
    private static final ArrayList<Movie> MOVIES = new ArrayList<>();

    /**
     * Map from movie title/year to the Movie object
     */
    private static final Map<String, Movie> MOVIE_MAP = new HashMap<>();

    /**
     * Adds a movie to both the arraylist and the map
     * @param movie the movie to be added
     */
    public static void addItem(Movie movie) {
        MOVIES.add(movie);
        MOVIE_MAP.put(movie.getTitleYear(), movie);
    }

    /**
     * Gets the movie at a given position in the arraylist
     * @param position position in the arraylist
     * @return the requested movie
     */
//    public static Movie getMovieAt(int position) {
//        return MOVIES.get(position);
//    }

    /**
     * Clears the arraylist of movies
     */
//    public static void clear() {
//        MOVIES = new ArrayList<>();
//    }
}
