package com.cs2340.officehours.freshavocados.model;

/**
 * Created by Pranathi Tupakula, Vagdevi Kondeti and Aditya Vishwanath on 06-02-2016.
 * Edited by Brandon Manuel on 15-02-2016.
 * Edited by Brody Johnstone on 15-02-2016.
 *
 * Contains information about a User.
 */
public class User {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String major;
    private String bio;
    private static int userNumber;

    /**
     *
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param username the user's username
     * @param password the user's password
     * @param email the user's email address
     * @param major the user's major
     * @param bio the user's bio
     */
    public User(String firstName, String lastName, String username, String password, String email,
                String major, String bio) {
        userNumber++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.major = major;
        this.bio = bio;
    }

    /**
     * Sets the user's password
     * @param pass the password to used
     */
    public void setPass(String pass) {
        password = pass;
    }

    /**
     * Sets the user's major
     * @param major the major to be used
     */
    public void setMajor(String major) { this.major = major; }

    /**
     * Sets the user's bio
     * @param bio the bio to be used
     */
    public void setBio(String bio) { this.bio = bio; }

    /**
     * Gets the user's first name
     * @return firstName the user's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets the user's last name
     * @return lastName the user's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Gets the user's username
     * @return username the user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the user's password
     * @return password the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the total number of current users
     * @return userNumber total number of current users
     */
    public int getUserNumber() {
        return userNumber;
    }

    /**
     * Gets the user's email address
     * @return email the user's email address
     */
    public String getEmailAddress() {
        return email;
    }

    /**
     * Gets the user's major
     * @return major the user's major
     */
    public String getMajor() { return major; }

    /**
     * Gets the user's bio
     * @return bio the user's bio
     */
    public String getBio() {
        return bio;
    }

    /**
     * Checks the given password with the user's password
     * @param pass password to be checked
     * @return true or false depending on if they are equal or not
     */
    public boolean checkPassword(String pass) {
        return password.equals(pass);
    }
}
