package com.example.cinefast;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "cinefast.db";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS snacks (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "description TEXT," +
                "price INTEGER," +
                "image INTEGER)");

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM snacks", null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        if (count == 0) {
            insertSnack(db, "Popcorn", "Large Buttered", 499, R.drawable.popcorn);
            insertSnack(db, "Nachos", "With Cheese Dip", 799, R.drawable.nachos);
            insertSnack(db, "Soft Drinks", "Large Any Flavor", 599, R.drawable.drinks);
            insertSnack(db, "Candy Mix", "Assorted Candies", 699, R.drawable.candymix);
        }
    }

    private void insertSnack(SQLiteDatabase db, String name, String desc, int price, int image) {
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("description", desc);
        cv.put("price", price);
        cv.put("image", image);
        db.insert("snacks", null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS snacks");
        onCreate(db);
    }

    public ArrayList<Snack> getAllSnacks() {
        ArrayList<Snack> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM snacks", null);

        if (cursor.moveToFirst()) {

            int nameIndex = cursor.getColumnIndexOrThrow("name");
            int descIndex = cursor.getColumnIndexOrThrow("description");
            int priceIndex = cursor.getColumnIndexOrThrow("price");
            int imageIndex = cursor.getColumnIndexOrThrow("image");

            do {
                list.add(new Snack(
                        cursor.getInt(imageIndex),
                        cursor.getString(nameIndex),
                        cursor.getString(descIndex),
                        cursor.getInt(priceIndex)
                ));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }
}