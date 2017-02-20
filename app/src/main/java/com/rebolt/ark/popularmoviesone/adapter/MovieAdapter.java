package com.rebolt.ark.popularmoviesone.adapter;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.rebolt.ark.popularmoviesone.R;
import com.rebolt.ark.popularmoviesone.activity.MainActivity;
import com.rebolt.ark.popularmoviesone.model.Movie;

import java.util.List;

/**
 * Created by pakhi on 16-08-2016.
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

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.carddetail, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.title.setText(movieList.get(position).getTitle());
        holder.count.setText(""+movieList.get(position).getVoteAverage());


        // loading album cover using Glide library
        Glide.with(mContext).load("http://image.tmdb.org/t/p/w185"+movieList.get(position).getPosterPath()).into(holder.thumbnail);

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}
