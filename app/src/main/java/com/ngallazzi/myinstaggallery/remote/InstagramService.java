package com.ngallazzi.myinstaggallery.remote;

import com.ngallazzi.myinstaggallery.models.Data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Nicola on 2017-04-18.
 */

public interface InstagramService {
    @GET("users/self/media/recent")
    Call<Data> listRecentMedia(@Query("access_token") String token);
}
