package com.example.PicYou_app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * Application class, created for saving global variables
 * and specific application logic
 */
public class PicApp extends Application {
    private Bundle globalBundle = new Bundle();
    public Bundle getGlobalBundle() {
        return globalBundle;
    }

    private Activity _currentActivity = null;

    public Activity getCurrentActivity() {
        return _currentActivity;
    }
    public void setCurrentActivity(Activity activity) {
        _currentActivity = activity;
    }


}
