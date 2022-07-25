package com.example.warehousemanagementapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "warehousedb";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_NAME = "products";

    // below variable is for our id column.
    private static final String ID_COL = "PID";

    // below variable is for our course name column
    private static final String SERIALNO_COL = "PSerialNo";

    // below variable id for our course duration column.
    private static final String PNAME_COL = "PName";

    // below variable for our course description column.
    private static final String PRICE_COL = "Price";

    // below variable is for our course tracks column.
    private static final String QUANTITY_COL = "PQnt";

    // creating a constructor for our database handler.
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SERIALNO_COL + " TEXT,"
                + PNAME_COL + " TEXT,"
                + PRICE_COL + " TEXT,"
                + QUANTITY_COL + " TEXT)";

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
    }

    // this method is use to add new course to our sqlite database.
    public void addNewProduct(String productSerial, String productName, String price, String productQnt) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        boolean exists = CheckIfProductExists(TABLE_NAME,SERIALNO_COL,productSerial);
        if (exists == true){
            String Query = " Select " + QUANTITY_COL + " from " + TABLE_NAME + " where " + SERIALNO_COL + " = " + productSerial + " limit 1 ";
            Cursor cursorQnt = db.rawQuery(Query, null);
            int oldqnt;
            if (cursorQnt.moveToFirst()) {
                do {
                    oldqnt = Integer.parseInt(cursorQnt.getString(0));

                } while (cursorQnt.moveToNext());
            int scannedqnt = Integer.parseInt(productQnt);
            int newqnt = oldqnt + scannedqnt;
            ContentValues values = new ContentValues();
            values.put(QUANTITY_COL, newqnt);
            db.update(TABLE_NAME, values, SERIALNO_COL + " = ?" , new String[]{productSerial});
            }

            cursorQnt.close();

        } else {

            // on below line we are creating a
            // variable for content values.
            ContentValues values = new ContentValues();

            // on below line we are passing all values
            // along with its key and value pair.
            values.put(SERIALNO_COL, productSerial);
            values.put(PNAME_COL, productName);
            values.put(PRICE_COL, price);
            values.put(QUANTITY_COL, productQnt);

            // after adding all values we are passing
            // content values to our table.
            db.insert(TABLE_NAME, null, values);
        }

        // at last we are closing our
        // database after adding database.
        db.close();
    }

    // we have created a new method for reading all the courses.
    public ArrayList<ProductModal> viewProducts()
    {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to read data
        // from database.
        Cursor cursorProducts = db.rawQuery("SELECT * FROM " + TABLE_NAME,
                null);

        // on below line we are creating a new array list.
        ArrayList<ProductModal> productModalArrayList = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorProducts.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our
                // array list.
                productModalArrayList.add(new
                        ProductModal(cursorProducts.getString(1),

                        cursorProducts.getString(3),

                        cursorProducts.getString(4),

                        cursorProducts.getString(2)));
            } while (cursorProducts.moveToNext());
            // moving our cursor to next.
        }

        // at last closing our cursor
        // and returning our array list.
        cursorProducts.close();
        return productModalArrayList;
    }

    // below is the method for deleting our course.
    public void deleteProduct(String serialNo) {

        // on below line we are creating
        // a variable to write our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are calling a method to delete our
        // course and we are comparing it with our course name.
        db.delete(TABLE_NAME, "PSerialNo=?", new String[]{serialNo});
        db.close();
    }

    public boolean CheckIfProductExists(String TableName, String dbfield, String fieldValue) {
        SQLiteDatabase db = this.getReadableDatabase();
        String Query = "Select * from " + TableName + " where " + dbfield + " = " + fieldValue;
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
