package com.waracle.androidtest;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/***
 * This is required for fetching the cake data without network access on the system thread.
 *
 *
 */
public final class AsyncFetchCakeData extends AsyncTask<CakeListRecyclerViewAdapter, Void, Boolean> {
    private static final String TAG = AsyncFetchCakeData.class.getSimpleName();
    private static String JSON_URL = "https://gist.githubusercontent.com/t-reed/739df99e9d96700f17604a3971e701fa/raw/1d4dd9c5a0ec758ff5ae92b7b13fe4d57d34e1dc/waracle_cake-android-client";

    private CakeListRecyclerViewAdapter mCakeListRecyclerViewAdapter = null;
    private Boolean                     mShowNoNetToast = false;
    private Application                 mAppContext = null;

    public AsyncFetchCakeData(Application context) {
        mAppContext = context;
    }

    private String      mUrl;

    /**
     * Returns the charset specified in the Content-Type of this header,
     * or the HTTP default (ISO-8859-1) if none can be found.
     */
    private static String parseCharset(String contentType) {
        if (contentType != null) {
            String[] params = contentType.split(",");
            for (int i = 1; i < params.length; i++) {
                String[] pair = params[i].trim().split("=");
                if (pair.length == 2) {
                    if (pair[0].equals("charset")) {
                        return pair[1];
                    }
                }
            }
        }
        return "UTF-8";
    }

    private JSONArray loadData() throws IOException, JSONException {
        URL url = new URL(JSON_URL);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            // Can you think of a way to improve the performance of loading data
            // using HTTP headers???

            // Also, Do you trust any utils thrown your way????

            byte[] bytes = StreamUtils.readUnknownFully(in);

            // Read in charset of HTTP content.
            String charset = parseCharset(urlConnection.getRequestProperty("Content-Type"));

            // Convert byte array to appropriate encoded string.
            String jsonText = new String(bytes, charset);

            // Read string as JSON.
            return new JSONArray(jsonText);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally {
            urlConnection.disconnect();
        }
        return null;
    }

    @Override
    protected Boolean doInBackground(CakeListRecyclerViewAdapter... adapter) {
        ArrayList<CakeDataItem>  cakeData = new ArrayList<CakeDataItem>();

        mCakeListRecyclerViewAdapter = adapter[0];

        try {
            JSONArray array = loadData();
            if (array != null) {
                // convert the JSON into CakeDataItems so that the API for the Adapter remains abstracted.
                for (int i = 0; i < array.length(); i++) {
                    cakeData.add(new CakeDataItem(array.getJSONObject(i).getString("title"),
                            array.getJSONObject(i).getString("desc"),
                            array.getJSONObject(i).getString("image")));

                }
            }
            else
            {
                //  for now we just cause a message to display on the view.
                //  it would be nice to present a network settings option.
                mShowNoNetToast = true;
                cakeData.add(new CakeDataItem("No list data. ", "Check you network connection.", " "));
            }
            mCakeListRecyclerViewAdapter.setItems(cakeData);
        } catch (IOException | JSONException e) {
            Log.e(TAG, e.getMessage());
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean status)
    {
        showRequiredToasts();
        mCakeListRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void showRequiredToasts(){
        if (mShowNoNetToast) {
            Toast.makeText(mAppContext, "Please check your network connection.", Toast.LENGTH_LONG).show();
        }
    }
}

