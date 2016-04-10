package com.cs2340.officehours.freshavocados.model;


import android.support.annotation.NonNull;

import com.cs2340.officehours.freshavocados.controller.LoginActivity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by brodyjohnstone on 2/23/16.
 */
public class Review implements Comparable<Review> {

    public static final Map<String, LinkedList<Review>> reviewMap= new HashMap<>();

    private final String username;
    private String major;
    private final float rating;
    private final String review_text;
    private final Movie movie;

    /**
     * Constructor for an instance of a Review
     * @param username username of the reviewer
     * @param major major of the reviewer
     * @param rating given to the movie
     * @param review_text the text written about the movie
     * @param movie the reviewed movie
     */
    public Review(String username, String major, float rating, String review_text, Movie movie) {
        this.username = username;
        this.major = major;
        this.rating = rating;
        this.review_text = review_text;
        this.movie = movie;
    }

    /**
     * Adds a review to the reviewMap
     * @param movie the name of the movie
     * @param review the corresponding review
     */
    public static void addReview(String movie, Review review) {
        LinkedList<Review> reviews = reviewMap.get(movie);
        if (reviews == null) {
            reviews = new LinkedList<>();
        }
        if (!(reviews.contains(review))) {
            reviews.add(review); //if user has already made a review, do nothing
        } else {
            reviews.remove(review);
            review.setMajor(LoginActivity.currentUser.getMajor());
            reviews.add(review);
        }
        reviewMap.put(movie, reviews);
    }

    /**
     * Returns the overall rating for a requested movie
     * @param movie the requested movie
     * @return the movie's overall rating
     */
    public static float getOverallRating(String movie) {
        LinkedList<Review> temp = reviewMap.get(movie);
        float rating = 0;
        if (temp == null) {
            return rating;
        }
        for (Review r : temp) {
            rating = rating + r.getRating();
        }
        return rating / temp.size();
    }

    //getters

    /**
     * Returns the username of the reviewer
     * @return username the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the major of the reviewer
     * @return major the major
     */
    public String getMajor() {
        return major;
    }

    /**
     * Returns the rating given
     * @return rating the rating given
     */
    public float getRating() {
        return rating;
    }

    /**
     * Returns the review text given
     * @return review_text the review text given
     */
    public String getReviewText() {
        return review_text;
    }

    /**
     * Returns the reviewed movie
     * @return movie the reviewed movie
     */
    public Movie getMovie() {
        return movie;
    }

    //setters

    /**
     * Sets the username of the reviewer
     * @param username username to be set
     */
//    public void setUsername(String username) {
//        this.username = username;
//    }

    /**
     * Sets the major of the reviewer
     * @param major major to be set
     */
    private void setMajor(String major) {
        this.major = major;
    }

    /**
     * Sets the rating of the review
     * @param rating rating to be set
     */
//    public void setRating(float rating) {
//        this.rating = rating;
//    }

    /**
     * Sets the review text of the review
     * @param review_text text to be set
     */
//    public void setReviewText(String review_text) {
//        this.review_text = review_text;
//    }

    /**
     * Sets what movie was reviewed
//     * @param movie movie to be set
     */
//    public void setMovie(Movie movie) {
//        this.movie = movie;
//    }

    @Override
    public int compareTo(@NonNull Review review) {
        return (int) review.getRating() - (int) this.getRating();
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
        final int num1 = 43;
        final int num2 = 19;
        final int num3 = 17;
        return username.hashCode() * num1 * review_text.hashCode() * num2 * major.hashCode() * num3;
    }

    @Override
    public String toString() {
        return "Review by " + username; //who cares tbh
    }
}
