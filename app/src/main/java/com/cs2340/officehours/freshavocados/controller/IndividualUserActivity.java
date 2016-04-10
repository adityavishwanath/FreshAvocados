package com.cs2340.officehours.freshavocados.controller;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.cs2340.officehours.freshavocados.R;
import com.cs2340.officehours.freshavocados.model.Review;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedList;

public class IndividualUserActivity extends Activity implements AdapterView.OnItemClickListener {

    private final LinkedList<Review> userReviews = new LinkedList<>();
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_user);

        //any relevant setup code here
        final boolean userLockedStatus = getIntent().getBooleanExtra("isLocked", true);
        final boolean userBanStatus = getIntent().getBooleanExtra("isBanned", true);
        username = getIntent().getStringExtra("user");

        final CheckBox isLocked = (CheckBox) findViewById(R.id.isLockedCheck);
        final CheckBox isBanned = (CheckBox) findViewById(R.id.isBannedCheck);
        final TextView username_display = (TextView) findViewById(R.id.username_display);

        isLocked.setChecked(userLockedStatus);
        isBanned.setChecked(userBanStatus);
        username_display.setText(username);

        //----------------------------
        final ListView listViewUserReviews = (ListView) findViewById(R.id.listViewUserReviews);
        listViewUserReviews.setOnItemClickListener(this);
        listViewUserReviews.setAdapter(new MyAdapter(this, userReviews));
    }

    /**
     * Represents clicking an individual review. Currently does nothing.
     * @param parent the adapterview
     * @param view the standard view
     * @param position where to index into the list
     * @param id the id of the given item
     */
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("Clicking indiv reviews", "success");
        //not sure yet what we'll do here.
    }

    public void onClickBackAdmin(View v) {
        finish();
    }

    public void onClickSubmitChanges(View v) {
        final CheckBox newLockedStatus = (CheckBox) findViewById(R.id.isLockedCheck);
        final CheckBox newBanStatus = (CheckBox) findViewById(R.id.isBannedCheck);

        final boolean updatedLockedStatus = newLockedStatus.isChecked();
        final boolean updatedBanStatus = newBanStatus.isChecked();

        String lock_status;
        String ban_status;
        if (updatedLockedStatus) {
            lock_status = "1";
        } else {
            lock_status = "0";
        }
        if (updatedBanStatus) {
            ban_status = "1";
        } else {
            ban_status = "0";
        }

        new UpdateTask().execute(username, lock_status, ban_status);
        finish();

    }

    private class MyAdapter extends ArrayAdapter<Review> {

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final View view = super.getView(position, convertView, parent);

            final Review rev = getItem(position);
            assert rev != null;

            final TextView reviewer = (TextView) view.findViewById(R.id.reviewer);
            reviewer.setText(rev.getUsername());

            final TextView major = (TextView) view.findViewById(R.id.user_major);
            major.setText(rev.getMajor());

            final RatingBar rating = (RatingBar) view.findViewById(R.id.user_rating);
            rating.setRating(rev.getRating());

            final TextView review_text = (TextView) view.findViewById(R.id.review_text);
            review_text.setText(rev.getReviewText());

            return view;
        }

        /**
         * Constructor for the custom adapter
         * @param context the context in which the adapter is being used
         * @param objects the list of things being displayed
         */
        public MyAdapter(Context context, LinkedList<Review> objects) {
            super(context, R.layout.review_item, objects);
        }
    }

    private class UpdateTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String ... args) {
            String link;
            BufferedReader bufferedReader;
            String result;
            try {
                String data = "username=" + URLEncoder.encode(args[0], "UTF-8");
                data = data + "&isLocked=" + URLEncoder.encode(args[1], "UTF-8");
                data = data + "&isBanned=" + URLEncoder.encode(args[2], "UTF-8");
                link = "http://officehours.netau.net/updateuser.php?" + data;
                final URL url = new URL(link);
                final HttpURLConnection con = (HttpURLConnection) url.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                result = bufferedReader.readLine();
                return result;
            } catch (Exception e) {
                Log.d("UpdateTask", e.getMessage());
                return e.getMessage();
            }
        }

        protected void onPostExecute(String result) {
            //does not need to do anything
        }
    }

}
