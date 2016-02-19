package com.cs2340.officehours.freshavocados.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Aditya Vishwanath on 19-02-2016.
 */
public class Movie implements Serializable {
    //TODO Later: Add the image/movie thumbnail to this model too.
    //TODO Later: Add the Reviews Functionality.

    private String title_year;
    private String actors;
    private String synopsis;

    /**
     * Method to set the title and year in the required display format.
     * @param title
     * @param year
     */
    private void setTitleAndYear(String title, String year) {
        title_year = title + " (" + year + ")";
    }

    /**
     * Method to set the two actors in the required format for display purposes.
     * @param actor1
     * @param actor2
     */
    private void setActors(String actor1, String actor2) {
        actors = actor1 + "  " + actor2;
    }

    /**
     * Method to initialize all the data members of the instance.
     * @param title
     * @param year
     * @param actor1
     * @param actor2
     * @param synopsis
     */
    public void setData(String title, String year, String actor1, String actor2, String synopsis) {
        setTitleAndYear(title, year);
        setActors(actor1, actor2);
        this.synopsis = synopsis;
    }

    /**
     * Getter method.
     * @return title_year
     */
    public String getTitleYear() {
        return title_year;
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
}
