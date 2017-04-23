package com.ngallazzi.myinstaggallery;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Nicola on 2017-04-23.
 */

public class MediaDialog extends Dialog {
    Context mContext;
    ImageView ivFullScreen;
    public MediaDialog(@NonNull Context context) {
        super(context);
        mContext = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fullscreen_image_layout);
        ivFullScreen = (ImageView) findViewById(R.id.ivFullScreen);
    }

    public void setImageUrl(String url){
        Picasso.with(mContext).load(url).into(ivFullScreen);
    }
}
