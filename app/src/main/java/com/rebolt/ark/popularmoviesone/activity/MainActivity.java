package com.rebolt.ark.popularmoviesone.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.rebolt.ark.popularmoviesone.model.Movie;
import com.rebolt.ark.popularmoviesone.adapter.MovieAdapter;
import com.rebolt.ark.popularmoviesone.R;
import com.rebolt.ark.popularmoviesone.rest.ApiClient;
import com.rebolt.ark.popularmoviesone.rest.ApiInterface;
import com.rebolt.ark.popularmoviesone.model.MoviesResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.v7.recyclerview.R.attr.layoutManager;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private List<Movie> movieList;
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
        setContentView(R.layout.activity_main);


        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY first from themoviedb.org", Toast.LENGTH_LONG).show();
            return;
        }
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(mLayoutManager);


        Call<MoviesResponse> call = apiService.getTopRatedMovies(API_KEY);

        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                int statuscode = response.code();
                movieList = response.body().getResults();
                adapter = new MovieAdapter(getApplicationContext(), movieList);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);

                Log.d(TAG, "Number of movies received: " + movieList);
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });

        recyclerView.addOnItemTouchListener(
                new RecyclerViewTouch(getApplicationContext(), new RecyclerViewTouch.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // TODO Handle item click

                        Intent intent = new Intent(getApplicationContext(), Detail.class);
                        intent.putExtra("name_of_extra", movieList.get(position));
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



    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
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


            Call<MoviesResponse> call = apiService.getPopularMovies(API_KEY);

            call.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(Call<MoviesResponse>call, Response<MoviesResponse> response) {
                    int statuscode = response.code();
                    movieList = response.body().getResults();
                    adapter = new MovieAdapter(getApplicationContext(),movieList);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(adapter);

                    TextView smalltxt = (TextView) findViewById(R.id.movie_desc_small);
                    TextView bigtxt = (TextView) findViewById(R.id.movie_desc_big);

                    smalltxt.setText(R.string.Movie_small_desc_most);
                    bigtxt.setText(R.string.Movie_big_desc_most);

                    Log.d(TAG, "Number of movies received: " + movieList.get(1).getTitle());
                }

                @Override
            public void onFailure(Call<MoviesResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
            return true;
        }
        if (id == R.id.action_setting) {


            Call<MoviesResponse> call = apiService.getTopRatedMovies(API_KEY);

            call.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(Call<MoviesResponse>call, Response<MoviesResponse> response) {
                    int statuscode = response.code();
                    movieList = response.body().getResults();
                    adapter = new MovieAdapter(getApplicationContext(),movieList);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(adapter);

                    TextView smalltxt = (TextView) findViewById(R.id.movie_desc_small);
                    TextView bigtxt = (TextView) findViewById(R.id.movie_desc_big);
                    smalltxt.setText(R.string.Movie_small_desc_top);
                    bigtxt.setText(R.string.Movie_big_desc_top);

                    Log.d(TAG, "Number of movies received: " + movieList.get(1).getTitle());
                }

                @Override
                public void onFailure(Call<MoviesResponse>call, Throwable t) {
                    // Log error here since request failed
                    Log.e(TAG, t.toString());
                }
            });
            return true;
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
}
