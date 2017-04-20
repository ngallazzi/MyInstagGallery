package com.ngallazzi.myinstaggallery;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.ngallazzi.myinstaggallery.models.Data;
import com.ngallazzi.myinstaggallery.models.RecentMedia;
import com.ngallazzi.myinstaggallery.remote.FetchUserRecentMedia;
import com.ngallazzi.myinstaggallery.remote.MyApi;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Nicola on 2017-04-20.
 */

public class GalleryActivity extends AppCompatActivity {
    private final static String TAG = GalleryActivity.class.getSimpleName();
    @BindView(R.id.civUserImage)
    CircleImageView civUserImage;
    @BindView(R.id.tvUserPostsNumber)
    TextView tvUserPostsNumber;
    Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);
        mContext = this;
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(mContext);
        String token = sharedPref.getString(getString(R.string.token_id),"");
        getUserData(token);
    }
    public void getUserData(String token){
        new FetchUserRecentMedia(new FetchUserRecentMedia.FetchUserRecentMediaCallbacks() {
            @Override
            public void onTaskStarted() {

            }

            @Override
            public void onSuccess(Data userData) {
                parseRecentMedia(userData.media);
            }

            @Override
            public void onError(int errorCode) {
                Toast.makeText(mContext,getString(R.string.network_error,errorCode),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String error) {
                Log.d(TAG,"An error occurred: " + error);
            }
        },mContext,token).execute();
    }

    public void parseRecentMedia(ArrayList<RecentMedia> media){
        for (RecentMedia rm : media){
            Log.v(TAG,rm.images.thumbnail.url);
        }
    }
}
