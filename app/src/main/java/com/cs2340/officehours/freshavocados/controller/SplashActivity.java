package com.cs2340.officehours.freshavocados.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.cs2340.officehours.freshavocados.R;


public class SplashActivity extends Activity {
    private final static int VIBRATE_TIME = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /*
        NOTE: The following is placeholder data, which is being used for M3 and for debugging purposes.
        Subsequently, we will remove this temporary data, and replace it with a holistic registration system.
         */

        /*
        NOTE: This has since been done.
         */
//        UserManagementFacade uM = new UserManager();
//        uM.addUser("b", "b", "b", "b", "b@b.com", "CS", "ayylmao");
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
        final int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * The user can click this button to be brought to an activity where they can login to the app
     * @param v the default param for onClick methods
     */
    public void onClickLogin(View v) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        final Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        a.vibrate(VIBRATE_TIME);
    }

    /**
     * The user can click this button to be brought to an activity where they can register for the app
     * @param v the default param for onClick methods
     */
    public void onClickRegister(View v) {
        startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
        final Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        a.vibrate(VIBRATE_TIME);
    }

    private Toast toast;

    /**
     * The user can click the icon to get a fun surprise
     * @param v the default param for onClick methods
     */
    public void onClickIcon(View v) {
        if (toast == null) {
            toast = Toast.makeText(getApplicationContext(), "You found the easter egg!", Toast.LENGTH_SHORT);
        }
        toast.show();
        final Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        a.vibrate(VIBRATE_TIME);
    }

}
