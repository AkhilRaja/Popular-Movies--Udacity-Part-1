package com.rebolt.ark.popularmoviesone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rebolt.ark.popularmoviesone.R;
import com.rebolt.ark.popularmoviesone.model.Movie;

public class Detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
      //  Movie myParcelableObject = i.getParcelableExtra("name_of_extra");
        Bundle b = getIntent().getExtras();
        Movie obj =
                b.getParcelable("name_of_extra");

        ImageView backdrop;
        backdrop = (ImageView) findViewById(R.id.backdrop);

        ImageView poster;
        poster = (ImageView) findViewById(R.id.poster_detail);


        TextView title;
        title = (TextView) findViewById(R.id.title_detail);

        TextView date;
        date = (TextView) findViewById(R.id.date_detail);

        TextView overview;
        overview = (TextView) findViewById(R.id.description_detail);


        Log.d("THis",obj.getBackdropPath());
        Glide.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w185"+obj.getBackdropPath()).into(backdrop);
        Glide.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w185"+obj.getPosterPath()).into(poster);
        title.setText(obj.getTitle());
        date.setText(obj.getReleaseDate());
        overview.setText(obj.getOverview());

    }

}
