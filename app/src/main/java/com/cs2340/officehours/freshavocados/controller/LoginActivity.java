package com.cs2340.officehours.freshavocados.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cs2340.officehours.freshavocados.R;
import com.cs2340.officehours.freshavocados.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class LoginActivity extends Activity {
    public static User currentUser;
    String data;

    private EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.loginUsername);
        password = (EditText) findViewById(R.id.loginPassword);
    }

    /**
     * Checks the user's username and password and attempts to log them into the system
     * @param v the default param for onClick methods
     */
    public void onClickConfirmLogin(View v) {
        Log.d("LoginActivity", "Login Button Pressed!");

        String name = username.getText().toString().trim();
        String pass = password.getText().toString();

        Log.d("LoginActivity", "Username text: " + name + " Password text: " + pass);

        //Try to log in if username and password are inputted
        if (!name.equals("") && !pass.equals("")) {
            try {
                data = "no data inputted yet";
                new LoginTask().execute(name, pass); //login task is private inner ASyncTask class
                System.out.println("Result is: " + data);
                Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                a.vibrate(50);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (name.equals("")) {
            Toast.makeText(getApplicationContext(),
                    "You  left your username field blank!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(),
                    "You left your password field blank!", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Cancels the login process and returns to the splash screen
     * @param v the default param for onClick methods
     */
    public void onClickCancelLogin(View v) {
//        startActivity(new Intent(getApplicationContext(), SplashActivity.class));
        finish();
        Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        a.vibrate(50);
    }

    /**
     * Asynchronous call to login. Takes in username and password as input. It uses hardcoded php
     * links to connect to the database and retrieve user information.
     * It will update currentuser with user details if valid user is inputted
     *
     */
    private class LoginTask extends AsyncTask<String, Void, String> {

        /**
         * This process immediately starts running when execute() is called. Inputs are username
         * and password. It will fetch from php database to see if data exists. It will pass return
         * message to onPostExecute() method
         * @param args username and password
         * @return sql database query in json format
         */
        @Override
        protected String doInBackground(String... args) {
            String name = args[0];
            String pass = args[1];
            String link;
            BufferedReader bufferedReader;
            String result = "";
            try {
                data = "?username=" + URLEncoder.encode(name, "UTF-8");
                data += "&password=" + URLEncoder.encode(pass, "UTF-8");
                link = "http://officehours.netau.net/login.php" + data;
                URL url = new URL(link);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                result = bufferedReader.readLine();
                return result;
            } catch (Exception e) {
                Log.d("LoginActivity", e.getMessage());
                return e.getMessage();
            }
        }

        /**
         * This method runs after doInBackground. It will process the json result and either use it
         * to realize that the connection failed, or use it to update the CurrentUser for the Login
         * Activity.
         * This uses a bit of a hack to process the result. When we query the database there are two
         * possible results:
         * 1) the actual object with user details - such as name, password, bio,
         * major, etc.
         * 2) An error message starting with "query_result".
         * In this, we check to see if query_result is in the result, if it is not that means the result
         * contains (1) - actual object with user results - in this case java will throw an error,
         * so we catch this error and process that json object
         * @param result
         */
        @Override
        protected void onPostExecute(String result) {
            String jsonStr = result;
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    String query_result = "";
                    try {
                        Log.d("LoginActivity", "Entry query_result exists");
                        query_result = jsonObj.getString("query_result");
                        if (query_result.equals("DENIED")) {
                            Toast.makeText(getApplicationContext(), "Login failed.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Couldn't connect to remote database.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e){
                        Log.d("LoginActivity", "Entry query_result does not exist");
                        String username = jsonObj.getString("Username");
                        String password = jsonObj.getString("Password");
                        String email = jsonObj.getString("Email");
                        String major = jsonObj.getString("Major");
                        String firstName = jsonObj.getString("FirstName");
                        String lastName = jsonObj.getString("LastName");
                        String bio = jsonObj.getString("Bio");
                        Toast.makeText(getApplicationContext(), "Login successfully. Login successful.", Toast.LENGTH_SHORT).show();
                        currentUser = new User(firstName, lastName, username, password, email,
                                major, bio);
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                } catch (JSONException e) {
                    Log.d("LoginActivity", "Some fatal error occurred");
                    Log.d("LoginActivity", "Exception: " + e.getMessage());
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error parsing JSON data.",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.d("LoginActivity", "Some major error occurred");
                Toast.makeText(getApplicationContext(), "Couldn't get any JSON data.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

}
