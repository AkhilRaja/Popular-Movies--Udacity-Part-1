package com.rebolt.ark.popularmoviesone.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by AkhilRaja on 10/12/16.
 */

public class ReviewModel {

    @SerializedName("content")
    private String content;
    @SerializedName("author")
    private String author;

    public String getContent()
        {
            return content;
        }
}
