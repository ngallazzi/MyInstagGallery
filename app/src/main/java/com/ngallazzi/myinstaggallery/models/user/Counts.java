package com.ngallazzi.myinstaggallery.models.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nicola on 2017-04-21.
 */

public class Counts implements Parcelable {
    @SerializedName("media")
    public int media;
    @SerializedName("follows")
    public int follows;
    @SerializedName("followed_by")
    public int followedBy;

    private Counts(Parcel in) {
        media = in.readInt();
        follows = in.readInt();
        followedBy = in.readInt();
    }

    public static final Parcelable.Creator<Counts> CREATOR
            = new Parcelable.Creator<Counts>() {
        public Counts createFromParcel(Parcel in) {
            return new Counts(in);
        }

        public Counts[] newArray(int size) {
            return new Counts[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(media);
        out.writeInt(follows);
        out.writeInt(followedBy);
    }
}
