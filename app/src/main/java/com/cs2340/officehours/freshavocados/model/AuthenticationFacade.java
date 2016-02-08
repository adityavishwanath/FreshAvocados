package com.cs2340.officehours.freshavocados.model;

/**
 * Created by Aditya Vishwanath on 06-02-2016.
 */
public interface AuthenticationFacade {
    boolean handleLoginRequest(String userName, String password);
}

