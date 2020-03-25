package com.rebolt.ark.popularmoviesone.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rebolt.ark.popularmoviesone.content.MovieContract;
import com.rebolt.ark.popularmoviesone.R;
import com.rebolt.ark.popularmoviesone.activity.MainActivity;
import com.rebolt.ark.popularmoviesone.model.Movie;

import java.util.List;

/**
 * Created by Akhil on 16-08-2016.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    private Context mContext;
    private List<Movie>movieList;


    public MovieAdapter(Context mContext, List<Movie> movielist) {
        this.mContext = mContext;
        this.movieList = movielist;
    }

    public MovieAdapter(MainActivity mContext, List<Movie> movielist) {
        this.mContext = mContext;
        this.movieList = movielist;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail;
        public ImageButton imageButton;

        public MyViewHolder(View view) {
            super(view);
            imageButton = view.findViewById(R.id.favorite_button);
            title = view.findViewById(R.id.title);
            count = view.findViewById(R.id.count);
            thumbnail = view.findViewById(R.id.thumbnail);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.carddetail, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.title.setText(movieList.get(position).getTitle());
        holder.count.setText(""+movieList.get(position).getVoteAverage());
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            ContentValues contentValues2 = new ContentValues();
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Favourite Added!",
                        Toast.LENGTH_LONG).show();
                contentValues2.put(MovieContract.Favourite.COLUMN_ID, movieList.get(position).getId());
                contentValues2.put(MovieContract.Favourite.COLUMN_TITLE, movieList.get(position).getTitle());
                contentValues2.put(MovieContract.Favourite.COLUMN_VOTE, movieList.get(position).getVoteAverage());
                contentValues2.put(MovieContract.Favourite.COLUMN_POSTER, movieList.get(position).getPosterPath());
                contentValues2.put(MovieContract.Favourite.COLUMN_BACKDROP, movieList.get(position).getBackdropPath());
                contentValues2.put(MovieContract.Favourite.COLUMN_OVERVIEW, movieList.get(position).getOverview());

                mContext.getContentResolver().insert(MovieContract.Favourite.CONTENT_URI, contentValues2);

                Log.d("S","Here"+movieList.get(position).getId()+movieList.get(position).getTitle()+movieList.get(position).getVoteAverage()
                + movieList.get(position).getBackdropPath()+movieList.get(position).getOverview());
            }
        });
        // loading album cover using Glide library
        Glide.with(mContext).load("https://image.tmdb.org/t/p/w185"+movieList.get(position).getPosterPath()).into(holder.thumbnail);

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}
