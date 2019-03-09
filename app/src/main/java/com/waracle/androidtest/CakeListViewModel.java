package com.waracle.androidtest;


import android.app.Application;

import java.util.ArrayList;
import androidx.lifecycle.ViewModel;

public class CakeListViewModel extends ViewModel {
    // add the array of CakeDataItems here
    private ArrayList<CakeDataItem> mCakeItems;
    private String          mUrl;

    /**
     *  Initialise this model based on the URL  string provided
     *
     */
    public void init(String url) {
        mUrl = url;
    }

    /**
     * getter for the cake list data
     *
     * @return  array of CakeDataItem objects representing the list information
     */
    public ArrayList<CakeDataItem>  getCakeDataItems() {
        return mCakeItems;
    }
}

