package com.google.codelabs.mdc.java.shrine.adapter;

import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.codelabs.mdc.java.shrine.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

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

import androidx.appcompat.widget.AppCompatImageView;
import androidx.viewpager.widget.PagerAdapter;

public class ImageIndividualSliderAdapter extends PagerAdapter implements View.OnClickListener {

    private AppCompatImageView imageView;
    private List<UrlModel> mImagesList;
    private onIndividualImageClick mListener;

    public ImageIndividualSliderAdapter(List<UrlModel> mImagesList, onIndividualImageClick mListener) {
        this.mListener = mListener;
        this.mImagesList = mImagesList;
    }

    public interface onIndividualImageClick {
        void onImageClicked(View view);
    }

    @Override
    public int getCount() {
        return mImagesList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = LayoutInflater.from(container.getContext())
                .inflate(R.layout.item_individual_image_slider, container, false);
        imageView = itemView.findViewById(R.id.image_individual_slider);
        if (mImagesList != null && position < mImagesList.size()) {
            Picasso.get()
                    .load(mImagesList.get(position).url)
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            imageView.setImageResource(R.drawable.shr_search);
                        }
                    });
        }
        container.addView(itemView);
        imageView.setOnClickListener(this);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((AppCompatImageView) object);
    }

    public static List<UrlModel> initProductEntryList(Resources resources) {
        InputStream inputStream = resources.openRawResource(R.raw.images);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            int pointer;
            while ((pointer = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, pointer);
            }
        } catch (IOException exception) {
            Log.e("", "Error writing/reading from the JSON file.", exception);
        } finally {
            try {
                inputStream.close();
            } catch (IOException exception) {
                Log.e("", "Error closing the input stream.", exception);
            }
        }
        String jsonProductsString = writer.toString();
        Gson gson = new Gson();
        Type productListType = new TypeToken<ArrayList<UrlModel>>() {
        }.getType();
        return gson.fromJson(jsonProductsString, productListType);
    }

    @Override
    public void onClick(View view) {
        mListener.onImageClicked(view);
    }

    public class UrlModel {
        public String url;

        public UrlModel(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
