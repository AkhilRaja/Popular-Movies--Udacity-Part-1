package com.rebolt.ark.popularmoviesone.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by AkhilRaja on 09/12/16.
 */

public class ReviewResponse {
    @SerializedName("id")
    private int id;
    @SerializedName("results")
    private List<ReviewModel> results;

    public List<ReviewModel> getResults()
    {
        return results;
    }
    public int getId()
    { return id;}
}
