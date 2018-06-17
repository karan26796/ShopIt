package com.google.codelabs.mdc.java.shrine.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.codelabs.mdc.java.shrine.ProductEntry;
import com.google.codelabs.mdc.java.shrine.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by karan on 4/19/2018.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<ProductEntry> mList;
    private onCartItemClickListener mClickListener;

    public CartAdapter(List<ProductEntry> mList, onCartItemClickListener mClickListener) {
        this.mList = mList;
        this.mClickListener = mClickListener;
    }

    public interface onCartItemClickListener {
        void onCartItemClicked(CartViewHolder viewHolder, int position, Bundle bundle);

        void onCartItemDeleteClicked(CartViewHolder viewHolder, int position);

        void onCartItemLongClicked(CartViewHolder viewHolder, int position);
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart,
                parent, false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CartViewHolder holder, int position) {
        holder.mCartTitle.setText(mList.get(position).getTitle());
        holder.mCartPrice.setText(mList.get(position).getPrice());
        holder.mCartProgress.setVisibility(View.VISIBLE);
        Picasso.get()
                .load(mList.get(position).getUrl())
                .transform(new RoundedCornersTransformation(25, 5))
                .into(holder.mCartImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.mCartProgress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        holder.mCartProgress.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
            , View.OnLongClickListener {
        public ImageView mCartImage;
        TextView mCartPrice;
        TextView mCartTitle;
        ProgressBar mCartProgress;
        ImageButton btnDelete, btnSave;

        public CartViewHolder(View itemView) {
            super(itemView);
            btnDelete = itemView.findViewById(R.id.image_button_delete);

            mCartImage = itemView.findViewById(R.id.image_cart);
            mCartTitle = itemView.findViewById(R.id.text_cart_title);
            mCartPrice = itemView.findViewById(R.id.text_cart_price);
            mCartProgress = itemView.findViewById(android.R.id.progress);

            itemView.setOnClickListener(this);
            btnDelete.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == itemView) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("cartItem", mList.get(getAdapterPosition()));
                mClickListener.onCartItemClicked(this, getAdapterPosition(), bundle);
            }
            if (v == btnDelete)
                mClickListener.onCartItemDeleteClicked(this, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            mClickListener.onCartItemLongClicked(this, getAdapterPosition());
            return false;
        }
    }
}
