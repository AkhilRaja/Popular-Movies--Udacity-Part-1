package com.rebolt.ark.popularmoviesone.activity;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.facebook.stetho.Stetho;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.rebolt.ark.popularmoviesone.content.MovieContract;
import com.rebolt.ark.popularmoviesone.model.Movie;
import com.rebolt.ark.popularmoviesone.adapter.MovieAdapter;
import com.rebolt.ark.popularmoviesone.R;
import com.rebolt.ark.popularmoviesone.rest.ApiClient;
import com.rebolt.ark.popularmoviesone.rest.ApiInterface;
import com.rebolt.ark.popularmoviesone.model.MoviesResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private List<Movie> movieList;
    List<Movie> movieList2 = new ArrayList<>();
    /**
     * ATTENTION: Add the API Key here at the bottom.
     */
    private static final String TAG = MainActivity.class.getSimpleName();
    private final static String API_KEY = "bb7151040747a331befa1dec25400c7b";

    ApiInterface apiService =
            ApiClient.getClient().create(ApiInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.activity_main);


        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY first from themoviedb.org", Toast.LENGTH_LONG).show();
            return;
        }
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new MovieAdapter(getApplicationContext(), movieList2);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        Cursor checkEmpty = getContentResolver().query(MovieContract.Movie.CONTENT_URI,null,null,null,null);

        if(checkEmpty.getCount() == 0) {
            Call<MoviesResponse> call = apiService.getTopRatedMovies(API_KEY);
            call.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                    int i = 0;
                    int statuscode = response.code();
                    movieList = response.body().getResults();
                    // Log.d(TAG,""+movieList.size());
                    ContentValues contentValues = new ContentValues();
                    while (i < movieList.size()) {
                        contentValues.put(MovieContract.Movie.COLUMN_ID, movieList.get(i).getId());
                        contentValues.put(MovieContract.Movie.COLUMN_TITLE, movieList.get(i).getTitle());
                        contentValues.put(MovieContract.Movie.COLUMN_VOTE, movieList.get(i).getVoteAverage());
                        contentValues.put(MovieContract.Movie.COLUMN_POSTER, movieList.get(i).getPosterPath());
                        contentValues.put(MovieContract.Movie.COLUMN_BACKDROP, movieList.get(i).getBackdropPath());
                        contentValues.put(MovieContract.Movie.COLUMN_OVERVIEW, movieList.get(i).getOverview());
                        contentValues.put(MovieContract.Movie.COLUMN_DATE, movieList.get(i).getReleaseDate());
                        contentValues.put(MovieContract.Movie.COLUMN_TYPE, 1);

                        getContentResolver().insert(MovieContract.Movie.CONTENT_URI, contentValues);
                        i++;
                    }

                    Log.d(TAG, "Successfully Written to the database !!");
                    Load_manager_data();

                }

                @Override
                public void onFailure(Call<MoviesResponse> call, Throwable t) {
                    // Log error here since request failed
                    Log.e(TAG, t.toString());
                }
            });
            Call<MoviesResponse> call2 = apiService.getPopularMovies(API_KEY);
            call2.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                    int i = 0;
                    int statuscode = response.code();
                    movieList = response.body().getResults();
                    // Log.d(TAG,""+movieList.size());
                    ContentValues contentValues = new ContentValues();
                    while (i < movieList.size()) {
                        contentValues.put(MovieContract.Movie.COLUMN_ID, movieList.get(i).getId());
                        contentValues.put(MovieContract.Movie.COLUMN_TITLE, movieList.get(i).getTitle());
                        contentValues.put(MovieContract.Movie.COLUMN_VOTE, movieList.get(i).getVoteAverage());
                        contentValues.put(MovieContract.Movie.COLUMN_POSTER, movieList.get(i).getPosterPath());
                        contentValues.put(MovieContract.Movie.COLUMN_BACKDROP, movieList.get(i).getBackdropPath());
                        contentValues.put(MovieContract.Movie.COLUMN_OVERVIEW, movieList.get(i).getOverview());
                        contentValues.put(MovieContract.Movie.COLUMN_DATE, movieList.get(i).getReleaseDate());
                        contentValues.put(MovieContract.Movie.COLUMN_TYPE, 2);
                        getContentResolver().insert(MovieContract.Movie.CONTENT_URI, contentValues);
                        i++;

                    }

                    Log.d(TAG, "Successfully Written to the database !!");

                }

                @Override
                public void onFailure(Call<MoviesResponse> call, Throwable t) {
                    // Log error here since request failed
                    Log.e(TAG, t.toString());
                }
            });

        }
        else{
            getLoaderManager().initLoader(12, null,this);
        }
        recyclerView.addOnItemTouchListener(
                new RecyclerViewTouch(getApplicationContext(), new RecyclerViewTouch.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // TODO Handle item click

                        Intent intent = new Intent(getApplicationContext(), Detail.class);
                        intent.putExtra("name_of_extra", movieList2.get(position));
                        startActivity(intent);
                    }
                })
        );
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initCollapsingToolbar();

        try {
            Glide.with(this).load(R.drawable.cover).into((ImageView) findViewById(R.id.backdrop));
            TextView smalltxt = (TextView) findViewById(R.id.movie_desc_small);
            TextView bigtxt = (TextView) findViewById(R.id.movie_desc_big);
            smalltxt.setText(R.string.Movie_small_desc_top);
            bigtxt.setText(R.string.Movie_big_desc_top);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void Load_manager_data()
    {
        getLoaderManager().initLoader(12,null,this);
    }
    ////This animation toolbar
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            getLoaderManager().initLoader(21,null,this);
            TextView smalltxt = (TextView) findViewById(R.id.movie_desc_small);
            TextView bigtxt = (TextView) findViewById(R.id.movie_desc_big);
            smalltxt.setText("Popular Movies");
            bigtxt.setText("Poplar Movies of all time");

            return true;
        }
        if (id == R.id.action_setting) {
            getLoaderManager().initLoader(12,null,this);
            TextView smalltxt = (TextView) findViewById(R.id.movie_desc_small);
            TextView bigtxt = (TextView) findViewById(R.id.movie_desc_big);
            smalltxt.setText(R.string.Movie_small_desc_top);
            bigtxt.setText(R.string.Movie_big_desc_top);

            return true;
        }

        if (id == R.id.favorite) {
            getLoaderManager().initLoader(1000, null, this);
            TextView smalltxt = (TextView) findViewById(R.id.movie_desc_small);
            TextView bigtxt = (TextView) findViewById(R.id.movie_desc_big);
            smalltxt.setText("Favourites");
            bigtxt.setText("Your Favourite Movies ");
        }
            return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        /*                   // Log.d(TAG, "Number of movies received: Hello : "+ movieList);
                    String [] args = {"372058"};
                    Uri uri = MovieContract.Movie.CONTENT_URI.buildUpon().appendPath("372058").build();

                    //This calls all of the Movies
                    //Cursor cursor = getContentResolver().query(MovieContract.Movie.CONTENT_URI,null, MovieContract.Movie.COLUMN_ID + " = ? ",args,null);

                    //This would call only the required movie
                    Cursor cursor = getContentResolver().query(uri,null, MovieContract.Movie.COLUMN_ID + " = ? ",args,null);

                    //String data = cursor.getString(cursor.getColumnIndex("_id"));
                    //Log.d(TAG,data);
        */
        Uri uri = MovieContract.Movie.CONTENT_URI.buildUpon().appendPath(""+i).build();
        CursorLoader cursorLoader; //= new CursorLoader(getApplicationContext());
        String [] args1 = {"1"};
        String [] args2 = {"2"};
        Log.d(TAG," "+i);

        if (i == 1000)
        {
            cursorLoader = new CursorLoader(getApplicationContext(),MovieContract.Favourite.CONTENT_URI,null,null,null,null);
            return cursorLoader;
        }
        else if(i == 12)
        {
            cursorLoader = new CursorLoader(getApplicationContext(),uri,null,MovieContract.Movie.COLUMN_TYPE + "=? ",args1,null);
            return cursorLoader;
        }

        else if (i == 21)
        {
            cursorLoader = new CursorLoader(getApplicationContext(),uri,null,MovieContract.Movie.COLUMN_TYPE + "=? ",args2,null);
            //cursorLoader = new CursorLoader(getApplicationContext(),uri,null,null,null,null);
            return cursorLoader;
        }

        else{
            return new CursorLoader(getApplicationContext(),MovieContract.Favourite.CONTENT_URI,null,null,null,null);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        movieList2.clear();

        Log.d(TAG + "Cursor : ",""+cursor.getCount());

        if (cursor.getCount()>0)
            if(cursor.moveToFirst()){
            do{
                // String data = cursor.getString(cursor.getColumnIndex(MovieContract.Movie.COLUMN_TITLE));
                // Log.d(TAG,data);
                // do what ever you want here
                movieList2.add(new Movie(
                        cursor.getString(cursor.getColumnIndex("poster_path")),
                        cursor.getString(cursor.getColumnIndex("overview")),
                        cursor.getInt(cursor.getColumnIndex("_id")),
                        cursor.getString(cursor.getColumnIndex("title")),
                        cursor.getString(cursor.getColumnIndex("backdrop_path")),
                        cursor.getDouble(cursor.getColumnIndex("vote_count")))
                );
            }while(cursor.moveToNext());

        }
        Log.d(TAG,"MovieList Size  = "+movieList2.size());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
