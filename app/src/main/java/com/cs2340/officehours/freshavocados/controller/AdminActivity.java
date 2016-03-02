package com.cs2340.officehours.freshavocados.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cs2340.officehours.freshavocados.R;
import com.cs2340.officehours.freshavocados.model.User;

import java.util.ArrayList;

public class AdminActivity extends Activity implements AdapterView.OnItemClickListener {

    private ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //code here for assigning the users list

        //--------------------------------------
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        listView.setAdapter(new MyAdapter(this, R.layout.list_item_user, users));
    }

    private class MyAdapter extends ArrayAdapter<User> {

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);

            User u = (User) getItem(position);
            assert u != null;

            TextView username_list_item = (TextView) view.findViewById(R.id.username_list_item);
            username_list_item.setText(u.getUsername());

            return view;
        }

        public MyAdapter(Context context, int resource, ArrayList<User> objects) {
            super(context, resource, objects);
        }

        @Override
        public boolean isEnabled(int position) { return true; }
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(AdminActivity.this, IndividualUserActivity.class);

        //add extras to the intent

        //------------------------
        startActivity(i);
    }
}
