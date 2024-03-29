package com.example.PicYou_app.Activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.InputStream;


public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView bmImage;
    private int size;

    public DownloadImageTask(ImageView bmImage, int size) {
        this.bmImage = bmImage;
        this.size = size;
    }


    protected Bitmap doInBackground(String... urls) {
        String urlDisplay = urls[0];
        Bitmap bitmapImage = null;
        try {
            InputStream in = new java.net.URL(urlDisplay).openStream();
            bitmapImage = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.d(GeneralActivity.DEBUG_TAG, e.getMessage());
            e.printStackTrace();
        }
        return bitmapImage;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
        bmImage.setLayoutParams(new GridView.LayoutParams(size, size));
        bmImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }
}
