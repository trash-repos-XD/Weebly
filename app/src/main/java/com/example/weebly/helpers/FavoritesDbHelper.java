package com.example.weebly.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;


public class FavoritesDbHelper extends SQLiteOpenHelper {


    static final class FavoritesContract {
        private FavoritesContract() {}

        public class FeedEntry implements BaseColumns {
            public static final String TABLE_NAME = "favorites";
            public static final String ANIME_ID = "anime_id";
        }
    }

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "favorite.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FavoritesContract.FeedEntry.TABLE_NAME + " (" +
                    FavoritesContract.FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FavoritesContract.FeedEntry.ANIME_ID + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FavoritesContract.FeedEntry.TABLE_NAME;

    public FavoritesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public static double addFavorite(Context context,String animeId){

        FavoritesDbHelper dbHelper = new FavoritesDbHelper(context);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FavoritesContract.FeedEntry.ANIME_ID, animeId);


        return db.insert(FavoritesContract.FeedEntry.TABLE_NAME, null, values);
    }

    public static void removeFavorite(Context context,String animeId){
        FavoritesDbHelper dbHelper = new FavoritesDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = FavoritesContract.FeedEntry.ANIME_ID + " LIKE ?";
        String[] selectionArgs = { animeId };
        db.delete(FavoritesContract.FeedEntry.TABLE_NAME, selection, selectionArgs);
    }

    public static ArrayList<String> getAllFavorites(Context context)  {
        FavoritesDbHelper dbHelper = new FavoritesDbHelper(context);

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                FavoritesContract.FeedEntry.ANIME_ID,
        };


        Cursor cursor = db.query(
                FavoritesContract.FeedEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );


        ArrayList<String> roomIds = new ArrayList<>();

        while(cursor.moveToNext()) {
            String _room_id= cursor.getString(cursor.getColumnIndexOrThrow(FavoritesContract.FeedEntry.ANIME_ID));
            roomIds.add(_room_id);
        }
        cursor.close();

        return roomIds;
    }
}