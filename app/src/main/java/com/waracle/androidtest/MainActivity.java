package com.waracle.androidtest;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

//    private static String JSON_URL = "https://gist.githubusercontent.com/t-reed/739df99e9d96700f17604a3971e701fa/raw/1d4dd9c5a0ec758ff5ae92b7b13fe4d57d34e1dc/waracle_cake-android-client";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Fragment is responsible for loading in some JSON and
     * then displaying a list of cakes with images.
     * Fix any crashes
     * Improve any performance issues
     * Use good coding practices to make code more secure
     */
    public static class PlaceholderFragment extends ListFragment {

        private static final String TAG = PlaceholderFragment.class.getSimpleName();

        private ListView    mListView;
        private CakeAdapter   mAdapter;

        public PlaceholderFragment() { /**/ }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            mListView = (ListView) rootView.findViewById(android.R.id.list);
            return rootView;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

//            This sets the list to the static test data if needed during testing.
//            ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),
//                    R.array.list_content, android.R.layout.simple_list_item_1);
//            this.setListAdapter(adapter);

            // Create and set the list adapter.
            mAdapter = new CakeAdapter(getActivity());

            // Load data from net. We will use an AsyncTask to fetch from the network without
            // using the system thread
            AsyncFetchCakeData cakeData = new AsyncFetchCakeData();
            cakeData.execute(mAdapter);

            // set the adapter for the list
            mListView.setAdapter(mAdapter);
        }
    }
}
