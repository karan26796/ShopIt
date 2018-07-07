package com.google.codelabs.mdc.java.shrine.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SpinnerColor implements Parcelable {
    private int color;
    private String colorName;

    public SpinnerColor(int color, String colorName) {
        this.color = color;
        this.colorName = colorName;
    }

    protected SpinnerColor(Parcel in) {
        color = in.readInt();
        colorName = in.readString();
    }

    public static final Creator<SpinnerColor> CREATOR = new Creator<SpinnerColor>() {
        @Override
        public SpinnerColor createFromParcel(Parcel in) {
            return new SpinnerColor(in);
        }

        @Override
        public SpinnerColor[] newArray(int size) {
            return new SpinnerColor[size];
        }
    };

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(color);
        parcel.writeString(colorName);
    }
}
