package com.rebolt.ark.popularmoviesone.content;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by AkhilRaja on 15/12/16.
 */

public class MovieContract {

    static final String CONTENT_AUTHORITY = "com.rebolt.ark.popularmoviesone";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    static final String PATH_MOVIE = "movie";
    static final String PATH_FAVOURITE = "favourite";


    public static final class Movie {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();



        static final String TABLE_NAME = "movie";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_VOTE = "vote_count";
        public static final String COLUMN_POSTER = "poster_path";
        public static final String COLUMN_BACKDROP = "backdrop_path";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_TYPE = "type";


        static final String DATABASE_CREATE = "create table "
                + TABLE_NAME
                + "("
                + COLUMN_ID + " integer primary key, "
                + COLUMN_TITLE + " text not null, "
                + COLUMN_VOTE + " integer not null,"
                + COLUMN_POSTER + " text not null,"
                + COLUMN_BACKDROP + " text not null,"
                + COLUMN_OVERVIEW + " text not null,"
                + COLUMN_DATE + " text not null,"
                + COLUMN_TYPE + " integer not null "
                + ");";


    }
    public static final class Favourite {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVOURITE).build();

        static final String TABLE_NAME = "favourite";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_VOTE = "vote_count";
        public static final String COLUMN_POSTER = "poster_path";
        public static final String COLUMN_BACKDROP = "backdrop_path";
        public static final String COLUMN_OVERVIEW = "overview";


        static final String DATABASE_CREATE = "create table "
                + TABLE_NAME
                + "("
                + COLUMN_ID + " integer primary key, "
                + COLUMN_TITLE + " text not null, "
                + COLUMN_VOTE + " integer not null,"
                + COLUMN_POSTER + " text not null,"
                + COLUMN_BACKDROP + " text not null,"
                + COLUMN_OVERVIEW + " text not null"
                + ");";


    }

}
