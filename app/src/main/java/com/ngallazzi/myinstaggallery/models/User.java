package com.ngallazzi.myinstaggallery.models;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nicola on 2017-04-19.
 */

public class User {
    @SerializedName("full_name")
    String fullName;
    @SerializedName("profile_picture")
    String profile_picture;
    @SerializedName("username")
    String username;
}
