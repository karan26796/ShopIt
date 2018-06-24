package com.google.codelabs.mdc.java.shrine.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.codelabs.mdc.java.shrine.R;
import com.google.codelabs.mdc.java.shrine.model.Options;

import java.util.ArrayList;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

public class OptionsAdapter extends RecyclerView.Adapter<OptionsAdapter.OptionsViewHolder> {
    private ArrayList<Options> mList;
    private optionClickListener mListener;

    public OptionsAdapter(ArrayList<Options> mList, optionClickListener mListener) {
        this.mList = mList;
        this.mListener = mListener;
    }

    public interface optionClickListener {
        void onOptionClicked(View view, int position);
    }

    @Override
    public OptionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_options, parent, false);

        return new OptionsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OptionsViewHolder holder, int position) {
        holder.optionsTitle.setText(mList.get(position).getTitle());
        holder.optionsImageView.setImageResource(mList.get(position).getDrawableIcon());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class OptionsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        AppCompatImageView optionsImageView;
        TextView optionsTitle;

        public OptionsViewHolder(View itemView) {
            super(itemView);
            optionsImageView = itemView.findViewById(R.id.image_options);
            optionsTitle = itemView.findViewById(R.id.text_options);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onOptionClicked(view, getAdapterPosition());
        }
    }
}
