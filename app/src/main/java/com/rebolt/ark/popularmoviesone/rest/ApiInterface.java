package com.rebolt.ark.popularmoviesone.rest;

import com.rebolt.ark.popularmoviesone.model.MoviesResponse;
import com.rebolt.ark.popularmoviesone.model.ReviewResponse;
import com.rebolt.ark.popularmoviesone.model.VideoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by pakhi on 18-08-2016.
 */
    public interface ApiInterface {
        @GET("movie/popular")
        Call<MoviesResponse> getPopularMovies(@Query("api_key") String apiKey);
        @GET("movie/top_rated")
        Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);
        @GET("movie/{id}/videos")
        Call<VideoResponse> getVideoDetails(@Path("id") int id, @Query("api_key") String apiKey);
        @GET("movie/{id}/reviews")
        Call<ReviewResponse> getReviewDetails(@Path("id")int id, @Query("api_key") String apiKey);
    }

