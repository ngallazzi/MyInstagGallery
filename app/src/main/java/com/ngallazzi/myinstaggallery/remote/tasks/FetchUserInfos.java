package com.ngallazzi.myinstaggallery.remote.tasks;

import android.content.Context;

import com.ngallazzi.myinstaggallery.models.user.Data;
import com.ngallazzi.myinstaggallery.remote.api.MyApi;
import com.ngallazzi.myinstaggallery.remote.api.UserDataResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nicola on 2017-04-20.
 */

public class FetchUserInfos {
    private final String TAG = FetchUserInfos.class.getSimpleName();
    FetchUserInfosCallbacks mCallbacks;
    private Context mContext;
    private MyApi myApiInstance;
    private String mToken;

    public FetchUserInfos(FetchUserInfosCallbacks callbacks, Context context, String token) {
        mCallbacks = callbacks;
        mContext = context;
        myApiInstance = MyApi.getInstance();
        mToken = token;
    }

    public void execute() {
        Call<UserDataResponse> call = myApiInstance.getInstagramService().getUserInfos(mToken);
        call.enqueue(new Callback<UserDataResponse>() {
            @Override
            public void onResponse(Call<UserDataResponse> call, Response<UserDataResponse> response) {
                if (response.isSuccessful()) {
                    mCallbacks.onSuccess(response.body());
                }else{
                    mCallbacks.onError(response.code());
                }
            }

            @Override
            public void onFailure(Call<UserDataResponse> call, Throwable t) {
                mCallbacks.onFailure(t.getMessage());
            }
        });
    }

    public interface FetchUserInfosCallbacks {
        void onSuccess(UserDataResponse userData);
        void onError(int errorCode);
        void onFailure(String error);
    }
}


