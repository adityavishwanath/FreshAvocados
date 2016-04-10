package com.cs2340.officehours.freshavocados.controller;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.cs2340.officehours.freshavocados.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;

public class EditProfileActivity extends Activity {
    /**
     * String for the user's new major
     */
    private String newMajor;
    /**
     * String for the user's new bio
     */
    private EditText newBio;
    /**
     * Int for the timer for the vibrator
     */
    private final static int VIBRATE_TIME = 50;
    /**
     * Int for max number of lines on bio
     */
    private final static int MAX_LINES = 6;
    /**
     * Int for name of activity
     */
    private final static String ACTIVITYNAME = "EditProfileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        final EditText bio = (EditText) findViewById(R.id.bioEdit);
        bio.setHorizontallyScrolling(false);
        bio.setMaxLines(MAX_LINES);
        bio.setText(LoginActivity.currentUser.getBio());
        final Spinner dropdown = (Spinner) findViewById(R.id.majorEdit);
        final String[] items = new String[] {"Aerospace Engineering", "Applied Language and Cultural Studies", "Applied Mathematics", "Applied Physics", "Architecture", "Biochemistry", "Biology", "Biomedical Engineering", "Building Construction", "Business Administration", "Civil Engineering", "Chemical Engineering", "Chemistry", "Computational Media", "Computer Engineering", "Computer Science", "Discrete Mathematics", "Earth and Atmospheric Sciences", "Economics", "Economics and International Affairs", "Electrical Engineering", "History, Technology, and Society", "Industrial Design", "Industrial Engineering", "International Affairs", "International Affairs and Modern Language", "Literature, Media, and Communication", "Materials Science and Engineering", "Mechanical Engineering", "Nuclear and Radiological Engineering", "Physics", "Psychology", "Public Policy"};
        final String userMajor = LoginActivity.currentUser.getMajor();
        final int pos = Arrays.asList(items).indexOf(userMajor);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.requestFocusFromTouch();
        dropdown.setSelection(pos);
        dropdown.requestFocus();
    }

    /**
     * Sets the user's major and bio accordingly upon click of the update button
     * @param v the default param for onClick methods
     */
    public void onClickUpdate(View v) {
        final Spinner majorSpinner = (Spinner) findViewById(R.id.majorEdit);
        newMajor = majorSpinner.getSelectedItem().toString();
        newBio = (EditText) findViewById(R.id.bioEdit);
        final String username = LoginActivity.currentUser.getUsername();

        final Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        a.vibrate(VIBRATE_TIME);

        if (newBio.getText().toString().length() != 0) {
            new editProfileTask().execute(username, newMajor,
                    newBio.getText().toString().trim());
        } else {
            Toast.makeText(getApplicationContext(),
                "One or more of the fields was left empty. Profile was not updated.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Cancels the editing process and goes back to a view of the profile
     * @param v the default param for onClick methods
     */
    public void onClickCancelEditProfile(View v) {
        finish();
        final Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        a.vibrate(VIBRATE_TIME);
    }

    /**
     * Asynchronous call to EditProfileTask.
     * Takes in inputs: username, major, bio
     * Connects to remote database to retrieve user information and update CurrentUser accordingly.
     */
    private class editProfileTask extends AsyncTask<String, Void, String> {

        /**
         * This method immediately starts running when execute() is called.
         * Inputs are username and password.
         * It will retrieve data from php database and pass the return message to onPostExecute().
         * @param args username, major, bio
         * @return sql database query in JSON format
         */
        @Override
        protected String doInBackground(String... args) {
            final String username = args[0];
            final String major = args[1];
            final String bio = args[2];

            String link;
            BufferedReader bufferedReader;
            String result;
            try {
                String data = "?username=" + URLEncoder.encode(username, "UTF-8");
                data += "&Major=" + URLEncoder.encode(major, "UTF-8");
                data += "&bio=" + URLEncoder.encode(bio, "UTF-8");
                link = "http://officehours.netau.net/editprofile.php" + data;
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
                    String query_result;
                    try {
                        Log.d(ACTIVITYNAME, "Entry query_result exists");
                        query_result = jsonObj.getString("query_result");
                        if ("SUCCESS".equals(query_result)) {
                            Toast.makeText(getApplicationContext(), "Profile edited successfully!", Toast.LENGTH_SHORT).show();
                            LoginActivity.currentUser.setMajor(newMajor);
                            LoginActivity.currentUser.setBio(newBio.getText().toString());
                            startActivity(new Intent(getApplicationContext(), ViewProfileActivity.class));
                            finish();
                        } else if ("FAILURE".equals(query_result)) {
                            Toast.makeText(getApplicationContext(), "Profile failed to change.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Couldn't connect to remote database.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } catch (org.json.JSONException e ){
                        Log.d(ACTIVITYNAME, "Some major error occurred");
                    }
                } catch (JSONException e) {
                    Log.d(ACTIVITYNAME, "Some fatal error occurred");
                    Log.d(ACTIVITYNAME, "Exception: " + e.getMessage());
                    Toast.makeText(getApplicationContext(), "Error parsing JSON data.",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.d(ACTIVITYNAME, "Some major error occurred");
                Toast.makeText(getApplicationContext(), "Couldn't get any JSON data.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

}
