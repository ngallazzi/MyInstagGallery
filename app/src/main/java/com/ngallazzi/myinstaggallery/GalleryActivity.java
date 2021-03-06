package com.ngallazzi.myinstaggallery;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
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
    private final String DIALOG_MEDIA_ID = "dialog_media";

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
    MediaDialog dialog;

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
        mDataset.clear();
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
        mLayoutManager = new GridLayoutManager(this,COLUMNS_COUNT);
        mRvMedia.setLayoutManager(mLayoutManager);
        mDataset = new ArrayList<Images>();
        mAdapter = new MediaAdapter(mDataset, mContext, new MediaAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClicked(String imageUrl) {
                showImageDialog(imageUrl);
            }
        });
        mRvMedia.setAdapter(mAdapter);
    }

    private void showImageDialog(String imageUrl) {
        FragmentManager fm = getSupportFragmentManager();
        MediaDialog mediaDialog = MediaDialog.newInstance(imageUrl);
        mediaDialog.show(fm, DIALOG_MEDIA_ID);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout(){
        CookieSyncManager.createInstance(GalleryActivity.this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        finish();
    }
}
