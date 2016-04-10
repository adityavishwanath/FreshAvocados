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

public class RegistrationActivity extends Activity {

    private Toast emptyField;
    private Toast wrongPass;
    private Toast invalidEmail;
    private String data;
    private final int vibrateTime = 50;
    private final int maxLines = 6;
//    private UserManager uM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
//        uM = new UserManager();
        EditText bio = (EditText) findViewById(R.id.bio);
        bio.setHorizontallyScrolling(false);
        bio.setMaxLines(maxLines);

        Spinner dropdown = (Spinner) findViewById(R.id.major);
        String[] items = new String[]{"Major", "Aerospace Engineering", "Applied Language and Cultural Studies",
                "Applied Mathematics", "Applied Physics",
                "Architecture", "Biochemistry", "Biology", "Biomedical Engineering", "Building Construction",
                "Business Administration", "Civil Engineering", "Chemical Engineering",
                "Chemistry", "Computational Media", "Computer Engineering", "Computer Science",
                "Discrete Mathematics", "Earth and Atmospheric Sciences", "Economics",
                "Economics and International Affairs", "Electrical Engineering", "History, Technology, and Society",
                "Industrial Design", "Industrial Engineering", "International Affairs",
                "International Affairs and Modern Language", "Literature, Media, and Communication",
                "Materials Science and Engineering", "Mechanical Engineering", "Nuclear and Radiological Engineering",
                "Physics", "Psychology", "Public Policy"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
    }


    /**
     * Looks at all of the registration fields and attempts to create a valid User that
     * can login to the app
     * @param v the default param for the current activity
     */
    public void createUser(View v) {


        EditText fname = (EditText) findViewById(R.id.f_name);
        EditText lname = (EditText) findViewById(R.id.l_name);
        EditText uname = (EditText) findViewById(R.id.u_name);
        EditText pass = (EditText) findViewById(R.id.pass);
        EditText email = (EditText) findViewById(R.id.email);
        EditText confirm_password = (EditText) findViewById(R.id.confirm_password);
        //EditText major = (EditText) findViewById(R.id.major);
        Spinner majorSpinner = (Spinner) findViewById(R.id.major);
        String major = majorSpinner.getSelectedItem().toString();
        EditText bio = (EditText) findViewById(R.id.bio);


        boolean passWasWrong = false;
        boolean fieldIsEmpty = false;
        boolean badEmail = false;
        //Checks for no empty fields
        if (pass.getText().toString().length() == 0 || fname.getText().toString().length() == 0
                || lname.getText().toString().length() == 0 || uname.getText().toString().length() == 0
                || email.getText().toString().length() == 0 || major.equals("Major")
                /*
                major.getText().toString().length() == 0
                */
                || bio.getText().toString().length() == 0) {
            if (emptyField == null) {
                emptyField = Toast.makeText(getApplicationContext(), "All fields must be filled in", Toast.LENGTH_SHORT);
            }
            fieldIsEmpty = true;
            emptyField.show();
            Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            a.vibrate(vibrateTime);
        }
        //Checks for valid email address
        if (!email.getText().toString().contains("@") || !(email.getText().toString().contains(".com")
                || email.getText().toString().contains(".gov") || email.getText().toString().contains(".net")
                || email.getText().toString().contains(".org") || email.getText().toString().contains(".info")
                || email.getText().toString().contains(".de") || email.getText().toString().contains(".edu"))) {
            if (invalidEmail == null) {
                invalidEmail = Toast.makeText(getApplicationContext(), "Please enter in a valid email address", Toast.LENGTH_SHORT);
            }
            badEmail = true;
            if (!fieldIsEmpty) {
                invalidEmail.show();
            }
            Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            a.vibrate(vibrateTime);
        }
        //Checks for matched passwords
        if (!fieldIsEmpty && !badEmail && !(pass.getText().toString().equals(confirm_password.getText().toString()))) {
            if (wrongPass == null) {
                wrongPass = Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT);
            }
            wrongPass.show();
            passWasWrong = true;
            Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            a.vibrate(vibrateTime);
        }
        //Create new user
        if (!fieldIsEmpty && !badEmail && !passWasWrong) {
            try {
                data = "no data inputted yet";
                Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                a.vibrate(vibrateTime);
                new RegisterTask().execute(uname.getText().toString().trim(), pass.getText().toString(),
                        email.getText().toString().trim(), major.trim(), fname.getText().toString().trim(),
                        lname.getText().toString().trim(), bio.getText().toString().trim()); //register task is private inner ASyncTask class
                System.out.println("Result is: " + data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        // On selecting a spinner item
//        String major = parent.getItemAtPosition(position).toString();
//
//    }
    /**
     * Cancels the registration process and returns to the splash screen
     * @param v the default param for onClick methods
     */
    public void onClickCancelRegistration(View v) {
//        startActivity(new Intent(getApplicationContext(), SplashActivity.class));
        finish();
        Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        a.vibrate(vibrateTime);
    }

    private Toast easterEgg;

    /**
     * The user can touch the icon and a little vibration occurs along with a small message
     * @param v the default param for onClick methods
     */
    public void onClickIcon(View v) {
        if (easterEgg == null) {
            easterEgg = Toast.makeText(getApplicationContext(), "You found the easter egg!", Toast.LENGTH_SHORT);
        }
        easterEgg.show();
        Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        a.vibrate(vibrateTime);
    }
    /**
     * Asynchronous call to RegisterTask.
     * Takes in inputs: username, password, email, major, first name, last name, bio
     * Connects to remote database to retrieve user information and update CurrentUser accordingly.
     */
    private class RegisterTask extends AsyncTask<String, Void, String> {

        /**
         * This process immediately starts running when execute() is called.
         * It will fetch from php database to see if data exists. It will pass return message
         * to onPostExecute() method.
         * @param args username, password, email, major, first name, last name, bio
         * @return sql database query in json format
         */
        @Override
        protected String doInBackground(String... args) {
            final int NAME_INDEX = 0;
            final int PASS_INDEX = 1;
            final int EMAIL_INDEX = 2;
            final int MAJOR_INDEX = 3;
            final int FNAME_INDEX = 4;
            final int LNAME_INDEX = 5;
            final int BIO_INDEX = 6;
            String name = args[NAME_INDEX];
            String pass = args[PASS_INDEX];
            String email = args[EMAIL_INDEX];
            String major = args[MAJOR_INDEX];
            String fname = args[FNAME_INDEX];
            String lname = args[LNAME_INDEX];
            String bio = args[BIO_INDEX];

            String link;
            BufferedReader bufferedReader;
            String result;
            try {
                data = "?username=" + URLEncoder.encode(name, "UTF-8");
                data += "&password=" + URLEncoder.encode(pass, "UTF-8");
                data += "&Email=" + URLEncoder.encode(email, "UTF-8");
                data += "&Major=" + URLEncoder.encode(major, "UTF-8");
                data += "&FirstName=" + URLEncoder.encode(fname, "UTF-8");
                data += "&LastName=" + URLEncoder.encode(lname, "UTF-8");
                data += "&bio=" + URLEncoder.encode(bio, "UTF-8");
                link = "http://officehours.netau.net/signup.php" + data;
                URL url = new URL(link);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                result = bufferedReader.readLine();
                return result;
            } catch (Exception e) {
                Log.d("RegistrationActivity", e.getMessage());
                return e.getMessage();
            }
        }

        /**
         *This method runs after doInBackground.
         * It will process the JSON result to determine any errors or if it was successful.
         * @param result JSON object retrieved from php response
         */
        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    JSONObject jsonObj = new JSONObject(result);
                    String query_result;
                    try {
                        Log.d("RegistrationActivity", "Entry query_result exists");
                        query_result = jsonObj.getString("query_result");
                        if (query_result.equals("SUCCESS")) {
                            Toast.makeText(getApplicationContext(), "Registered successfully!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            finish();
                        } else if (query_result.equals("EXISTING")) {
                            Toast.makeText(getApplicationContext(), "Username already exists!", Toast.LENGTH_SHORT).show();
                        } else if (query_result.equals("EMAIL")) {
                            Toast.makeText(getApplicationContext(), "Email is already used!", Toast.LENGTH_SHORT).show();
                        } else if (query_result.equals("FAILURE")) {
                            Toast.makeText(getApplicationContext(), "Registration failed.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Couldn't connect to remote database.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Log.d("RegistrationActivity", "Some major error occurred");
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

