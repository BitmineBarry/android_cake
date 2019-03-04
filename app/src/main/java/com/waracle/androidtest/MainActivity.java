package com.waracle.androidtest;

import android.app.Activity;
import android.app.ListFragment;
import android.net.http.HttpResponseCache;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;


public class MainActivity extends Activity {
    private static final String TAG = AsyncImageLoader.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // setup cache management for HTTP/HTTPS for this application
        try {
            File httpCacheDir = new File(getCacheDir(), "http");
            long httpCacheSize = 3 * 1024 * 1024;
            HttpResponseCache.install(httpCacheDir, httpCacheSize);
        } catch (IOException e) {
            Log.w(TAG, "HTTP response cache installation failed:" + e);
        }

        if (savedInstanceState == null) {
                    getFragmentManager().beginTransaction()
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
            // TODO: cause a flush of the HTTP cache here and start data collection again

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        // remove the cache for this application
        HttpResponseCache cache = HttpResponseCache.getInstalled();
        if (cache != null) {
            cache.flush();
        }
        super.onStop();
    }

    /**
     * Fragment is responsible for loading in some JSON and
     * then displaying a list of cakes with images.
     * Fix any crashes
     * Improve any performance issues
     * Use good coding practices to make code more secure
     */
    public static class PlaceholderFragment extends ListFragment {

//        private static final String TAG = PlaceholderFragment.class.getSimpleName();

        private ListView    mListView;
        private CakeAdapter   mAdapter;

        public PlaceholderFragment() { /**/ }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView;
            rootView = inflater.inflate(R.layout.fragment_main, container, false);
            mListView = rootView.findViewById(android.R.id.list);
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
