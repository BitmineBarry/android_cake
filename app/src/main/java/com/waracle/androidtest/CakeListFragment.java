package com.waracle.androidtest;

import androidx.lifecycle.ViewModelProviders;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
//import android.app.Fragment;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */


public class CakeListFragment extends Fragment {
    private static final String TAG = CakeListFragment.class.getSimpleName();
    private static String JSON_URL = "https://gist.githubusercontent.com/t-reed/739df99e9d96700f17604a3971e701fa/raw/1d4dd9c5a0ec758ff5ae92b7b13fe4d57d34e1dc/waracle_cake-android-client";
    private String  jsonURL = JSON_URL;

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    private CakeListViewModel   mCakeListViewModel;

    private RecyclerView                recyclerView;
    private RecyclerView.Adapter        mAdapter;
    private RecyclerView.LayoutManager  layoutManager;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CakeListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static CakeListFragment newInstance(int columnCount) {
        CakeListFragment fragment = new CakeListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Activity    thisActivity = getActivity();

        // in the future allow the option of being passed a URL here by reading it from the savedInstanceState
        super.onCreate(savedInstanceState);



        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        //
        mCakeListViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(CakeListViewModel.class);

        // Java 7 No Lambda available
        final Observer<Integer> cakeListObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable final Integer updated) {
                Log.i(TAG, "Updated cakeList.... " + updated);
                // Here we tell the adapter to update the cake list

//                TextView view = (TextView)getActivity().findViewById(R.id.display_text);
//                view.setText(s);
            }
        };
        mCakeListViewModel.getCakeDataItemsUpdated().observe(this, cakeListObserver);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cakelist_list, container, false);
        CakeListRecyclerViewAdapter thisAdapter;

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            thisAdapter = new CakeListRecyclerViewAdapter(mCakeListViewModel, jsonURL, mListener);
            recyclerView.setAdapter(thisAdapter);
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        CakeListViewModel viewModel = ViewModelProviders.of(this).get(CakeListViewModel.class);
//        viewModel.init(LiveData<new String(JSON_URL)>);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(CakeDataItem item);
    }
}
