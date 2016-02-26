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

    Toast passwordChangedToast;
    Toast passwordsNotMatched;
    protected String password;
    String data;
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
        EditText newPass = (EditText) findViewById(R.id.new_pass);
        EditText confirmPass = (EditText) findViewById(R.id.confirm_new_pass);
        if (newPass.getText().toString().equals(confirmPass.getText().toString())) {
            String name = LoginActivity.currentUser.getUsername();
            password = newPass.getText().toString();
            new newPassTask().execute(name, password);
        } else {
            if (passwordsNotMatched == null) {
                passwordsNotMatched = Toast.makeText(getApplicationContext(),
                        "Passwords did not match. Please try again.", Toast.LENGTH_SHORT);
            }
            passwordsNotMatched.show();
        }
        Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        a.vibrate(50);
    }

    /**
     * Cancels the process of changing the password and returns to the view profile screen
     * @param v the default param for onClick methods
     */
    public void onClickCancelChangePassword(View v) {
        //startActivity(new Intent(getApplicationContext(), ViewProfileActivity.class));
        finish();
        Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        a.vibrate(50);
    }

    private class newPassTask extends AsyncTask<String, Void, String> {

        /**
         * This process immediately starts running when execute() is called. Inputs are username
         * and password. It will fetch from php database to see if data exists. It will pass return
         * message to onPostExecute() method
         *
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
                link = "http://officehours.netau.net/newpass.php" + data;
                URL url = new URL(link);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                result = bufferedReader.readLine();
                return result;
            } catch (Exception e) {
                Log.d("NewPasswordActivity", e.getMessage());
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
                        Log.d("NewPasswordActivity", "Entry query_result exists");
                        query_result = jsonObj.getString("query_result");
                        if (query_result.equals("SUCCESS")) {
                            Toast.makeText(getApplicationContext(), "Password changed successfully!", Toast.LENGTH_SHORT).show();
                            LoginActivity.currentUser.setPass(password);
                            System.out.println("PASS CHANGED");
                            finish();
                        } else if (query_result.equals("FAILURE")) {
                            Toast.makeText(getApplicationContext(), "Password failed to change.", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Couldn't connect to remote database.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e){

                    }
                } catch (JSONException e) {
                    Log.d("RegistrationActivity", "Some fatal error occurred");
                    Log.d("RegistrationActivity", "Exception: " + e.getMessage());
                    e.printStackTrace();
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