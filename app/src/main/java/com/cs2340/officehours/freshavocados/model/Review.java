package com.cs2340.officehours.freshavocados.model;

import android.media.Rating;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by brodyjohnstone on 2/23/16.
 */
public class Review implements Comparable<Review> {

    public static Map<String, Review> reviewMap= new HashMap<>();

    private String username;
    private String major;
    private Rating rating;
    private String review_text;
    private Movie movie;

    public Review(String username, String major, Rating rating, String review_text, Movie movie) {
        this.username = username;
        this.major = major;
        this.rating = rating;
        this.review_text = review_text;
        this.movie = movie;
    }

    public static void addReview(String movie, Review review) {
        reviewMap.put(movie, review);
    }

    //getters

    public String getUsername() {
        return username;
    }

    public String getMajor() {
        return major;
    }

    public Rating getRating() {
        return rating;
    }

    public String getReviewText() {
        return review_text;
    }

    public Movie getMovie() {
        return movie;
    }

    //setters

    public void setUsername(String username) {
        this.username = username;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public void setReviewText(String review_text) {
        this.review_text = review_text;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    @Override
    public int compareTo(Review review) {
        return ((int) this.rating.getStarRating()) - ((int) review.rating.getStarRating());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (!(o instanceof Review)) {
            return false;
        }
        Review other = (Review) o;
        return this.username.equals(other.username);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "Review by " + username; //who cares tbh
    }
}
