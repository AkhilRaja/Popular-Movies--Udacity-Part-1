package com.rebolt.ark.popularmoviesone.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by AkhilRaja on 09/12/16.
 */

public class VideoResponse {
    @SerializedName("id")
    private int id;
    @SerializedName("results")
    private List<VideoModel> results;

    public List<VideoModel> getResults()
    {
        return results;
    }
    public int getId()
    { return id;}
}
