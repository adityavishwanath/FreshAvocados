package com.cs2340.officehours.freshavocados.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cs2340.officehours.freshavocados.R;
import com.cs2340.officehours.freshavocados.model.UserManagementFacade;
import com.cs2340.officehours.freshavocados.model.UserManager;


public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /*
        NOTE: The following is placeholder data, which is being used for M3 and for debugging purposes.
        Subsequently, we will remove this temporary data, and replace it with a holistic registration system.
         */

        UserManagementFacade uM = new UserManager();
        //The five of us on the team, in alphabetical order
        uM.addUser("Aditya", "Vishwanath", "aditya", "password");
        uM.addUser("Brody", "Johnstone", "brody", "password");
        uM.addUser("Brandon", "Manuel", "brandon", "password");
        uM.addUser("Pranathi", "Tupakula", "pranathi", "password");
        uM.addUser("Vagdevi", "Kondeti", "vagdevi", "password");
        //just a dummy user/pass as per the requirement of M3
        uM.addUser("Temporary", "M3Account", "user", "pass");

        Log.d("SplashActivity", "CREATED THE TEMPORARY USERS!");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickLogin(View v) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        a.vibrate(50);
    }

    public void onClickRegister(View v) {
        startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
        Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        a.vibrate(50);
    }

    Toast toast;
    public void onClickIcon(View v) {
        if (toast == null) {
            toast = Toast.makeText(getApplicationContext(), "You found the easter egg!", Toast.LENGTH_SHORT);
        }
        toast.show();
        Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        a.vibrate(50);
    }

}
