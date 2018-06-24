package com.google.codelabs.mdc.java.shrine.model;

import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.codelabs.mdc.java.shrine.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ProductEntry extends Favorites implements Parcelable {
    private static final String TAG = ProductEntry.class.getSimpleName();
    private List<String> color = new ArrayList<>();
    private List<String> size = new ArrayList<>();
    public int qty;

    public ProductEntry() {
    }

    public List<String> getColor() {
        return color;
    }

    public void setColor(List<String> color) {
        this.color = color;
    }

    public List<String> getSize() {
        return size;
    }

    public void setSize(List<String> size) {
        this.size = size;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    protected ProductEntry(Parcel in) {
        title = in.readString();
        url = in.readString();
        price = in.readString();
        qty = in.readInt();
        color = in.createStringArrayList();
        size = in.createStringArrayList();
    }

    public static final Creator<ProductEntry> CREATOR = new Creator<ProductEntry>() {
        @Override
        public ProductEntry createFromParcel(Parcel in) {
            return new ProductEntry(in);
        }

        @Override
        public ProductEntry[] newArray(int size) {
            return new ProductEntry[size];
        }
    };

    /**
     * Loads a raw JSON at R.raw.products and converts it into a list of ProductEntry objects
     */
    public static List<ProductEntry> initProductEntryList(Resources resources) {
        InputStream inputStream = resources.openRawResource(R.raw.products);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            int pointer;
            while ((pointer = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, pointer);
            }
        } catch (IOException exception) {
            Log.e(TAG, "Error writing/reading from the JSON file.", exception);
        } finally {
            try {
                inputStream.close();
            } catch (IOException exception) {
                Log.e(TAG, "Error closing the input stream.", exception);
            }
        }
        String jsonProductsString = writer.toString();
        Gson gson = new Gson();
        Type productListType = new TypeToken<ArrayList<ProductEntry>>() {
        }.getType();
        return gson.fromJson(jsonProductsString, productListType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(url);
        parcel.writeString(price);
        parcel.writeInt(qty);
        parcel.writeStringList(color);
        parcel.writeStringList(size);
    }
}
