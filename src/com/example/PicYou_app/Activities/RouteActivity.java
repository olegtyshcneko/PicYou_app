package com.example.PicYou_app.Activities;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;
import com.example.PicYou_app.R;

//Activity which will decide where to route user, checking if there is valid access token
public class RouteActivity extends GeneralActivity implements DialogInterface.OnDismissListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blank);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkConnectionAndRoute();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 0:
                return buildExitDialog();
        }
        return super.onCreateDialog(id);
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        checkConnectionAndRoute();
    }

    private AlertDialog buildExitDialog() {
        return new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_launcher)
                .setTitle("No internet connection")
                .setPositiveButton("Retry", new Dialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getBaseContext(),
                                "Retrying connection...", Toast.LENGTH_SHORT).show();
                        showDialog(0);
                    }
                })
                .setNegativeButton("Cancel", new Dialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        moveTaskToBack(true);
                    }
                })
                .setMessage("We're sorry, but " +
                        "this application can't work without internet connection.")
                .setOnDismissListener(this)
                .create();
    }

    private void checkConnectionAndRoute() {
        if (!isOnline()) {
            showDialog(0);
        } else {
            route();
        }
    }

    private void route() {
        if (MyPrefs.loadAccessToken().isEmpty()) {
            startActivity(new Intent("com.example.PicYou_app.LoginActivity")
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        } else {
            //todo: check if access token is not old enough?
            startActivity(new Intent("com.example.PicYou_app.MainActivity")
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
    }

    private Boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
