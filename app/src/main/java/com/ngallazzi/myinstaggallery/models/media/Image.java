package com.ngallazzi.myinstaggallery.models.media;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nicola on 2017-04-19.
 */

public class Image implements Parcelable {
    @SerializedName("width")
    public int width;
    @SerializedName("height")
    public int height;
    @SerializedName("url")
    public String url;

    private Image(Parcel in) {
        width = in.readInt();
        height = in.readInt();
        url = in.readString();
    }

    public static final Parcelable.Creator<Image> CREATOR
            = new Parcelable.Creator<Image>() {
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(width);
        out.writeInt(height);
        out.writeString(url);
    }
}
