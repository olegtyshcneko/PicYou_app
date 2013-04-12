package com.example.PicYou_app.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.GridView;
import com.example.PicYou_app.Helper;
import com.example.PicYou_app.R;
import com.olegtyshchenko.PicYouAPI.Models.Image;
import com.olegtyshchenko.PicYouAPI.Models.Responses.ImagesResponse;
import com.olegtyshchenko.PicYouAPI.PicYouApi;
import com.olegtyshchenko.PicYouAPI.ResponseParser;
import org.scribe.model.Verifier;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends GeneralActivity {

    ResponseParser parser;
    GridView gridView;
    List<String> imagesUrl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        gridView = (GridView) findViewById(R.id.gridImageView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(MyPrefs.loadAccessToken().isEmpty()) {
            initialize(false);
        } else {
            initialize(true);
        }
    }

    //Making back button ignore RouteActivity
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onClick(View v) {
        requestImages();
    }

    private void requestImages() {
        final ProgressDialog progressDialog = ProgressDialog.show(this,
                "Synchronizing", "Please wait...", true);
        imagesUrl = new ArrayList<String>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String recent = auth.getConnector().recent();
                ImagesResponse recentImages = parser.imagesParse(recent);

                for(Image image: recentImages.images) {
                    //String resp = auth.getConnector().image(image.url);
                    //ImageResponse imageResponse = parser.imageParse(resp);
                    imagesUrl.add(image.small.url);
                }
                progressDialog.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showImages();
                    }
                });
            }
        }).start();
    }
    private void showImages() {
        gridView.setAdapter(new ImageAdapter(this, imagesUrl));
    }

    private void initialize(Boolean service) {
        final ProgressDialog progressDialog = ProgressDialog.show(this,
                "Verifying", "Please wait...", true);
        parser = new ResponseParser();

        if(service) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if(!auth.isInitialized()) {
                        auth.initializeWithOptions(PicYouApi.class, "picapp:///");
                        auth.setAccessToken(MyPrefs.loadAccessToken());
                    }
                    progressDialog.dismiss();
                }
            }).start();

        } else {
            final Verifier ver = Helper.parseVerifier(
                    getIntent().getStringExtra("verifier_url"));

            new Thread(new Runnable() {
                @Override
                public void run() {
                    if(auth.isInitialized()) {
                        auth.initializeAccessToken(ver);
                        MyPrefs.saveAccessToken(auth.getAccessToken());
                    }
                    progressDialog.dismiss();
                }
            }).start();
        }
    }
}