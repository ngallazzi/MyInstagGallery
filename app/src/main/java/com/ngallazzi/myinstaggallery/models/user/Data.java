package com.ngallazzi.myinstaggallery.models.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.ngallazzi.myinstaggallery.models.media.Image;
import com.ngallazzi.myinstaggallery.models.media.Images;

/**
 * Created by Nicola on 2017-04-21.
 */

public class Data implements Parcelable {
    @SerializedName("id")
    String id;
    @SerializedName("username")
    public String username;
    @SerializedName("profile_picture")
    public String profilePicture;
    @SerializedName("counts")
    public Counts counts;

    private Data(Parcel in) {
        id = in.readString();
        username = in.readString();
        profilePicture = in.readString();
        counts = in.readParcelable(Counts.class.getClassLoader());
    }

    public static final Parcelable.Creator<Data> CREATOR
            = new Parcelable.Creator<Data>() {
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        public Data[] newArray(int size) {
            return new Data[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(id);
        out.writeString(username);
        out.writeString(profilePicture);
        out.writeParcelable(counts,0);
    }
}
