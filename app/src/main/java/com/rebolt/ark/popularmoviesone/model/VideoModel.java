package com.rebolt.ark.popularmoviesone.model;

import android.provider.MediaStore;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by AkhilRaja on 09/12/16.
 */

public class VideoModel {

    @SerializedName("id")
        private String id;
    @SerializedName("iso_639_1")
        private String lang;
    @SerializedName("key")
        private String key;
    @SerializedName("name")
        private String name;
    @SerializedName("type")
    private String type;

    public String getKey(){return key;}
    public String getName(){return name;}
    public String getType(){return type;}
    public String getId(){return id;}

}
