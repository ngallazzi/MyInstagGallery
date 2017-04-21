package com.ngallazzi.myinstaggallery.remote.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Nicola on 2017-04-18.
 */

public interface InstagramService {
    @GET("users/self/media/recent")
    Call<RecentMediaResponse> listRecentMedia(@Query("access_token") String token);
    @GET("users/self")
    Call<UserDataResponse> getUserInfos(@Query("access_token") String token);
}
