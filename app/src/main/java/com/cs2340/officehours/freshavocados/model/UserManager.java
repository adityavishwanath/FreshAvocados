package com.cs2340.officehours.freshavocados.model;

/**
 * Created by Aditya Vishwanath on 02-06-2016.
 * Edited by Brandon Manuel on 15-02-2016.
 */
//class UserManager implements AuthenticationFacade, UserManagementFacade {
//    private static final Map<String, User> USERS = new HashMap<>();
//
//    /**
//     * Returns the user by id
//     * @param id the ID to be found
//     * @return User the associated user
//     */
//    private User findUserById(String id) {
//        return USERS.get(id);
//    }
//
//    /**
//     * Adds a User to the system
//     * @param firstName user's first name
//     * @param lastName user's last name
//     * @param userName user's username
//     * @param pass user's password
//     * @param email user's email
//     * @param major user's major
//     * @param bio user's bio
//     * @return true or false depending on success
//     */
//    public boolean addUser(String firstName, String lastName, String userName, String pass,
//                           String email, String major, String bio) {
//        if (USERS.containsKey(userName)) {
//           return false;
//        } else {
//            User user = new User(firstName, lastName, userName, pass, email, major, bio);
//            USERS.put(userName, user);
//            return true;
//        }
//    }
//
//    /**
//     * Handles a login request by a User
//     * @param userName the username to be checked if it is valid
//     * @param pass the password to be checked if it is valid and associated with the given username
//     * @return true or false depending on the success
//     */
//    public boolean handleLoginRequest(String userName, String pass) {
//        User u = findUserById(userName);
//        return u != null && u.checkPassword(pass);
//    }
//
//// --Commented out by Inspection START (4/10/16, 12:41 PM):
////    /**
////     * No-arg constructor for a UserManager
////     */
////    public UserManager() {
////        //Empty Constructor.
////    }
//// --Commented out by Inspection STOP (4/10/16, 12:41 PM)
//
//}
