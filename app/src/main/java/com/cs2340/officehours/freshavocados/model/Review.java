package com.cs2340.officehours.freshavocados.model;

import android.media.Rating;
import android.widget.RatingBar;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by brodyjohnstone on 2/23/16.
 */
public class Review implements Comparable<Review> {

    public static Map<String, LinkedList<Review>> reviewMap= new HashMap<>();

    private String username;
    private String major;
    private RatingBar rating;
    private String review_text;
    private Movie movie;

    public Review(String username, String major, RatingBar rating, String review_text, Movie movie) {
        this.username = username;
        this.major = major;
        this.rating = rating;
        this.review_text = review_text;
        this.movie = movie;
    }

    public static void addReview(String movie, Review review) {
        LinkedList<Review> reviews = reviewMap.get(movie);
        if (!(reviews.contains(review))) {
            reviews.add(review);
        }
        reviewMap.put(movie, reviews);
    }

    //getters

    public String getUsername() {
        return username;
    }

    public String getMajor() {
        return major;
    }

    public RatingBar getRating() {
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

    public void setRating(RatingBar rating) {
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
        return this.rating.getNumStars() - review.rating.getNumStars();
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
        return username.hashCode() * 43 * review_text.hashCode() * 19 * major.hashCode() * 17;
    }

    @Override
    public String toString() {
        return "Review by " + username; //who cares tbh
    }
}
