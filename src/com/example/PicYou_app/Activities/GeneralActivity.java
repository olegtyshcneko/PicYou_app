package com.example.PicYou_app.Activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.example.PicYou_app.PicApp;
import com.olegtyshchenko.PicYouAPI.Authenticator;
import org.scribe.model.Token;


//Activity to save shared state and logic
public abstract class GeneralActivity extends Activity {
    public static final String DEBUG_TAG = "Pic_You";
    private static final String PREFS_NAME = "PicPrefs";


    private static SharedPreferences appPreferences;
    //Authenticator is free for all activities
    static Authenticator auth = Authenticator.getAuthenticator();

    //All activities will have access to shared state through app variable
    PicApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (PicApp) this.getApplicationContext();
        appPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        app.setCurrentActivity(this);
    }
    @Override
    protected void onPause() {
        clearActivity();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        clearActivity();
        super.onDestroy();
    }

    private void clearActivity() {
        Activity currActivity = app.getCurrentActivity();
        if(currActivity!=null && currActivity.equals(this)) {
            app.setCurrentActivity(null);
        }
    }

    public static class MyPrefs {
        public static void saveAccessToken(Token token) {
            if(!(token.isEmpty()
                    && appPreferences.getString("access_token", null) == null
                    && appPreferences.getString("access_secret", null) == null)) {
                SharedPreferences.Editor editor = appPreferences.edit();
                editor.putString("access_token", token.getToken());
                editor.putString("access_secret", token.getSecret());
                editor.commit();
            }
        }
        public static Token loadAccessToken() {
            String access_token = appPreferences.getString("access_token", null);
            String access_secret = appPreferences.getString("access_secret", null);
            if(access_token == null || access_secret == null) {
                return Token.empty();
            } else {
                return new Token(access_token, access_secret);
            }
        }
    }
}
