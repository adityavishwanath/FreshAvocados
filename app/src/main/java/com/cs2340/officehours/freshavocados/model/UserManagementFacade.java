package com.cs2340.officehours.freshavocados.model;

/**
 * Created by Aditya Vishwanath on 06-02-2016.
 * Edited by Brandon Manuel on 15-02-2016.
 */
public interface UserManagementFacade {
    boolean addUser(String firstName, String lastName, String userName, String pass, String email,
                    String major, String bio);
    User findUserById(String id);
}
