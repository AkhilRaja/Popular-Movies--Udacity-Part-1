package com.rebolt.ark.popularmoviesone;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by AkhilRaja on 11/12/16.
 */

public class MovieDBHelper extends SQLiteOpenHelper{

    private static final String Database_Name = "Movie.db";
    private static final int Database_Version = 8;



    public MovieDBHelper(Context context) {
        super(context, Database_Name, null, Database_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(MovieContract.Movie.DATABASE_CREATE);
        sqLiteDatabase.execSQL(MovieContract.Favourite.DATABASE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.Movie.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.Favourite.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }
}
