package com.example.PicYou_app.Activities;

import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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

    private Dimensions getDisplayDimensions(){
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        return new Dimensions(display.getWidth(), display.getHeight());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ImageView imageView;
        Dimensions dimensions = getDisplayDimensions();
        int size = dimensions.width > 480 ? 240 : 120;

        if(convertView == null) {
            imageView = new ImageView(mContext);
            new DownloadImageTask(imageView, size).execute(imagesUrl.get(position));

        } else {
            imageView = (ImageView) convertView;
        }
        return imageView;
    }

    private class Dimensions {
        public int width;
        public int height;

        public Dimensions(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }
}
