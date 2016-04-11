package com.cs2340.officehours.freshavocados.controller;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cs2340.officehours.freshavocados.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class NewPasswordActivity extends Activity {

//    Toast passwordChangedToast;
    /**
     * Toast for when passwords don't match
     */
    private Toast passwordsNotMatched;
    /**
     * String with the user's password
     */
    private String password;
    /**
     * Int for vibrator
     */
    private static final int VIBRATE_TIME = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
    }

    /**
     * Checks to see if the new passwords match and sets the user's password accordingly
     * @param v the default param for onClick methods
     */
    public void onClickChangePass(View v) {
        final EditText newPass = (EditText) findViewById(R.id.new_pass);
        final EditText confirmPass = (EditText) findViewById(R.id.confirm_new_pass);
        if (newPass.getText().toString().equals(confirmPass.getText().toString())) {
            final String name = LoginActivity.currentUser.getUsername();
            password = newPass.getText().toString();
            new newPassTask().execute(name, password);
        } else {
            if (passwordsNotMatched == null) {
                passwordsNotMatched = Toast.makeText(getApplicationContext(),
                        "Passwords did not match. Please try again.", Toast.LENGTH_SHORT);
            }
            passwordsNotMatched.show();
        }
        final Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        a.vibrate(VIBRATE_TIME);
    }

    /**
     * Cancels the process of changing the password and returns to the view profile screen
     * @param v the default param for onClick methods
     */
    public void onClickCancelChangePassword(View v) {
        //startActivity(new Intent(getApplicationContext(), ViewProfileActivity.class));
        finish();
        final Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        a.vibrate(VIBRATE_TIME);
    }

    /**
     * Asynchronous call to newPassTask.
     * Takes in username and password as input.
     * Connects to remote database to retrieve user information and update CurrentUser accordingly.
     */
    private class newPassTask extends AsyncTask<String, Void, String> {

        /**
         * This method immediately starts running when execute() is called.
         * Inputs are username and password.
         * It will retrieve data from php database and pass the return message to onPostExecute().
         * @param args username and password
         * @return sql database query in JSON format
         */
        @Override
        protected String doInBackground(String... args) {
            final String name = args[0];
            final String pass = args[1];

            String link;
            BufferedReader bufferedReader;
            String result;
            try {
                String data = "?username=" + URLEncoder.encode(name, "UTF-8");
                data += "&password=" + URLEncoder.encode(pass, "UTF-8");
                link = "http://officehours.netau.net/newpass.php" + data;
                final URL url = new URL(link);
                final HttpURLConnection con = (HttpURLConnection) url.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                result = bufferedReader.readLine();
                return result;
            } catch (java.io.IOException e) {
                Log.d("NewPasswordActivity", e.getMessage());
                return e.getMessage();
            }
        }

        /**
         * This method runs after doInBackground.
         * It will process the JSON result to determine any errors or if it was successful.
         * @param result JSON object retrieved from php response
         */
        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    final JSONObject jsonObj = new JSONObject(result);
                    String queryResult;
                    try {
                        Log.d("NewPasswordActivity", "Entry query_result exists");
                        queryResult = jsonObj.getString("query_result");
                        if ("SUCCESS".equals(queryResult)) {
                            Toast.makeText(getApplicationContext(), "Password changed successfully!", Toast.LENGTH_SHORT).show();
                            LoginActivity.currentUser.setPass(password);
                            finish();
                        } else if ("FAILURE".equals(queryResult)) {
                            Toast.makeText(getApplicationContext(), "Password failed to change.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Couldn't connect to remote database.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } catch (org.json.JSONException e) {
                        Log.d("NewPasswordActivity", "Some major error occurred");
                    }
                } catch (JSONException e) {
                    Log.d("RegistrationActivity", "Some fatal error occurred");
                    Log.d("RegistrationActivity", "Exception: " + e.getMessage());
                    Log.v("EXCEPTION", e.getMessage());
                    Toast.makeText(getApplicationContext(), "Error parsing JSON data.",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.d("RegistrationActivity", "Some major error occurred");
                Toast.makeText(getApplicationContext(), "Couldn't get any JSON data.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

}