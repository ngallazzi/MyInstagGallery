package com.ngallazzi.myinstaggallery;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.ngallazzi.myinstaggallery.models.user.Data;
import com.ngallazzi.myinstaggallery.remote.api.RecentMediaResponse;
import com.ngallazzi.myinstaggallery.models.media.Images;
import com.ngallazzi.myinstaggallery.models.media.RecentMedia;
import com.ngallazzi.myinstaggallery.remote.api.UserDataResponse;
import com.ngallazzi.myinstaggallery.remote.tasks.FetchUserInfos;
import com.ngallazzi.myinstaggallery.remote.tasks.FetchUserRecentMedia;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Nicola on 2017-04-20.
 */

public class GalleryActivity extends AppCompatActivity {
    private final static String TAG = GalleryActivity.class.getSimpleName();
    private final int COLUMNS_COUNT = 3;
    private final String SAVED_USER_DATA_ID = "user_data_id";
    private final String SAVED_RECYCLER_VIEW_STATUS_ID = "rv_status_id";
    private final String SAVED_RECYCLER_VIEW_DATASET_ID = "rv_dataset_id";

    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.civUserImage)
    CircleImageView civUserImage;
    @BindView(R.id.tvUserMediaCount)
    TextView tvUserMediaCount;
    @BindView(R.id.tvFollowedCount)
    TextView tvFollowedCount;
    @BindView(R.id.tvFollowedByCount)
    TextView tvFollowedByCount;

    @BindView(R.id.rvMedia)
    RecyclerView mRvMedia;

    private Data userData;
    private MediaAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Images> mDataset;
    Context mContext;
    Parcelable mListState;
    Bundle mSavedInstanceState;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);
        mContext = this;
        mSavedInstanceState = savedInstanceState;
        initRecyclerView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mSavedInstanceState==null){
            getRemoteData(); // No saved data, get data from remote
        }else{
            restorePreviousState(); // Restore data found in the Bundle
        }
    }

    public void getUserInfos(final String token){
        new FetchUserInfos(new FetchUserInfos.FetchUserInfosCallbacks() {
            @Override
            public void onTaskStarted() {

            }

            @Override
            public void onSuccess(UserDataResponse response) {
                userData = response.data;
                setUserHeader();
                getUserMedia(token);
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

    public void getUserMedia(String token){
        new FetchUserRecentMedia(new FetchUserRecentMedia.FetchUserRecentMediaCallbacks() {
            @Override
            public void onTaskStarted() {

            }

            @Override
            public void onSuccess(RecentMediaResponse userData) {
                updateDataset(userData.media);
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

    public void updateDataset(ArrayList<RecentMedia> media){
        for (RecentMedia rm : media){
            mDataset.add(rm.images);
        }
        mAdapter.notifyDataSetChanged();
    }

    public void setUserHeader(){
        Picasso.with(mContext).load(userData.profilePicture).into(civUserImage);
        tvUserName.setText(userData.username);
        tvUserMediaCount.setText(String.valueOf(userData.counts.media));
        tvFollowedCount.setText(String.valueOf(userData.counts.follows));
        tvFollowedByCount.setText(String.valueOf(userData.counts.followedBy));
    }

    public void initRecyclerView(){
        mRvMedia.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new GridLayoutManager(this,COLUMNS_COUNT);
        mRvMedia.setLayoutManager(mLayoutManager);
        mDataset = new ArrayList<Images>();
        // specify an adapter (see also next example)
        mAdapter = new MediaAdapter(mDataset,mContext);
        mRvMedia.setAdapter(mAdapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Parcelable listState = mRvMedia.getLayoutManager().onSaveInstanceState();
        // saving header data
        saveUserHeaderData(outState);
        // saving recyclerview position
        outState.putParcelable(SAVED_RECYCLER_VIEW_STATUS_ID, listState);
        // saving recyclerview items
        outState.putParcelableArrayList(SAVED_RECYCLER_VIEW_DATASET_ID,mDataset);
        super.onSaveInstanceState(outState);
    }

    public void saveUserHeaderData(Bundle outState){
        outState.putParcelable(SAVED_USER_DATA_ID,userData);
    }

    public void restorePreviousState(){
        // getting user header data
        userData = mSavedInstanceState.getParcelable(SAVED_USER_DATA_ID);
        // Restoring user header
        setUserHeader();
        // getting recyclerview position
        mListState = mSavedInstanceState.getParcelable(SAVED_RECYCLER_VIEW_STATUS_ID);
        // getting recyclerview items
        mDataset = mSavedInstanceState.getParcelableArrayList(SAVED_RECYCLER_VIEW_DATASET_ID);
        // Restoring adapter items
        mAdapter.setItems(mDataset);
        // Restoring recycler view position
        mRvMedia.getLayoutManager().onRestoreInstanceState(mListState);
    }

    public void getRemoteData(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(mContext);
        String token = sharedPref.getString(getString(R.string.token_id),"");
        getUserInfos(token);
    }
}
