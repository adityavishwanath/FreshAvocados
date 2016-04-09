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
    private String data;

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
                    "You left your username field blank!", Toast.LENGTH_LONG).show();
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
     * Asynchronous call to LoginTask.
     * Takes in username and password as input.
     * Connects to remote database to retrieve user information and update CurrentUser accordingly.
     */
    private class LoginTask extends AsyncTask<String, Void, String> {

        /**
         * This method immediately starts running when execute() is called.
         * Inputs are username and password.
         * It will retrieve data from php database and pass the return message to onPostExecute().
         * @param args username and password
         * @return sql database query in JSON format
         */
        @Override
        protected String doInBackground(String... args) {
            String name = args[0];
            String pass = args[1];
            String link;
            BufferedReader bufferedReader;
            String result;
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
         * This method runs after doInBackground.
         * It will process the JSON result to determine any errors or if it was successful.
         * This uses a bit of a hack to process the result. When we query the database there are two
         * possible results:
         * 1) the actual object with user details (name, major, bio, etc)
         * 2) An error message starting with "query_result".
         * If there are no error messages, then we use the catch exception to retrieve the JSON object
         * containing the user information and update CurrentUser accordingly.
         * @param result JSON object retrieved from php response
         */
        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    JSONObject jsonObj = new JSONObject(result);
                    String query_result;
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
                        String isLocked = jsonObj.getString("isLocked");
                        String isBanned = jsonObj.getString("isBanned");
                        Integer adminStatus = Integer.parseInt(jsonObj.getString("IsAdmin"));
                        boolean isAdmin = (adminStatus == 1);
                        if (isBanned.equals("1")) {
                            Toast.makeText(getApplicationContext(), "Account is banned.", Toast.LENGTH_SHORT).show();
                        } else if (isLocked.equals("1")) {
                            Toast.makeText(getApplicationContext(), "Account is locked. Please contact an admin for help.", Toast.LENGTH_SHORT).show();
                        } else {
                            currentUser = new User(firstName, lastName, username, password, email,
                                    major, bio);
                            Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_SHORT).show();
                            if (!isAdmin) {
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            } else {
                                startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                            }
                            finish();
                            
                        }
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
