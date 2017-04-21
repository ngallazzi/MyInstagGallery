package com.ngallazzi.myinstaggallery.remote.tasks;

import android.content.Context;

import com.ngallazzi.myinstaggallery.remote.api.MyApi;
import com.ngallazzi.myinstaggallery.remote.api.RecentMediaResponse;

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
        Call<RecentMediaResponse> call = myApiInstance.getInstagramService().listRecentMedia(mToken);
        call.enqueue(new Callback<RecentMediaResponse>() {
            @Override
            public void onResponse(Call<RecentMediaResponse> call, Response<RecentMediaResponse> response) {
                if (response.isSuccessful()) {
                    mCallbacks.onSuccess(response.body());
                }else{
                    mCallbacks.onError(response.code());
                }
            }

            @Override
            public void onFailure(Call<RecentMediaResponse> call, Throwable t) {
                mCallbacks.onFailure(t.getMessage());
            }
        });
    }

    public interface FetchUserRecentMediaCallbacks {
        void onTaskStarted();
        void onSuccess(RecentMediaResponse response);
        void onError(int errorCode);
        void onFailure(String error);
    }
}


