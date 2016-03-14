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
import android.widget.Toast;

import com.cs2340.officehours.freshavocados.R;
import com.cs2340.officehours.freshavocados.model.User;

import org.apache.http.protocol.HTTP;
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

    private ArrayList<String> admin_usernames = new ArrayList<>();
    private MyAdapter adapter;
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

    private class MyAdapter extends ArrayAdapter<String> {

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);

            String u = (String) getItem(position);
            assert u != null;

            TextView username_list_item = (TextView) view.findViewById(R.id.listing_users);
            username_list_item.setText(u.toString());

            return view;
        }

        public MyAdapter(Context context, int resource, int textView, ArrayList<String> objects) {
            super(context, resource, textView, objects);
        }

        @Override
        public boolean isEnabled(int position) {
            return true;
        }
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(AdminActivity.this, IndividualUserActivity.class);
        String user = admin_usernames.get(position);
        try {
            AsyncTask uit = new UserInfoTask().execute(user);
            uit.get(1000, TimeUnit.MILLISECONDS);
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

    public void onClickLogoutAdmin(View v) {
        LoginActivity.currentUser = null;
        Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        Vibrator a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        a.vibrate(50);
    }

    public void onClickGenerateUserList(View v) {
        Integer i = admin_usernames.size();
        Log.d("List size", i.toString());
        for (String s : admin_usernames) {
            Log.d("User", s.toString());
        }
        ListView listView = (ListView) findViewById(R.id.list_view_admin);
        listView.setOnItemClickListener(this);
        adapter = new MyAdapter(this, R.layout.list_item_user, R.id.listing_users, admin_usernames);
        listView.setAdapter(adapter);
        //adapter.notifyDataSetChanged();
    }

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
                return result;
            } catch (Exception e) {
                Log.d("UserInfoTask", e.getMessage());
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            String jsonStr = result;
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    isLocked = jsonObj.getString("isLocked");
                    isBanned = jsonObj.getString("isBanned");
                } catch (Exception e) {
                    Log.d("Could not get status from user", "Uh-oh");
                }
            }
        }


    }

    private class AdminTask extends AsyncTask<String, Void, String> {

        /**
         * This method immediately starts running when execute() is called.
         * Inputs are username and password.
         * It will retrieve data from php database and pass the return message to onPostExecute().
         *
         * @param args username and password
         * @return sql database query in JSON format
         */
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

        /**
         * This method runs after doInBackground.
         * It will process the JSON result to determine any errors or if it was successful.
         * This uses a bit of a hack to process the result. When we query the database there are two
         * possible results:
         * 1) the actual object with user details (name, major, bio, etc)
         * 2) An error message starting with "query_result".
         * If there are no error messages, then we use the catch exception to retrieve the JSON object
         * containing the user information and update CurrentUser accordingly.
         *
         * @param result JSON object retrieved from php response
         */
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
