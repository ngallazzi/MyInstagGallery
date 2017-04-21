package com.ngallazzi.myinstaggallery.remote.api;

import com.google.gson.annotations.SerializedName;
import com.ngallazzi.myinstaggallery.models.media.RecentMedia;

import java.util.ArrayList;

/**
 * Created by Nicola on 2017-04-19.
 */

public class RecentMediaResponse {
    @SerializedName("data")
    public ArrayList<RecentMedia> media;
}
