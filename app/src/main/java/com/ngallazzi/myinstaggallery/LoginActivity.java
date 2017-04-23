package com.ngallazzi.myinstaggallery;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import java.net.URI;
import java.net.URL;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.http.Url;

public class LoginActivity extends AppCompatActivity {
    private final String TAG = LoginActivity.class.getSimpleName();
    public final String BASE_AUTH_URL = "https://api.instagram.com/oauth/authorize/";
    public final String CLIENT_ID = "4d735c6d1a9f43209ba00913ed8fc45c";
    public final String REDIRECT_URI = "https://github.com/ngallazzi";
    public final String RESPONSE_TYPE = "token";
    private String authUrl = BASE_AUTH_URL + "?client_id="+CLIENT_ID+"&redirect_uri="+REDIRECT_URI+"&response_type="+RESPONSE_TYPE;
    @BindView(R.id.wvLogin)
    WebView wvLogin;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mContext = this;
    }

    @Override
    protected void onStart() {
        super.onStart();
        initWebView();
    }

    public void initWebView(){
        wvLogin.setWebViewClient(new MyWebViewClient());
        wvLogin.loadUrl(authUrl);
    }


    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains("access_token")) {
                String[] parsed = url.split("=");
                saveToken(parsed[1]);
                goToGalleryActivity();
                return true;
            }
            return false;
        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);
            Utils.clearApplicationData(mContext);
        }
    }

    private void saveToken(String token){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.token_id), token);
        editor.commit();
        Log.v(TAG,"Saved token: " + token);
    }

    private void goToGalleryActivity(){
        Intent intent = new Intent(this,GalleryActivity.class);
        startActivity(intent);
    }
}
