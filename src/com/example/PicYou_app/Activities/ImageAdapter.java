package com.example.PicYou_app.Activities;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

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
