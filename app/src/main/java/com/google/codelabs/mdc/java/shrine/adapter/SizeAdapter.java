package com.google.codelabs.mdc.java.shrine.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.codelabs.mdc.java.shrine.R;

import java.util.List;

import androidx.appcompat.widget.AppCompatImageView;

public class SizeAdapter extends ArrayAdapter<String> {
    private List<String> mColorsList;

    public SizeAdapter(@NonNull Context context, int resource, List<String> mColorsList) {
        super(context, resource);
        this.mColorsList = mColorsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(convertView.getContext())
                    .inflate(R.layout.item_color, parent, false);
        }
        AppCompatImageView imageView = view.findViewById(R.id.image_color);
        imageView.setBackgroundColor(Color.CYAN);

        TextView textView = view.findViewById(R.id.text_color);
        textView.setText(mColorsList.get(position));
        return view;
    }
}
