package com.cs2340.officehours.freshavocados.controller;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cs2340.officehours.freshavocados.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class AdminActivity extends Activity implements AdapterView.OnItemClickListener {
/**
 * AdminActivity page for admins to
 * edit user banned / locked statuses
 *
 * @author OfficeHours
 * @version 1.0
 */

    /**
     * ArrayList of admin usernames
     */
    private final ArrayList<String> adminUsernames = new ArrayList<>();
    /**
     * String for whether or not user is banned
     */
    private String isBanned = "";
    /**
     * String for whether or not user is locked
     */
    private String isLocked = "";
    /**
     * Int for vibrate time for vibrator
     */
    private final static int VIBRATE_TIME = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //code here for assigning the users list

        //--------------------------------------
        new AdminTask().execute();
        final Integer i = adminUsernames.size();
        Log.d("List size", i.toString());

//        ListView listView = (ListView) findViewById(R.id.listView);
//        listView.setOnItemClickListener(this);
//        adapter = new MyAdapter(this, R.layout.list_item_user, R.id.adminTitle, admin_usernames);
//        listView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final Intent i = new Intent(AdminActivity.this, IndividualUserActivity.class);
        final String user = adminUsernames.get(position);
        final int timeMillis = 10000;
        try {
            final AsyncTask uit = new UserInfoTask().execute(user);
            uit.get(timeMillis, TimeUnit.MILLISECONDS);
        } catch (java.lang.InterruptedException e) {
            Log.d("Uh oh", e.getMessage());
        }
        catch (java.util.concurrent.ExecutionException e) {
            Log.d("Oh no", e.getMessage());
        }
        catch (java.util.concurrent.TimeoutException e) {
            Log.d("Oops", e.getMessage());
        }

        Log.d("User", user);
        Log.d("isLocked", isLocked);
        Log.d("isBanned", isBanned);
        final boolean userLockStatus = "1".equals(isLocked);
        final boolean userBanStatus = "1".equals(isBanned);
        i.putExtra("isLocked", userLockStatus);
        i.putExtra("isBanned", userBanStatus);
        i.putExtra("user", user);


        //add extras to the intent

        //------------------------
        startActivity(i);
    }

    /**
     * Defines the logout process for an admin
     * @param v the current view
     */
    public void onClickLogoutAdmin(View v) {
        LoginActivity.currentUser = null;
        final Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        final Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        a.vibrate(VIBRATE_TIME);
    }

    /**
     * Generates the list of users onto the screen
     * @param v the current view
     */
    public void onClickGenerateUserList(View v) {
        final Integer i = adminUsernames.size();
        Log.d("List size", i.toString());
        for (String s : adminUsernames) {
            Log.d("User", s);
        }
        final ListView listView = (ListView) findViewById(R.id.list_view_admin);
        listView.setOnItemClickListener(this);
        final MyAdapter adapter = new MyAdapter(this, adminUsernames);
        listView.setAdapter(adapter);
        //adapter.notifyDataSetChanged();
    }

    /**
     * This defines the way in which the users are displayed in the this activity's list view
     */
    private class MyAdapter extends ArrayAdapter<String> {

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final View view = super.getView(position, convertView, parent);

            final String u = getItem(position);
            assert u != null;

            final TextView usernameListItem = (TextView) view.findViewById(R.id.listing_users);
            usernameListItem.setText(u);

            return view;
        }

        /**
         * The constructor for our adapter
         * @param context the source content
         * @param objects the list of objects to be displayed
         */
        public MyAdapter(Context context, ArrayList<String> objects) {
            super(context, R.layout.list_item_user, R.id.listing_users, objects);
        }

        @Override
        public boolean isEnabled(int position) {
            return true;
        }
    }

    /**
     * An AsyncTask that grabs the user information from the database
     */
    private class UserInfoTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String ... args) {
            String link;
            BufferedReader bufferedReader;
            String result;
            try {
                link = "http://officehours.netau.net/getuserstatus.php?username=";
                link = link + URLEncoder.encode(args[0], "UTF-8");
                final URL url = new URL(link);
                final HttpURLConnection con = (HttpURLConnection) url.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                result = bufferedReader.readLine();
                if (result != null) {
                    try {
                        final JSONObject jsonObject = new JSONObject(result);
                        isLocked = jsonObject.getString("isLocked");
                        isBanned = jsonObject.getString("isBanned");
                    }
                    catch (JSONException e) {
                        Log.d("uh oh", e.getMessage());
                    }
                }
                return result;
            } catch (java.io.IOException e) {
                Log.d("UserInfoTask", e.getMessage());
                return e.getMessage();
            }
        }

//        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
//            progressDialog = new ProgressDialog(AdminActivity.this);
//            progressDialog.setMessage("Loading info...");
//            progressDialog.setCancelable(false);
//            progressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Void ... values) {
            // do nothing
        }

        @Override
        protected void onPostExecute(String result) {
//            String jsonStr = result;
//            if (jsonStr != null) {
//                try {
//                    JSONObject jsonObj = new JSONObject(jsonStr);
//                    isLocked = jsonObj.getString("isLocked");
//                    isBanned = jsonObj.getString("isBanned");
//                    this.get(1000, TimeUnit.MILLISECONDS);
//                    while (true) {
//                        if (isLocked != null && isBanned != null) {
//                            progressDialog.cancel();
//                            return;
//                        }
//                    }
//                } catch (Exception e) {
//                    Log.d("Could not get status", "Uh-oh");
//                }
//            }
        }


    }

    /**
     * An AsyncTask that grabs the usernames from the database
     */
    private class AdminTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... args) {
            String link;
            BufferedReader bufferedReader;
            String result;
            try {
                link = "http://officehours.netau.net/getusernames.php";
                final URL url = new URL(link);
                final HttpURLConnection con = (HttpURLConnection) url.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                result = bufferedReader.readLine();
                result = result.substring(0, result.length() - 2);
                result = "{ Usernames: [" + result + "] }";
                Log.d("Testing JSON result", result);
                return result;
            } catch (java.io.IOException e) {
                Log.d("AdminActivity", e.getMessage());
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    final JSONObject jsnObject = new JSONObject(result);
                    final JSONArray array = jsnObject.getJSONArray("Usernames");
                    for (int i = 0; i < array.length(); i++) {
                        final JSONObject user = array.getJSONObject(i);
                        final String username = user.getString("Username");
                        adminUsernames.add(username);
                    }
                } catch (org.json.JSONException e) {
                    Log.d("woops", e.getMessage());
                }
            } else {
                Log.d("Couldn't get JSON data", "JSON string was empty");
            }
//            if (jsonStr != null) {
//                try {
//                    JSONObject jsonObj = new JSONObject(jsonStr);
//                    String query_result = "";
//                    try {
//                        Log.d("LoginActivity", "Entry query_result exists");
//                        query_result = jsonObj.getString("query_result");
//                        if (query_result.equals("DENIED")) {
//                            Toast.makeText(getApplicationContext(), "Login failed.",
//                                    Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(getApplicationContext(),
//                                    "Couldn't connect to remote database.",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    } catch (Exception e){
//                        Log.d("LoginActivity", "Entry query_result does not exist");
//                        String username = jsonObj.getString("Username");
//                        String password = jsonObj.getString("Password");
//                        String email = jsonObj.getString("Email");
//                        String major = jsonObj.getString("Major");
//                        String firstName = jsonObj.getString("FirstName");
//                        String lastName = jsonObj.getString("LastName");
//                        String bio = jsonObj.getString("Bio");
//                        Integer adminStatus = Integer.parseInt(jsonObj.getString("IsAdmin"));
//                        boolean isAdmin = (adminStatus == 1);
//                        Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_SHORT).show();
//                        currentUser = new User(firstName, lastName, username, password, email,
//                                major, bio);
//                        if (!isAdmin) {
//                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                        } else {
//                            startActivity(new Intent(getApplicationContext(), AdminActivity.class));
//                        }
//                        finish();
//                    }
//                } catch (JSONException e) {
//                    Log.d("LoginActivity", "Some fatal error occurred");
//                    Log.d("LoginActivity", "Exception: " + e.getMessage());
//                    e.printStackTrace();
//                    Toast.makeText(getApplicationContext(), "Error parsing JSON data.",
//                            Toast.LENGTH_SHORT).show();
//                }
//            } else {
//                Log.d("LoginActivity", "Some major error occurred");
//                Toast.makeText(getApplicationContext(), "Couldn't get any JSON data.",
//                        Toast.LENGTH_SHORT).show();
//            }
        }
    }
}
