package com.cs2340.officehours.freshavocados.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.cs2340.officehours.freshavocados.R;

public class EditProfileActivity extends Activity {

    Toast profileUpdatedToast;
    Toast emptyFieldsToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        EditText bio = (EditText) findViewById(R.id.bioEdit);
        bio.setHorizontallyScrolling(false);
        bio.setMaxLines(6);
        bio.setText(LoginActivity.currentUser.getBio());
        Spinner dropdown = (Spinner) findViewById(R.id.majorEdit);
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
     * Sets the user's major and bio accordingly upon click of the update button
     * @param v the default param for onClick methods
     */
    public void onClickUpdate(View v) {
        Spinner majorSpinner = (Spinner) findViewById(R.id.majorEdit);
        String newMajor = majorSpinner.getSelectedItem().toString();
        EditText newBio = (EditText) findViewById(R.id.bioEdit);

        Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        a.vibrate(50);

        if (newBio.getText().toString().length() != 0 && !newMajor.equals("Major")) {
            LoginActivity.currentUser.setMajor(newMajor);
            LoginActivity.currentUser.setBio(newBio.getText().toString());
            if (profileUpdatedToast == null) {
                profileUpdatedToast = Toast.makeText(getApplicationContext(),
                        "Profile Updated!", Toast.LENGTH_SHORT);
            }
            profileUpdatedToast.show();
            startActivity(new Intent(getApplicationContext(), ViewProfileActivity.class));
        } else {
            if (emptyFieldsToast == null) {
                emptyFieldsToast = Toast.makeText(getApplicationContext(),
                        "One or more of the fields was left empty. Profile was not updated.", Toast.LENGTH_SHORT);
            }
            emptyFieldsToast.show();
        }
    }

    /**
     * Cancels the editing process and goes back to a view of the profile
     * @param v the default param for onClick methods
     */
    public void onClickCancelEditProfile(View v) {
        startActivity(new Intent(getApplicationContext(), ViewProfileActivity.class));
        Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        a.vibrate(50);
    }
}
