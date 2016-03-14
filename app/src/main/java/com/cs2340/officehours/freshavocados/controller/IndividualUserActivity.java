package com.cs2340.officehours.freshavocados.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.cs2340.officehours.freshavocados.R;
import com.cs2340.officehours.freshavocados.model.Review;

import java.util.ArrayList;
import java.util.LinkedList;

public class IndividualUserActivity extends Activity implements AdapterView.OnItemClickListener {

    private LinkedList<Review> userReviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_user);

        //any relevant setup code here

        //----------------------------
        ListView listViewUserReviews = (ListView) findViewById(R.id.listViewUserReviews);
        listViewUserReviews.setOnItemClickListener(this);
        listViewUserReviews.setAdapter(new MyAdapter(this, R.layout.review_item, userReviews));
    }

    private class MyAdapter extends ArrayAdapter<Review> {

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);

            Review rev = (Review) getItem(position);
            assert rev != null;

            TextView reviewer = (TextView) view.findViewById(R.id.reviewer);
            reviewer.setText(rev.getUsername());

            TextView major = (TextView) view.findViewById(R.id.user_major);
            major.setText(rev.getMajor());

            RatingBar rating = (RatingBar) view.findViewById(R.id.user_rating);
            rating.setRating(rev.getRating().getRating());

            TextView review_text = (TextView) view.findViewById(R.id.review_text);
            review_text.setText(rev.getReviewText());

            return view;
        }

        public MyAdapter(Context context, int resource, LinkedList<Review> objects) {
            super(context, resource, objects);
        }
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("Clicking indiv reviews", "success");
        //not sure yet what we'll do here.
    }

    public void onClickBackAdmin(View v) {
        finish();
    }

    public void onClickSubmitChanges(View v) {
        //code here to save the changed data back to the database
    }

    private class AdminTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String ... args) {
            //stuff
            return null;
        }

        protected void onPostExecute(String result) {
            //
        }
    }

}
