package com.waracle.androidtest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/***
 * This is required for fetching the image without network access on the system thread.
 *
 * Here we use an asynchronous thread to fetch the image from the given URL.
 * The class requires a valid ImageView reference. This is used to render the image once loaded.
 * .doInBackground takes the URL of the image as an input in the form of a String.
 *
 */
public class AsyncImageLoader extends AsyncTask<String, Void, byte[]> {
    private static final String TAG = AsyncImageLoader.class.getSimpleName();

    public AsyncImageLoader(ImageView view){ mImageView  = view; }

    private ImageView   mImageView;
    private String      mUrl;

    @Override
    protected byte[] doInBackground(String... urls) {
        // fetch the data from the remote URL
        HttpURLConnection connection = null;
        byte[] dataBytes;

        mUrl = urls[0];
        try {
            connection = (HttpURLConnection) new URL(mUrl).openConnection();
        }
        catch(Exception e)
        {
            Log.e(TAG, e.getMessage());
            return null;
        }

        InputStream inputStream = null;
        try {
            try {
                // Read data from workstation
                inputStream = connection.getInputStream();
            } catch (IOException e) {
                // Read the error from the workstation
                inputStream = connection.getErrorStream();
                Log.e(TAG, e.getMessage());
            }

            // Can you think of a way to make the entire
            // HTTP more efficient using HTTP headers??

            // cache files and use the HTTP header to check for changes to the cache...
            // TODO: investigate and implement image caching

            try {
                dataBytes = StreamUtils.readUnknownFully(inputStream);
            } catch (IOException e)
            {
                Log.e(TAG, e.getMessage());
                return null;
            }
            return dataBytes;
        } finally {
            // Close the input stream if it exists.
            StreamUtils.close(inputStream);

            // Disconnect the connection
            connection.disconnect();
        }
    }

    @Override
    protected void onPostExecute(byte[] dataStream)
    {
        // What if we didn't receive a dataStream from the remote site ???
        if (dataStream == null)
        {
            // set image to a default here perhaps
            Log.w(TAG, "URL "+ mUrl + "did not fetch a dataStream for an image");
        }
        else {
            // now we can set the ImageView's bitmap to the image that we have received
            // we do it here because this is back on the UI thread.

            Bitmap bitmap = BitmapFactory.decodeByteArray(dataStream, 0, dataStream.length);
            mImageView.setImageBitmap(bitmap);
        }
    }
}

