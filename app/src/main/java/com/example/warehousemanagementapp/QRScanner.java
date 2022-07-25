package com.example.warehousemanagementapp;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.VIBRATE;


import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import eu.livotov.labs.android.camview.ScannerLiveView;
import eu.livotov.labs.android.camview.camera.CameraController;
import eu.livotov.labs.android.camview.scanner.decoder.zxing.ZXDecoder;

public class QRScanner extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    private ScannerLiveView camera;

    private com.example.warehousemanagementapp.DBHandler localdb;
    private com.example.warehousemanagementapp.GlobalDBHandler globaldb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_qrscanner);

        getSupportActionBar().hide();

        // check permission method is to check that the camera permission is granted by user or not.
        // request permission method is to request the camera permission if not given.

        if (checkPermission()){
            // if permission is already granted display a toast message
            Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
        }
        else {
            requestPermission();
        }
        //initialize scannerLiveview and textview.
        camera = (ScannerLiveView) findViewById(R.id.camview);

        camera.setScannerViewEventListener(new ScannerLiveView.ScannerViewEventListener() {
            @Override
            public void onScannerStarted(ScannerLiveView scanner) {
                //method is called when scanner is started
                Toast.makeText(QRScanner.this, "Scanner Started", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onScannerStopped(ScannerLiveView scanner) {
                // method is called when scanner is stoped.
                Toast.makeText(QRScanner.this, "Scanner Stopped", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onScannerError(Throwable err) {
                // method is called when scanner gives some error.
                Toast.makeText(QRScanner.this, "Scanner Error: " + err.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeScanned(String data) {
                // method is called when camera scans the qr code and the data from qr code is stored in data in string format.
                final ConnectivityManager connMgr = (ConnectivityManager) QRScanner.this.getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connMgr != null) {
                    NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();
                    if (activeNetworkInfo != null) { // connected to the internet
                        // connected to the mobile provider's data plan
                        if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                            // connected to wifi
                            localdb = new com.example.warehousemanagementapp.DBHandler(QRScanner.this);
                            globaldb = new com.example.warehousemanagementapp.GlobalDBHandler();
                            String[] product = data.split(",");
                            if (product.length != 4) {
                                Toast.makeText(QRScanner.this, "Invalid QR Code", Toast.LENGTH_SHORT).show();
                            } else {
                                localdb.addNewProduct(product[0], product[1], product[2], product[3]);
                                globaldb.addNewProduct(product[0], product[1], product[2], product[3]);
                                Intent switchActivityIntent = new Intent(QRScanner.this, ViewProducts.class);
                                startActivity(switchActivityIntent);
                            }
                        }
                    } else {
                        // setup the alert builder
                        AlertDialog.Builder builder = new AlertDialog.Builder(QRScanner.this);
                        builder.setTitle("Wifi Connection");
                        builder.setMessage("This feature requires a Wifi connection. Please Connect to a Wifi network and try again.");

                        // add a button
                        builder.setPositiveButton("OK", null);

                        // create and show the alert dialog
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
            }
        });

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.page_2);

    }

    @Override
    protected void onResume() {
        super.onResume();
        ZXDecoder decoder = new ZXDecoder();
        //0.5 is the area where we have to place red marker for scanning.
        decoder.setScanAreaPercent(0.8);
        //below method will set secoder to camera.
        camera.setDecoder(decoder);
        camera.startScanner();
    }

    @Override
    protected void onPause() {
        // on app pause the camera will stop scanning.
        camera.stopScanner();
        super.onPause();
    }


    private boolean checkPermission() {
        // here we are checking two permission that is vibrate and camera which is granted by user and not.
        // if permission is granted then we are returning true otherwise false.
        int camera_permission = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int vibrate_permission = ContextCompat.checkSelfPermission(getApplicationContext(), VIBRATE);

        return camera_permission == PackageManager.PERMISSION_GRANTED && vibrate_permission == PackageManager.PERMISSION_GRANTED;
    }


    private void requestPermission() {
        //this method is to request the runtime permission.
        int PERMISSION_REQUEST_CODE = 200;
        ActivityCompat.requestPermissions(this, new String[]{CAMERA, VIBRATE}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        //this method is called when user allows the permission to use camera.
        if (grantResults.length > 0) {

            boolean cameraaccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            boolean vibrateaccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

            if (cameraaccepted
                    && vibrateaccepted) {
                Toast.makeText(this, "Permission granted..", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Denined \n You cannot use app without providing permssion"
                        , Toast.LENGTH_SHORT).show();

            }
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.page_1:
                Intent switchActivityIntentQR = new Intent(QRScanner.this, MainActivity.class);
                startActivity(switchActivityIntentQR);
                return true;

            case R.id.page_3:
                Intent switchActivityIntentList = new Intent(QRScanner.this, ViewProducts.class);
                startActivity(switchActivityIntentList);
                return true;
        }
        return false;
    }
}
