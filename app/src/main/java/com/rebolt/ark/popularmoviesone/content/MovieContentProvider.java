package com.rebolt.ark.popularmoviesone.content;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

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

        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIE, 1);
        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIE+"/#", 2);
        uriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_FAVOURITE,3);
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
                    mcursor = sqLiteDatabase.rawQuery("select * from " + MovieContract.PATH_MOVIE,null);
                    break;
            // If the incoming URI was for a single row
            case 2:
                   // selectionArgs[0] = uri.getLastPathSegment();
                    mcursor = sqLiteDatabase.query(MovieContract.PATH_MOVIE,projection,selection,selectionArgs,null,null,sortOrder);
                /*
                 * Because this URI was for a single row, the _ID value part is
                 * present. Get the last path segment from the URI; this is the _ID value.
                 * Then, append the value to the WHERE clause for the query
                 */
                break;
            case 3:
                    mcursor = sqLiteDatabase.query(MovieContract.PATH_FAVOURITE,projection,selection,selectionArgs,null,null,sortOrder);
            default:

                // If the URI is not recognized, you should do some error handling here.
        }
        // call the code to actually do the query
       // mcursor.setNotificationUri(getContext().getContentResolver(),uri);
       // getContext().getContentResolver().notifyChange(uri,null);
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
        switch (uriMatcher.match(uri)) {


            // If the incoming URI was for all of table3
            case 3:
                try {
                    sqLiteDatabase.insertOrThrow(MovieContract.PATH_FAVOURITE, null, contentValues);
                }
                catch (SQLiteConstraintException sqlitexcept)
                {
                    Log.d("SQLITE : ",""+sqlitexcept);
                }

                break;

            default:
                try {
                    sqLiteDatabase.insertOrThrow(MovieContract.PATH_MOVIE, null, contentValues);
                }
                catch (SQLiteConstraintException sqlitexcept)
                {
                    Log.d("SQLITE : ",""+sqlitexcept);
                }
                break;
        }



       // getContext().getContentResolver().notifyChange(uri,null);

        return uri;
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
