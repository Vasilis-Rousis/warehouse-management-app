package com.example.warehousemanagementapp;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



public class GlobalDBHandler extends AppCompatActivity
{



            public void addNewProduct(String productSerial, String productName, String price, String productQnt)
            {
                try
                {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                    OkHttpClient client = new OkHttpClient();

                    HttpUrl.Builder urlBuilder = HttpUrl.parse("http://192.168.1.4/CRUDAPI/addProduct.php").newBuilder();
                    urlBuilder.addQueryParameter("PSerialNo", productSerial);
                    urlBuilder.addQueryParameter("PName", productName);
                    urlBuilder.addQueryParameter("Price", price);
                    urlBuilder.addQueryParameter("PQnt", productQnt);

                    String url = urlBuilder.build().toString();


                    Request request = new Request.Builder()
                            .url(url)
                            .build();


                    client.newCall(request).enqueue(new Callback()
                    {
                        @Override
                        public void onFailure(Call call, IOException e)
                        {

                            //txtInfo.setText(e.getMessage());
                            //txtInfo.setText("test");
                            System.out.println("-------------------------"+e.getMessage()+"---------------------");
                            Log.d("onFailure", "failed1 " + e.getMessage());
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, final Response response) throws IOException
                        {
                            runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    try
                                    {
                                        System.out.println((response.body().string()));
                                    }
                                    catch (IOException e)
                                    {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        };
                    });
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }




            public void deleteProduct(String serialNo)
            {
                try
                {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                    OkHttpClient client = new OkHttpClient();

                    HttpUrl.Builder urlBuilder = HttpUrl.parse("http://192.168.1.4/CRUDAPI/deleteProduct.php").newBuilder();
                    urlBuilder.addQueryParameter("PSerialNo", serialNo);

                    String url = urlBuilder.build().toString();

                    Request request = new Request.Builder()
                            .url(url)
                            .build();

                    client.newCall(request).enqueue(new Callback()
                    {
                        @Override
                        public void onFailure(Call call, IOException e)
                        {
                            // Write some code here...
                        }

                        @Override
                        public void onResponse(Call call, final Response response) throws IOException
                        {
                            runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    try
                                    {
                                        System.out.println((response.body().string()));
                                    }
                                    catch (IOException e)
                                    {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        };
                    });
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            };

}