package com.sonnyzoom.loaderdemo.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.sonnyzoom.loaderdemo.db.BookDBHelper;

/**
 * Created by zoom on 2016/3/30.
 */
public class BookProvider extends ContentProvider {

    private static final String AUTHORITY="com.sonnyzoom.loaderdemo.provider.bookprovider";
    public static final Uri URI_BOOK_ALL=Uri.parse("content://"+AUTHORITY+"/book");

    private static UriMatcher matcher;
    private BookDBHelper helper;
    private SQLiteDatabase db;

    private static final int BOOK_ALL=0;
    private static final int BOOK_ONE=1;

    static {
        matcher=new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(AUTHORITY,"book/",BOOK_ALL);
        matcher.addURI(AUTHORITY,"book/#",BOOK_ONE);
    }

    @Override
    public boolean onCreate() {
        helper=BookDBHelper.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        switch (matcher.match(uri)){

            case BOOK_ALL:

                break;
            case BOOK_ONE:
                long id= ContentUris.parseId(uri);
                selection="_id=?";
                selectionArgs=new String[]{String.valueOf(id)};
                break;
            default:
                throw new IllegalArgumentException("Wrong Uri:"+uri);
        }

        db=helper.getReadableDatabase();
        Cursor cursor=db.query(BookDBHelper.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(),URI_BOOK_ALL);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        if (matcher.match(uri)!=BOOK_ALL){
            throw new IllegalArgumentException("Wrong Uri:"+uri);
        }
        db=helper.getReadableDatabase();
        long rowId = db.insert(BookDBHelper.TABLE_NAME, null, values);
        if (rowId>0){
            notifyDataSetChanged();
            return ContentUris.withAppendedId(uri,rowId);
        }

        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    private void notifyDataSetChanged() {
        getContext().getContentResolver().notifyChange(URI_BOOK_ALL,null);
    }
}
