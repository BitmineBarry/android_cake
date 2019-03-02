package com.waracle.androidtest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/***
 * This is required for fetching the cake data without network access on the system thread.
 *
 *
 */
public final class AsyncFetchCakeData extends AsyncTask<CakeAdapter, Void, Boolean> {
    private static final String TAG = AsyncFetchCakeData.class.getSimpleName();
    private static String JSON_URL = "https://gist.githubusercontent.com/t-reed/739df99e9d96700f17604a3971e701fa/raw/1d4dd9c5a0ec758ff5ae92b7b13fe4d57d34e1dc/waracle_cake-android-client";
    private CakeAdapter mCakeAdapter = null;

    public AsyncFetchCakeData(){ }

//    private CakeAdapter mAdapter;
    private String      mUrl;

    /**
     * Returns the charset specified in the Content-Type of this header,
     * or the HTTP default (ISO-8859-1) if none can be found.
     */
    public static String parseCharset(String contentType) {
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
    protected Boolean doInBackground(CakeAdapter... adapter) {
        Log.e(TAG, "Thread ID "+ Thread.currentThread());
        mCakeAdapter = adapter[0];

            try {
                JSONArray array = loadData();
                mCakeAdapter.setItems(array);
            } catch (IOException | JSONException e) {
                Log.e(TAG, e.getMessage());

//                 TODO: here we could add a message to the fragment to display what happened
            }

            return false;
    }


    @Override
    protected void onPostExecute(Boolean status)
    {
        mCakeAdapter.notifyDataSetChanged();
        status = status;
    }
}

