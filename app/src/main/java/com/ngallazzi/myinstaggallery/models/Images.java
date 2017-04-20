package com.ngallazzi.myinstaggallery.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nicola on 2017-04-20.
 */

public class Images {
    @SerializedName("thumbnail")
    public Image thumbnail;
    @SerializedName("low_resolution")
    public Image lowResolution;
    @SerializedName("standard_resolution")
    public Image standardResolution;
}
