package com.ngallazzi.myinstaggallery.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Nicola on 2017-04-18.
 */

public class MyApi {
    private static MyApi mInstance = null;
    public static final String BASE_URL = "https://api.instagram.com/v1/";
    private InstagramService mInstagramService;

    private MyApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.mInstagramService = retrofit.create(InstagramService.class);
    }

    public static MyApi getInstance() {
        if (mInstance == null) {
            mInstance = new MyApi();
        }
        return mInstance;
    }

    public InstagramService getInstagramService() {
        return this.mInstagramService;
    }
}
