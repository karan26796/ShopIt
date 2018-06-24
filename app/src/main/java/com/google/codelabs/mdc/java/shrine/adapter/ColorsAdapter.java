package com.google.codelabs.mdc.java.shrine.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.codelabs.mdc.java.shrine.R;
import com.google.codelabs.mdc.java.shrine.model.ProductColors;

import java.util.ArrayList;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

public class ColorsAdapter extends RecyclerView.Adapter<ColorsAdapter.ColorsViewHolder> {
    ArrayList<ProductColors> mList;

    @Override
    public ColorsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_color, parent, false);
        return new ColorsViewHolder(view);
    }

    public ColorsAdapter(ArrayList<ProductColors> mList) {
        this.mList = mList;
    }

    @Override
    public void onBindViewHolder(ColorsViewHolder holder, int position) {
        holder.color.setBackgroundColor(mList.get(position).getColor());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ColorsViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView color;

        public ColorsViewHolder(View itemView) {
            super(itemView);
            color = itemView.findViewById(R.id.image_color);
        }
    }
}
