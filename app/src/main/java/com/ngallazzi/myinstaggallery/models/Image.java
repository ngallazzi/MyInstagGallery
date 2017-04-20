package com.ngallazzi.myinstaggallery.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nicola on 2017-04-19.
 */

public class Image {
    @SerializedName("width")
    public int width;
    @SerializedName("height")
    public int height;
    @SerializedName("url")
    public String url;
}
