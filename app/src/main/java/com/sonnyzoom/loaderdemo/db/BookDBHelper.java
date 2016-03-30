package com.sonnyzoom.loaderdemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zoom on 2016/3/30.
 */
public class BookDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Book.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "BookInfo";
    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String PRICE = "price";

    private static final String CREATE_DATABASE="CREATE TABLE "+TABLE_NAME+" ( "
            +ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"
            +NAME+" TEXT,"
            +PRICE+" INTEGER)";

    private volatile static BookDBHelper helper;

    private BookDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static BookDBHelper getInstance(Context context) {

        if (helper == null) {
            synchronized (BookDBHelper.class) {
                if (helper == null) {
                    helper = new BookDBHelper(context);
                }
            }
        }

        return helper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
