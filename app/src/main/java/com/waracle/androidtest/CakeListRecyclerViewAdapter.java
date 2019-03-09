package com.waracle.androidtest;

import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.waracle.androidtest.CakeListFragment.OnListFragmentInteractionListener;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link CakeDataItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class CakeListRecyclerViewAdapter extends RecyclerView.Adapter<CakeListRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = CakeListRecyclerViewAdapter.class.getSimpleName();

    private final String                            mUrl;
    private final OnListFragmentInteractionListener mListener;
    private List<CakeDataItem>                      mCakeData = new ArrayList<CakeDataItem>();
    private Application                             mAppContext = null;

    public CakeListRecyclerViewAdapter(Application context, String url, OnListFragmentInteractionListener listener) {
        CakeDataItem thisCake;
        mUrl = url;
        mListener = listener;
        mAppContext = context;
        AsyncFetchCakeData cakeData = new AsyncFetchCakeData(mAppContext);
        cakeData.execute(this);
    }

    // receive the list of info from the AsyncFetchCakeData
    public void setItems(ArrayList<CakeDataItem> items) {
        mCakeData = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_cakelist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mCakeData.get(position);
        holder.mTitleView.setText(mCakeData.get(position).getTitle());
        holder.mDescView.setText(mCakeData.get(position).getDescription());

        // set the image here from the URL
        AsyncImageLoader asyncImage = new AsyncImageLoader(holder.mImageView);
        if (TextUtils.isEmpty(holder.mItem.getUrl())) {
            throw new InvalidParameterException("URL is empty!");
        }
        asyncImage.execute(holder.mItem.getUrl());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public  int getItemCount() {
        return (mCakeData == null) ? (0) : (mCakeData.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mImageView;
        public final TextView mTitleView;
        public final TextView mDescView;
        public CakeDataItem mItem;

        public ViewHolder(View view) {
            super(view);

            mView = view;
            mImageView = view.findViewById(R.id.image);
            mTitleView = view.findViewById(R.id.title);
            mDescView =  view.findViewById(R.id.desc);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitleView.getText() + "'";
        }
    }
}
