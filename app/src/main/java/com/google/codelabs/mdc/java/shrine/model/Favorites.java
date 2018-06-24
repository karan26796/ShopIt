package com.google.codelabs.mdc.java.shrine.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Favorites implements Parcelable {
    private static final String TAG = Favorites.class.getSimpleName();

    public String title;
    public String url;
    public String price;
    public long id;

    public Favorites() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Favorites(
            String title, String url, String price) {
        this.title = title;
        this.url = url;
        this.price = price;
    }

    protected Favorites(Parcel in) {
        title = in.readString();
        url = in.readString();
        price = in.readString();
    }

    public static final Creator<Favorites> CREATOR = new Creator<Favorites>() {
        @Override
        public Favorites createFromParcel(Parcel in) {
            return new Favorites(in);
        }

        @Override
        public Favorites[] newArray(int size) {
            return new Favorites[size];
        }
    };

    /**
     * Loads a raw JSON at R.raw.products and converts it into a list of ProductEntry objects
     */

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(url);
        parcel.writeString(price);
    }
}
