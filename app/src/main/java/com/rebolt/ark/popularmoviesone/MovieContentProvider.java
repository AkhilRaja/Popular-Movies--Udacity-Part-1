package com.rebolt.ark.popularmoviesone;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.util.Log;

import static com.rebolt.ark.popularmoviesone.MovieContract.*;

/**
 * Created by AkhilRaja on 15/12/16.
 */

public class MovieContentProvider extends ContentProvider
{

    private MovieDBHelper movieDBHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Cursor mcursor;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_MOVIE, 1);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_MOVIE+"/#", 2);

    }

    @Override
    public boolean onCreate() {

        movieDBHelper = new MovieDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection,
                        String selection,
                        String[] selectionArgs,
                        String sortOrder) {


        sqLiteDatabase = movieDBHelper.getReadableDatabase();
        switch (uriMatcher.match(uri)) {


            // If the incoming URI was for all of table3
            case 1:
                    mcursor = sqLiteDatabase.rawQuery("select * from " + PATH_MOVIE,null);
                    break;
            // If the incoming URI was for a single row
            case 2:
                   // selectionArgs[0] = uri.getLastPathSegment();
                    mcursor = sqLiteDatabase.query(PATH_MOVIE,projection,selection,selectionArgs,null,null,sortOrder);
                /*
                 * Because this URI was for a single row, the _ID value part is
                 * present. Get the last path segment from the URI; this is the _ID value.
                 * Then, append the value to the WHERE clause for the query
                 */
                break;

            default:

                // If the URI is not recognized, you should do some error handling here.
        }
        // call the code to actually do the query
        return mcursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {

        sqLiteDatabase = movieDBHelper.getWritableDatabase();
        try {
            sqLiteDatabase.insertOrThrow(MovieContract.PATH_MOVIE, null, contentValues);
        }
        catch (SQLiteConstraintException sqlitexcept)
        {
            Log.d("SQLITE : ",""+sqlitexcept);
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
