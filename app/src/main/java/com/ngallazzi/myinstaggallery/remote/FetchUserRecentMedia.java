package com.ngallazzi.myinstaggallery.remote;

import android.content.Context;
import android.util.Log;

import com.ngallazzi.myinstaggallery.models.Data;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nicola on 2017-04-20.
 */

public class FetchUserRecentMedia {
    private final String TAG = FetchUserRecentMedia.class.getSimpleName();
    String mLatitude, mLongitude;
    int mTeamId;
    FetchUserRecentMediaCallbacks mCallbacks;
    private Context mContext;
    private MyApi myApiInstance;
    private String mToken;

    public FetchUserRecentMedia(FetchUserRecentMediaCallbacks callbacks, Context context, String token) {
        mCallbacks = callbacks;
        mContext = context;
        myApiInstance = MyApi.getInstance();
        mToken = token;
    }

    public void execute() {
        mCallbacks.onTaskStarted();
        Call<Data> call = myApiInstance.getInstagramService().listRecentMedia(mToken);
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if (response.isSuccessful()) {
                    mCallbacks.onSuccess(response.body());
                }else{
                    mCallbacks.onError(response.code());
                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                mCallbacks.onFailure(t.getMessage());
            }
        });
    }

    public interface FetchUserRecentMediaCallbacks {
        void onTaskStarted();
        void onSuccess(Data userData);
        void onError(int errorCode);
        void onFailure(String error);
    }
}


