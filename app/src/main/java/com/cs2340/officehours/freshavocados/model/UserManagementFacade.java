package com.cs2340.officehours.freshavocados.model;

/**
 * Created by Aditya Vishwanath on 06-02-2016.
 */
public interface UserManagementFacade {
    boolean addUser(String firstName, String lastName, String userName, String pass);
    User findUserById(String id);
}
