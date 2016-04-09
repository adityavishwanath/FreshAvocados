package com.cs2340.officehours.freshavocados.controller;

import android.app.ProgressDialog;
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
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AdminActivity extends Activity implements AdapterView.OnItemClickListener {

    private ArrayList<String> admin_usernames = new ArrayList<>();
    private String isBanned = "";
    private String isLocked = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //code here for assigning the users list

        //--------------------------------------
        new AdminTask().execute();
        Integer i = admin_usernames.size();
        Log.d("List size", i.toString());

//        ListView listView = (ListView) findViewById(R.id.listView);
//        listView.setOnItemClickListener(this);
//        adapter = new MyAdapter(this, R.layout.list_item_user, R.id.adminTitle, admin_usernames);
//        listView.setAdapter(adapter);
    }

    /**
     * This defines the way in which the users are displayed in the this activity's list view
     */
    private class MyAdapter extends ArrayAdapter<String> {

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);

            String u = getItem(position);
            assert u != null;

            TextView username_list_item = (TextView) view.findViewById(R.id.listing_users);
            username_list_item.setText(u);

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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(AdminActivity.this, IndividualUserActivity.class);
        String user = admin_usernames.get(position);
        try {
            AsyncTask uit = new UserInfoTask().execute(user);
            uit.get(10000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            Log.d("Uh oh", e.getMessage());
        }
        Log.d("User", user);
        Log.d("isLocked", isLocked);
        Log.d("isBanned", isBanned);
        boolean userLockStatus = isLocked.equals("1");
        boolean userBanStatus = isBanned.equals("1");
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
        Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        a.vibrate(50);
    }

    /**
     * Generates the list of users onto the screen
     * @param v the current view
     */
    public void onClickGenerateUserList(View v) {
        Integer i = admin_usernames.size();
        Log.d("List size", i.toString());
        for (String s : admin_usernames) {
            Log.d("User", s);
        }
        ListView listView = (ListView) findViewById(R.id.list_view_admin);
        listView.setOnItemClickListener(this);
        MyAdapter adapter = new MyAdapter(this, admin_usernames);
        listView.setAdapter(adapter);
        //adapter.notifyDataSetChanged();
    }

    /**
     * An AsyncTask that grabs the user information from the database
     */
    private class UserInfoTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String ... args) {
            String link;
            BufferedReader bufferedReader;
            String result = "";
            try {
                link = "http://officehours.netau.net/getuserstatus.php?username=";
                link = link + URLEncoder.encode(args[0], "UTF-8");
                URL url = new URL(link);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                result = bufferedReader.readLine();
                if (result != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        isLocked = jsonObject.getString("isLocked");
                        isBanned = jsonObject.getString("isBanned");
                    } catch (Exception e) {
                        Log.d("uh oh", e.getMessage());
                    }
                }
                return result;
            } catch (Exception e) {
                Log.d("UserInfoTask", e.getMessage());
                return e.getMessage();
            }
        }

        ProgressDialog progressDialog;

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
     * An AsyncTask that grabes the usernames from the database
     */
    private class AdminTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... args) {
            String link;
            BufferedReader bufferedReader;
            String result = "";
            try {
                link = "http://officehours.netau.net/getusernames.php";
                URL url = new URL(link);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                result = bufferedReader.readLine();
                result = result.substring(0, result.length() - 2);
                result = "{ Usernames: [" + result + "] }";
                Log.d("Testing JSON result", result);
                return result;
            } catch (Exception e) {
                Log.d("AdminActivity", e.getMessage());
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            String jsonStr = result;
            if (jsonStr != null) {
                try {
                    JSONObject jsnObject = new JSONObject(jsonStr);
                    JSONArray array = jsnObject.getJSONArray("Usernames");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject user = array.getJSONObject(i);
                        String username = user.getString("Username");
                        admin_usernames.add(username);
                    }
                } catch (Exception e) {
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
