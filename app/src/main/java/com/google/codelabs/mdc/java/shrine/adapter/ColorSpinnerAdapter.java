package com.google.codelabs.mdc.java.shrine.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.codelabs.mdc.java.shrine.R;

import java.util.List;

public class ColorSpinnerAdapter extends ArrayAdapter {
    private List<String> colors;
    private LayoutInflater inflter;

    public ColorSpinnerAdapter(@NonNull Context context, int resource, List<String> colors) {
        super(context, resource);
        this.colors = colors;
        inflter = (LayoutInflater.from(getContext()));
    }

    public View getCustomView(int position, View convertView,
                              ViewGroup parent) {

        convertView = inflter.inflate(R.layout.spinner_rows, parent, false);

        TextView tvLanguage = convertView
                .findViewById(R.id.text_color_spinner);

        tvLanguage.setText("france");
        tvLanguage.setTextColor(Color.rgb(75, 180, 225));

        ImageView img = convertView.findViewById(R.id.image_color_spinner);
        if (!colors.get(position).equals(""))
            img.setBackgroundColor(Color.parseColor(colors.get(position)));
        else
            img.setBackgroundColor(Color.parseColor(colors.get(1)));
        return convertView;
    }

    // It gets a View that displays in the drop down popup the data at the specified position
    @Override
    public View getDropDownView(int position, View convertView,
                                @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    // It gets a View that displays the data at the specified position
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public int getCount() {
        return colors.size();
    }
}

