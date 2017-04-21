package com.ngallazzi.myinstaggallery.models.media;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nicola on 2017-04-19.
 */

public class RecentMedia {
    @SerializedName("id")
    public String id;
    @SerializedName("images")
    public Images images;
    @SerializedName("created_time")
    String createdTime;
}
