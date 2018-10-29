package vn.edu.poly.manager.Component;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import vn.edu.poly.manager.Networking.NetworkStateMonitor;
import vn.edu.poly.manager.R;

public abstract class BaseActivity extends AppCompatActivity{

    public static String token = "";
    public static String id = "";
    public static String author_id = "";
    public static String category_id = "";
    public static String title = "";
    public static String seo_title = "";
    public static String excerpt = "";
    public static String body = "";
    public static String image = "";
    public static String slug = "";
    public static String meta_description = "";
    public static String meta_keywords = "";
    public static String status = "";
    public static String featured = "";
    public static String created_at = "";
    public static String updated_at = "";
    public static String name = "";
    public static String avatar = "";
    public static int NumberFind;
    public static SharedPreferences dataLogin;
    public static SharedPreferences.Editor editor;
    private final int MY_PERMISSIONS_REQUEST_INTERNET = 10;
    BroadcastReceiver broadcastReceiver;
    public Snackbar snackbar;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = getWindow().getDecorView().getRootView();
        checkInternetPermission(this);
        dataLogin  = getSharedPreferences("data_login", MODE_PRIVATE);
        snackbar = Snackbar.make(view, "Not connect internet", Snackbar.LENGTH_INDEFINITE)
                .setAction("Setting", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                    }
                })
                .setActionTextColor(Color.RED);
        View viewSnackBar = snackbar.getView();
        TextView txt_snack = viewSnackBar.findViewById(android.support.design.R.id.snackbar_text);
        TextView txt_snack_action = viewSnackBar.findViewById(android.support.design.R.id.snackbar_action);
        txt_snack.setTypeface(Typeface.createFromAsset(getAssets(),
                "roboto_regular.ttf"));
        txt_snack_action.setTypeface(Typeface.createFromAsset(getAssets(),
                "roboto_regular.ttf"));
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (new NetworkStateMonitor().checkInterNet(context)){
                    snackbar.dismiss();
                } else {
                    snackbar.show();
                }
            }
        };
    }

    public void checkInternetPermission(Activity thisActivity) {

        if (ContextCompat.checkSelfPermission(thisActivity,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(thisActivity,
                    Manifest.permission.INTERNET)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                ActivityCompat.requestPermissions(thisActivity,
                        new String[]{Manifest.permission.INTERNET},
                        MY_PERMISSIONS_REQUEST_INTERNET);
            }
        } else {
            // Permission has already been granted
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_INTERNET: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    finish();
                    System.exit(0);
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(broadcastReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }
}
