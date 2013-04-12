package com.example.PicYou_app.Activities;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.example.PicYou_app.R;
import com.olegtyshchenko.PicYouAPI.PicYouApi;

public class LoginActivity extends GeneralActivity {

    WebView wView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initWebView();
        //authorize();
    }

    @Override
    protected void onDestroy() {
        wView.clearCache(true);
        super.onDestroy();
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
        authorize();
    }

    private void authorize() {
        final ProgressDialog progressDialog = ProgressDialog.show(this,
                "Authorizing", "Please wait...", true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(!auth.isInitialized()) {
                    auth.initializeWithOptions(PicYouApi.class, "picapp:///");
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        wView.loadUrl(auth.getAuthorizationUrl());
                        wView.setVisibility(View.VISIBLE);
                    }
                });
                progressDialog.dismiss();
            }
        }).start();
    }

    private void initWebView() {
        wView = (WebView) findViewById(R.id.loginWebView);
        wView.setWebViewClient(new WebViewController());
        wView.getSettings().setJavaScriptEnabled(true);
    }

    public class WebViewController extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(url.contains("picapp")) {
                Intent i = new Intent("com.example.PicYou_app.MainActivity");
                i.putExtra("verifier_url", url);
                app.getCurrentActivity().startActivity(i);
                return false;
            }
            view.loadUrl(url);
            return true;
        }
    }

}