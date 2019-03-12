package com.waracle.androidtest;


import android.app.Application;
import android.util.Log;

import java.util.ArrayList;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CakeListViewModel extends ViewModel {
    public enum cakeModelStatus {
            CMS_LOADING,        // currently loading data from the remote data source
            CMS_ERROR,          // Loading of data from the remote source failed
            CMS_READY,          // Data loaded and ready to use.
    };

    static final String TAG = CakeListViewModel.class.getSimpleName();

    // add the array of CakeDataItems here
    private MutableLiveData<ArrayList<CakeDataItem> > mCakeItems = new MutableLiveData<>();
    private MutableLiveData<String>     mUrl;
    private cakeModelStatus             mStatus;

    public LiveData<ArrayList<CakeDataItem> >  getCakeDataItems() {
        if (mCakeItems == null)
        {
            Log.i(TAG, "Creating mCakeItems as it was null");
            mCakeItems = new MutableLiveData<ArrayList<CakeDataItem> >();
        }
        return mCakeItems;
    }

    public CakeListViewModel(){
        // trigger the load of the list data
        // fetch from repository which in turn will fetch using an async provider of some sort
        if (mCakeItems != null)
        {
            ArrayList<CakeDataItem> cakeData = new ArrayList<CakeDataItem>();
            cakeData.add(new CakeDataItem("Test title 1", "Desc 1", "Url1"));
            cakeData.add(new CakeDataItem("Test title 2", "Desc 2", "Url2"));
            cakeData.add(new CakeDataItem("Test title 3", "Desc 3", "Url3"));

            mCakeItems.postValue(cakeData);
        }
    }
    /**
     *  Initialise this model based on the URL  string provided
     *
     */
//    public void initViewModel(String url) {
//        mUrl.setValue(url);
//    }

    /**
     * Get the ViewModel status.
     */
    public cakeModelStatus getModelStatus() {
        return mStatus;
    }
}

