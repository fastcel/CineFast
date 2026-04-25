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

        db.execSQL("CREATE TABLE snacks (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "description TEXT," +
                "price INTEGER," +
                "image INTEGER)");

        // 🔥 INSERT INITIAL DATA
        insertSnack(db, "Popcorn", "Large Buttered", 499, R.drawable.popcorn);
        insertSnack(db, "Nachos", "With Cheese Dip", 799, R.drawable.nachos);
        insertSnack(db, "Soft Drinks", "Large Any Flavor", 599, R.drawable.drinks);
        insertSnack(db, "Candy Mix", "Assorted Candies", 699, R.drawable.candymix);
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

    // 🔥 FETCH SNACKS
    public ArrayList<Snack> getAllSnacks() {
        ArrayList<Snack> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM snacks", null);

        if (cursor.moveToFirst()) {
            do {
                list.add(new Snack(
                        cursor.getInt(cursor.getColumnIndexOrThrow("image")),
                        cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        cursor.getString(cursor.getColumnIndexOrThrow("description")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("price"))
                ));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }
}