package com.cs2340.officehours.freshavocados.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Aditya Vishwanath on 06-02-2016.
 */
public class UserManager implements AuthenticationFacade, UserManagementFacade {
    public static Map<String, User> users = new HashMap<>();


    public User findUserById(String id) {
        return users.get(id);
    }

    public boolean addUser(String firstName, String lastName, String userName, String pass) {
        if (users.containsKey(userName)) {
           return false;
        } else {
            User user = new User(firstName, lastName, userName, pass);
            users.put(userName, user);
            return true;
        }
    }

    public boolean handleLoginRequest(String userName, String pass) {
        User u = findUserById(userName);
        if (u == null) {
            return false;
        }
        return u.checkPassword(pass);
    }

    public UserManager() {
        //Empty Constructor.
    }

}
