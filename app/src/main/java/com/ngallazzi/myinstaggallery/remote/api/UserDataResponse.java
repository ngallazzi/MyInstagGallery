package com.ngallazzi.myinstaggallery.remote.api;

import com.google.gson.annotations.SerializedName;
import com.ngallazzi.myinstaggallery.models.user.Data;

/**
 * Created by Nicola on 2017-04-21.
 */

public class UserDataResponse {
    @SerializedName("data")
    public Data data;
}
