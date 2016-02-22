package com.cs2340.officehours.freshavocados.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Vibrator;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView;


import com.cs2340.officehours.freshavocados.R;
import com.cs2340.officehours.freshavocados.model.UserManager;

public class RegistrationActivity extends Activity {

    Toast emptyField;
    Toast wrongPass;
    Toast invalidEmail;

    UserManager uM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        uM = new UserManager();
        EditText bio = (EditText) findViewById(R.id.bio);
        bio.setHorizontallyScrolling(false);
        bio.setMaxLines(6);

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
            a.vibrate(50);
        }

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
            a.vibrate(50);
        }

        if (!fieldIsEmpty && !badEmail && !(pass.getText().toString().equals(confirm_password.getText().toString()))) {
            if (wrongPass == null) {
                wrongPass = Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT);
            }
            wrongPass.show();
            passWasWrong = true;
            Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            a.vibrate(50);
        }
        if (!fieldIsEmpty && !badEmail && !passWasWrong) {
            boolean isTrue = uM.addUser(fname.getText().toString(), lname.getText().toString(),
                    uname.getText().toString(), pass.getText().toString(),
                    email.getText().toString(), major, bio.getText().toString());
            if (isTrue) {
                Toast toast = Toast.makeText(getApplicationContext(), "Profile created successfully", Toast.LENGTH_SHORT);
                toast.show();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                a.vibrate(50);
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "Username already exists", Toast.LENGTH_SHORT);
                toast.show();
                Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                a.vibrate(50);
            }
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String major = parent.getItemAtPosition(position).toString();

    }
    /**
     * Cancels the registration process and returns to the splash screen
     * @param v the default param for onClick methods
     */
    public void onClickCancelRegistration(View v) {
        startActivity(new Intent(getApplicationContext(), SplashActivity.class));
        Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        a.vibrate(50);
    }

    Toast easterEgg;

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
        a.vibrate(50);
    }

}
