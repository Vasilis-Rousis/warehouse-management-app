package com.example.warehousemanagementapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductRVAdapter extends RecyclerView.Adapter<ProductRVAdapter.ViewHolder> {

    // variable for our array list and context
    private ArrayList<ProductModal> productModalArrayList;
    private Context context;
    private com.example.warehousemanagementapp.DBHandler dbHandler;
    private com.example.warehousemanagementapp.GlobalDBHandler globalDBHandler;


    // constructor
    public ProductRVAdapter(ArrayList<ProductModal> productModalArrayList, Context context) {
        this.productModalArrayList = productModalArrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // on below line we are inflating our layout
        // file for our recycler view items.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_rv_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // on below line we are setting data
        // to our views of recycler view item.
        ProductModal modal = productModalArrayList.get(position);
        holder.productSerialTV.setText(modal.getProductSerial());
        holder.productNameTV.setText(modal.getProductQnt());
        holder.priceTV.setText(modal.getProductName());
        holder.productQntTV.setText(modal.getPrice());


    }

    @Override
    public int getItemCount() {
        // returning the size of our array list
        return productModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        // creating variables for our text views.
        private TextView productSerialTV, productNameTV, priceTV, productQntTV;
        private Button deletebtn;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            // initializing our text views
            productSerialTV = itemView.findViewById(R.id.idTVProductSerial);
            productNameTV = itemView.findViewById(R.id.idTVProductName);
            priceTV = itemView.findViewById(R.id.idTVPrice);
            productQntTV = itemView.findViewById(R.id.idTVProductQuantity);
            deletebtn = itemView.findViewById(R.id.idTVDeleteButton);

            deletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbHandler = new com.example.warehousemanagementapp.DBHandler(context);
                    globalDBHandler = new com.example.warehousemanagementapp.GlobalDBHandler();
                    // calling a method to delete our course.
                    dbHandler.deleteProduct(productSerialTV.getText().toString());
                    globalDBHandler.deleteProduct(productSerialTV.getText().toString());
                    System.out.println("-------------------------"+productSerialTV.getText().toString());
                    Toast.makeText(context, "Product Deleted", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(context, ViewProducts.class);
                    context.startActivity(i);
                }
            });

        }


    }

}

