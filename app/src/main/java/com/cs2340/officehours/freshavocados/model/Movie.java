package com.cs2340.officehours.freshavocados.model;

import java.io.Serializable;

/**
 * Created by Aditya Vishwanath on 19-02-2016.
 * Represents a Movie
 */
public class Movie implements Serializable {

    private String titleYear;
    private String actors;
    private String synopsis;
    private String thumbnailLink;
//    private Drawable thumbnail;

    /**
     * Method to set the title and year in the required display format.
     * @param title the title to be set
     * @param year the year to be set
     */
    private void setTitleAndYear(String title, String year) {
        titleYear = title + " (" + year + ")";
    }

    /**
     * Method to set the two actors in the required format for display purposes.
     * @param actor1 primary actor
     * @param actor2 secondary actor
     */
    private void setActors(String actor1, String actor2) {
        actors = actor1 + ",  " + actor2;
    }

    /**
     * Method to set the thumbnail for the movie
     * @param thumbnailLink the thumbnail for the movie
     */
//    private void setThumbnailLink(String thumbnailLink) {
//        this.thumbnailLink = thumbnailLink;
//    }

    /**
     * Method to initialize all the data members of the instance.
     * @param title title of movie
     * @param year year of movie
     * @param actor1 primary actor
     * @param actor2 secondary actor
     * @param synopsis1 short description of movie
     * @param thumbnailLink1 link to thumbnail
     */
    public void setData(String title, String year, String actor1, String actor2, String synopsis1,
                        String thumbnailLink1) {
        setTitleAndYear(title, year);
        setActors(actor1, actor2);
        this.synopsis = synopsis1;
        this.thumbnailLink = thumbnailLink1;
    }

    /**
     * Getter method.
     * @return title_year
     */
    public String getTitleYear() {
        return titleYear;
    }

    /**
     * Getter method.
     * @return actors
     */
    public String getActors() {
        return actors;
    }

    /**
     * Getter method.
     * @return synopsis
     */
    public String getSynopsis() {
        return synopsis;
    }

    /*
     * Getter method
     * @return thumbnailLink
     */
    public String getThumbnailLink() {
        return thumbnailLink;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (this == other) {
            return true;
        }
        if (!(other instanceof Movie)) {
            return false;
        }
        Movie that = (Movie) other;
        return this.getTitleYear().equals(that.getTitleYear());
    }

    @Override
    public int hashCode() {
        return super.hashCode() + titleYear.hashCode() * 157;
    }
}
