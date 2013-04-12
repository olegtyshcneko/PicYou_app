package com.example.PicYou_app.Activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import com.example.PicYou_app.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> imagesUrl;

    public ImageAdapter(Context c, List<String> imagesUrl) {
        mContext = c;
        this.imagesUrl = imagesUrl;
    }

    @Override
    public int getCount() {
        return imagesUrl.size();
    }

    @Override
    public Object getItem(int position) {
        return imagesUrl.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ImageView imageView;
        if(convertView == null) {
            imageView = new ImageView(mContext);
            new DownloadImageTask(imageView).execute(imagesUrl.get(position));
        } else {
            imageView = (ImageView) convertView;
        }
        return imageView;
    }
}
