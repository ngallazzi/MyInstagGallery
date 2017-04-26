package com.ngallazzi.myinstaggallery;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Nicola on 2017-04-23.
 */

public class MediaDialog extends DialogFragment {
    Context mContext;
    ImageView ivFullScreen;
    final static String IMAGE_URL_ID = "image_url";

    public MediaDialog() {

    }

    public static MediaDialog newInstance(String imageUrl) {
        MediaDialog dialog = new MediaDialog();
        Bundle args = new Bundle();
        args.putString(IMAGE_URL_ID, imageUrl);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fullscreen_image_layout, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivFullScreen = (ImageView) view.findViewById(R.id.ivFullScreen);
        String url = getArguments().getString(IMAGE_URL_ID);
        Picasso.with(mContext).load(url).into(ivFullScreen);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
