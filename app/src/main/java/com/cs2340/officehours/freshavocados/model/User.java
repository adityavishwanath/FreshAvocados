package com.cs2340.officehours.freshavocados.model;

/**
 * Created by Pranathi Tupakula, Vagdevi Kondeti and Aditya Vishwanath on 06-02-2016.
 * Edited by Brandon Manuel on 15-02-2016.
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

    public void setFirstName(String first) {
        firstName = first;
    }
    public void setLastName(String last) {
        lastName = last;
    }
    public void setPass(String pass) {
        password = pass;
    }
    public void setMajor(String major) { this.major = major; }
    public void setBio(String bio) { this.bio = bio; }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public int getUserNumber() {
        return userNumber;
    }
    public String getEmailAddress() {
        return email;
    }
    public String getMajor() { return major; }
    public String getBio() {
        return bio;
    }
    public boolean checkPassword(String pass) {
        return password.equals(pass);
    }
}
