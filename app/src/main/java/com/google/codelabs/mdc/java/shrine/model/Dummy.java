package com.google.codelabs.mdc.java.shrine.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Dummy implements Parcelable {
    private static final String TAG = Dummy.class.getSimpleName();

    public String title;
    public String url;
    public String price;
    public long id;
    List<String> color,size;
    int qty;

    protected Dummy(Parcel in) {
        title = in.readString();
        url = in.readString();
        price = in.readString();
        id = in.readLong();
        color = in.createStringArrayList();
        size = in.createStringArrayList();
        qty = in.readInt();
    }

    public static final Creator<Dummy> CREATOR = new Creator<Dummy>() {
        @Override
        public Dummy createFromParcel(Parcel in) {
            return new Dummy(in);
        }

        @Override
        public Dummy[] newArray(int size) {
            return new Dummy[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(url);
        parcel.writeString(price);
        parcel.writeLong(id);
        parcel.writeStringList(color);
        parcel.writeStringList(size);
        parcel.writeInt(qty);
    }
}
