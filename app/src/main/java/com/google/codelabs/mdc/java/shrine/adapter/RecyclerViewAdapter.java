package com.google.codelabs.mdc.java.shrine.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.codelabs.mdc.java.shrine.ProductEntry;
import com.google.codelabs.mdc.java.shrine.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>
        implements Filterable {

    private List<ProductEntry> mArrayList;
    private List<ProductEntry> mDisplayList;
    private onClickListener mClickListener;

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                return null;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            }
        };
    }

    public interface onClickListener {
        void onItemClicked(int position, RecyclerViewAdapter.RecyclerViewHolder viewHolder, Bundle bundle);

        void onOptionsClicked(int position, View view, ProductEntry productEntry);
    }

    public RecyclerViewAdapter(List<ProductEntry> mArrayList, onClickListener mClickListener) {
        this.mArrayList = mArrayList;
        this.mClickListener = mClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        return position % 3;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = R.layout.shr_staggered_product_card_first;
        if (viewType == 1) {
            layoutId = R.layout.shr_staggered_product_card_second;
        } else if (viewType == 2) {
            layoutId = R.layout.shr_staggered_product_card_third;
        }

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(layoutId, parent, false);
        return new RecyclerViewAdapter.RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        if (mArrayList != null && position < mArrayList.size()) {
            ProductEntry product = mArrayList.get(position);
            holder.textTitle.setText(product.title);
            holder.textPrice.setText(product.price);
            //holder.progressBar.setVisibility(View.VISIBLE);

            Picasso.get()
                    .load(product.url)
                    .into(holder.imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            //holder.progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Exception e) {
                            //holder.progressBar.setVisibility(View.GONE);
                            holder.imageView.setImageResource(R.drawable.shr_search);
                        }
                    });
        }

    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public AppCompatImageView imageView;
        public TextView textPrice, textTitle;
        ImageButton imageButton;
        //ProgressBar progressBar;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            imageButton = itemView.findViewById(R.id.image_button);
            //progressBar = itemView.findViewById(android.R.id.progress);
            textTitle = itemView.findViewById(R.id.text_title);
            textPrice = itemView.findViewById(R.id.text_price);
            imageView = itemView.findViewById(R.id.image_recycler);

            imageButton.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view == imageButton) {
                ProductEntry product = mArrayList.get(getAdapterPosition());
                mClickListener.onOptionsClicked(getAdapterPosition(), view, product);
            } else if (view == itemView) {
                Bundle bundle = new Bundle();
                ProductEntry product = mArrayList.get(getAdapterPosition());
                bundle.putParcelable("item", product);
                bundle.putInt("position", getAdapterPosition());
                mClickListener.onItemClicked(getAdapterPosition(), this, bundle);
            }
        }
    }
}
