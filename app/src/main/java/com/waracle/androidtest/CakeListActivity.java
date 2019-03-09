package com.waracle.androidtest;

import android.app.Activity;
import android.net.http.HttpResponseCache;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.io.IOException;


public class MainActivity extends Activity implements CakeListFragment.OnListFragmentInteractionListener {
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
                    .add(R.id.container, new CakeListFragment())
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
            HttpResponseCache cache = HttpResponseCache.getInstalled();
            cache.flush();
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

    /*** implementation of interface OnListFragmentInteractionListener
     *
     */
    public void onListFragmentInteraction(CakeDataItem item)
    {
        Log.i(TAG, "onListFragmentInteraction called for item " + item);
    }

}