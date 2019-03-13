package com.waracle.androidtest;

import android.util.Log;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/***
 *
 *  The repository is intended to abstract the data for the rest of the application. This orchestrates
 *  fetching data in whatever means necessary but provides a single consistent API for the application
 *  to access the data. The task of fetching data may well be offset to other objects.
 *
 */

public class CakeRepository {
    private final String TAG = CakeRepository.class.getSimpleName();

    private ArrayList<CakeDataItem>  mCakeListItems;
    private MutableLiveData<Integer> mCakeListUpdate;
    private String  mUrl;


    public CakeRepository(){
        if (mCakeListItems == null) {
            mCakeListItems = new ArrayList<>();
        }

        if (mCakeListUpdate == null) {
            mCakeListUpdate = new MutableLiveData<Integer>();
//            mCakeListUpdate.postValue(0);
        }

        // trigger the load of the list data
        // fetch from repository which in turn will fetch using an async provider of some sort
        ArrayList<CakeDataItem> cakeData = new ArrayList<CakeDataItem>();
        cakeData.add(new CakeDataItem("Test title 1", "Desc 1", "Url1"));
        cakeData.add(new CakeDataItem("Test title 2", "Desc 2", "Url2"));
        cakeData.add(new CakeDataItem("Test title 3", "Desc 3", "Url3"));
        mCakeListItems = cakeData;

        scheduleRefresh();
    }
    /**

     *
     * @return ArrayList of CakeDataItem objects that represent the cake list contents
     */

    public ArrayList<CakeDataItem> getCakeDataItems() {
//        if (mCakeItems == null)
//        {
//            Log.i(TAG, "Creating mCakeItems as it was null");
//            mCakeItems = new MutableLiveData<ArrayList<CakeDataItem> >();
//        }
        return mCakeListItems;
    }

    /**
     * setter for the cake list URL just in case we want to add flexability
     *
     * @param   url - this is a String representing the URL to be used to fetch the cake information.
     *
     * @return void
     */

    public void setUrlString(String url) {
        mUrl = url;
    }

    /**
     * getter for the cake list URL
     *
     * @return String representing the currently set URL for fetching the cake list data
     */

    public String getUrlString() {
        return mUrl;
    }

    /**
     * Schedule an asynchronous refresh of the data for the list.
     * This initiates the refresh process. When updated the list information will trigger a refresh
     * of the displayed data.
     *
     */
    public void scheduleRefresh() {
        // TODO : start a refresh
        Log.e(TAG, "scheduleRefresh...");

        mCakeListUpdate.postValue(0);
    }
}