package com.ngallazzi.myinstaggallery.models.media;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nicola on 2017-04-20.
 */

public class Images implements Parcelable {
    @SerializedName("thumbnail")
    public Image thumbnail;
    @SerializedName("low_resolution")
    public Image lowResolution;
    @SerializedName("standard_resolution")
    public Image standardResolution;

    private Images(Parcel in) {
        thumbnail = in.readParcelable(Image.class.getClassLoader());
        lowResolution = in.readParcelable(Image.class.getClassLoader());
        standardResolution = in.readParcelable(Image.class.getClassLoader());
    }

    public static final Parcelable.Creator<Images> CREATOR
            = new Parcelable.Creator<Images>() {
        public Images createFromParcel(Parcel in) {
            return new Images(in);
        }

        public Images[] newArray(int size) {
            return new Images[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeParcelable(thumbnail,0);
        out.writeParcelable(lowResolution,0);
        out.writeParcelable(standardResolution,0);
    }
}
