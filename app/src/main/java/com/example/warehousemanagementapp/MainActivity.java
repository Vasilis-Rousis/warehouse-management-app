package com.example.warehousemanagementapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;


import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    VideoView vv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


            final ConnectivityManager connMgr = (ConnectivityManager) MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);

            if (connMgr != null) {
                NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

                if (activeNetworkInfo != null) { // connected to the internet
                    // connected to the mobile provider's data plan
                    if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                        // connected to wifi
                    }
                }else {
                    // setup the alert builder
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Wifi Connection");
                    builder.setMessage("This app requires a Wifi connection. Please Connect to a Wifi network to use this app.");

                    // add a button
                    builder.setPositiveButton("OK", null);

                    // create and show the alert dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }


        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        vv = (VideoView)findViewById(R.id.videoView);

        Uri uri = Uri.parse("android.resource://com.example.warehousemanagementapp/res/"
                + R.raw.test);

        vv.setVideoURI(uri);
        vv.requestFocus();
        vv.setOnPreparedListener(mediaPlayer -> vv.start());

        //Video Loop
        vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                vv.start(); //need to make transition seamless.
            }
        });

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.page_1);
    }

    public void switchToQR() {
        Intent switchActivityIntent = new Intent(MainActivity.this, QRScanner.class);
        startActivity(switchActivityIntent);
    }

    public void switchToList() {
        Intent switchActivityIntent = new Intent(MainActivity.this, ViewProducts.class);
        startActivity(switchActivityIntent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.page_2:
                Intent switchActivityIntentQR = new Intent(MainActivity.this, QRScanner.class);
                startActivity(switchActivityIntentQR);
                return true;

            case R.id.page_3:
                Intent switchActivityIntentList = new Intent(MainActivity.this, ViewProducts.class);
                startActivity(switchActivityIntentList);
                return true;
        }
        return false;
    }
}
