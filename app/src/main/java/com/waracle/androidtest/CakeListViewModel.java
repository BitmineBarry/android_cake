package com.waracle.androidtest;


import android.app.Application;
import android.util.Log;

import java.util.ArrayList;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CakeListViewModel extends ViewModel {
    static final String TAG = CakeListViewModel.class.getSimpleName();
    private CakeRepository  mCakeRepository = new CakeRepository();

    public enum cakeModelStatus {
            CMS_LOADING,        // currently loading data from the remote data source
            CMS_ERROR,          // Loading of data from the remote source failed
            CMS_READY,          // Data loaded and ready to use.
    };

    // add the array of CakeDataItems here
    private MutableLiveData<Integer>    mVMCakeListUpdated = new MutableLiveData<>();

//    private MutableLiveData<ArrayList<CakeDataItem> > mCakeItems = new MutableLiveData<>();

//    private MutableLiveData<String>     mUrl;
    private cakeModelStatus             mStatus;

    public CakeListViewModel(){
    }

    public LiveData<Integer>   getCakeDataItemsUpdated() {
        return mVMCakeListUpdated;
    }

    public ArrayList<CakeDataItem>   getCakeDataItems() {
        return mCakeRepository.getCakeDataItems();
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

    public void refreshCakeData() {
        mCakeRepository.scheduleRefresh();
    }
}

