package com.waracle.androidtest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CakeAdapter extends BaseAdapter {

    // Can you think of a better way to represent these items???
    private JSONArray   mItems;
    private ImageLoader mImageLoader;
    private Activity    mActivity;

    public CakeAdapter(Activity activity) {
        this(new JSONArray());
        mActivity = activity;
    }

    public CakeAdapter(JSONArray items) {
        mItems = items;
        mImageLoader = new ImageLoader();
    }

    @Override
    public int getCount() {
        return mItems.length();
    }

    @Override
    public Object getItem(int position) {
        try {
            return mItems.getJSONObject(position);
        } catch (JSONException e) {
            Log.e("", e.getMessage());
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mActivity);
        View root = inflater.inflate(R.layout.list_item_layout, parent, false);
        if (root != null) {
            TextView title = (TextView) root.findViewById(R.id.title);
            TextView desc = (TextView) root.findViewById(R.id.desc);
            ImageView image = (ImageView) root.findViewById(R.id.image);
            try {
                JSONObject object = (JSONObject) getItem(position);
                title.setText(object.getString("title"));
                desc.setText(object.getString("desc"));
                mImageLoader.load(object.getString("image"), image);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return root;
    }

    public void setItems(JSONArray items) {
        mItems = items;
    }
}
