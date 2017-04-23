package com.ngallazzi.myinstaggallery;

/**
 * Created by Nicola on 2017-04-21.
 */

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ngallazzi.myinstaggallery.custom.DynamicHeightImageView;
import com.ngallazzi.myinstaggallery.models.media.Image;
import com.ngallazzi.myinstaggallery.models.media.Images;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Nicola on 2017-01-27.
 */

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.ViewHolder> {
    private final String TAG = MediaAdapter.class.getSimpleName();
    private ArrayList<Images> mImages;
    private Context mContext;
    private MediaDialog dialog;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public DynamicHeightImageView ivMedia;
        public ViewHolder(View v) {
            super(v);
            ivMedia = (DynamicHeightImageView) v.findViewById(R.id.ivMedia);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MediaAdapter(ArrayList<Images> myDataset, Context context) {
        mImages = myDataset;
        mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MediaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        // create a new view
        View layout = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image, parent, false);
        final ViewHolder vh = new ViewHolder(layout);

        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Images curImages = mImages.get(vh.getAdapterPosition());
                dialog = new MediaDialog(mContext,curImages.standardResolution.url);
                dialog.show();
            }
        });
        return vh;
    }



    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Images image = mImages.get(position);
        Picasso.with(mContext).load(image.standardResolution.url).into(holder.ivMedia);
    }


    public void setItems(ArrayList<Images> items){
        mImages = items;
    }

    @Override
    public int getItemCount() {
        if (mImages!=null){
            return mImages.size();
        }else{
            return 0;
        }
    }
}

