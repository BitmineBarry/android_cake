package com.waracle.androidtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.app.Activity;
import android.net.http.HttpResponseCache;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.io.IOException;

import androidx.lifecycle.ViewModelProviders;


public class CakeListActivity extends AppCompatActivity implements CakeListFragment.OnListFragmentInteractionListener {
    private static final String TAG = CakeListActivity.class.getSimpleName();

    private CakeListViewModel   mCakeListViewModel = new CakeListViewModel();
    private CakeListFragment    mCakeFragment = new CakeListFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.cake_list_activity);

        // setup cache management for HTTP/HTTPS for this application
        try {
            File httpCacheDir = new File(getCacheDir(), "http");
            long httpCacheSize = 3 * 1024 * 1024;
            HttpResponseCache.install(httpCacheDir, httpCacheSize);
        } catch (IOException e) {
            Log.w(TAG, "HTTP response cache installation failed:" + e);
        }


        if (savedInstanceState == null) {
                    getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, mCakeFragment).commit();
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
            mCakeListViewModel.refreshCakeData();
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

    // this may not be the best way....
    public CakeListViewModel getCakeListViewModel() {
        return mCakeListViewModel;
    }


}
