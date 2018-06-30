package com.google.codelabs.mdc.java.shrine.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.codelabs.mdc.java.shrine.R;
import com.google.codelabs.mdc.java.shrine.model.ProductColors;
import com.google.codelabs.mdc.java.shrine.utils.TextDrawable;

import java.util.ArrayList;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

public class ColorsAdapter extends RecyclerView.Adapter<ColorsAdapter.ColorsViewHolder> {
    private ArrayList<ProductColors> mList;
    private boolean isSelected = false;

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
        TextDrawable textDrawable = TextDrawable.builder()
                .buildRound("", mList.get(position).getColor());
        holder.color.setImageDrawable(textDrawable);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ColorsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        AppCompatImageView selectedColor, color;

        public ColorsViewHolder(View itemView) {
            super(itemView);
            color = itemView.findViewById(R.id.image_color);
            selectedColor = itemView.findViewById(R.id.image_selection);
            color.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (!mList.get(getAdapterPosition()).isSelected()
                    && !isSelected) {
                selectedColor.setVisibility(View.VISIBLE);
                mList.get(getAdapterPosition()).setSelected(true);
                isSelected = true;
            } else if (mList.get(getAdapterPosition()).isSelected()
                    && isSelected) {
                selectedColor.setVisibility(View.GONE);
                mList.get(getAdapterPosition()).setSelected(false);
                isSelected = false;
            }
        }
    }
}
