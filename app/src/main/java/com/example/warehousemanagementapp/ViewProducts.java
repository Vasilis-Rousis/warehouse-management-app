package com.example.warehousemanagementapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ViewProducts extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    // creating variables for our array list,
    // dbhandler, adapter and recycler view.
    private ArrayList<com.example.warehousemanagementapp.ProductModal> productModalArrayList;
    private com.example.warehousemanagementapp.DBHandler dbHandler;
    private com.example.warehousemanagementapp.ProductRVAdapter productRVAdapter;
    private RecyclerView productsRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ConnectivityManager connMgr = (ConnectivityManager) ViewProducts.this.getSystemService(Context.CONNECTIVITY_SERVICE);

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
                builder.setMessage("Viewing products requires a Wifi connection. Please Connect to a Wifi network and try again.");

                // add a button
                builder.setPositiveButton("OK", null);

                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();

                Intent switchActivityIntentQR = new Intent(ViewProducts.this, MainActivity.class);
                startActivity(switchActivityIntentQR);
            }
        }

        setContentView(R.layout.activity_view_products);

        getSupportActionBar().hide();

        // initializing our all variables.
        productModalArrayList = new ArrayList<>();
        dbHandler = new com.example.warehousemanagementapp.DBHandler(ViewProducts.this);

        // getting our course array
        // list from db handler class.
        productModalArrayList = dbHandler.viewProducts();

        // on below line passing our array lost to our adapter class.
        productRVAdapter = new com.example.warehousemanagementapp.ProductRVAdapter(productModalArrayList, ViewProducts.this);
        productsRV = findViewById(R.id.idRVProducts);

        // setting layout manager for our recycler view.
        LinearLayoutManager linearLayoutManager = new
                LinearLayoutManager(ViewProducts.this, RecyclerView.VERTICAL, false);

        productsRV.setLayoutManager(linearLayoutManager);

        // setting our adapter to recycler view.
        productsRV.setAdapter(productRVAdapter);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.page_3);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.page_1:
                Intent switchActivityIntentQR = new Intent(ViewProducts.this, MainActivity.class);
                startActivity(switchActivityIntentQR);
                return true;

            case R.id.page_2:
                Intent switchActivityIntentList = new Intent(ViewProducts.this, QRScanner.class);
                startActivity(switchActivityIntentList);
                return true;
        }
        return false;
    }
}
