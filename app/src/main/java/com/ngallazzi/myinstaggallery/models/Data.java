package com.ngallazzi.myinstaggallery.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Nicola on 2017-04-19.
 */

public class Data {
    @SerializedName("data")
    public ArrayList<RecentMedia> media;
}
