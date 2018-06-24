package com.google.codelabs.mdc.java.shrine.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.codelabs.mdc.java.shrine.R;
import com.google.codelabs.mdc.java.shrine.model.Favorites;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;


/**
 * Created by karan on 4/19/2018.
 */

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {
    private List<Favorites> mList;
    private onFavoritesItemClickListener mClickListener;

    public FavoritesAdapter(List<Favorites> mList, onFavoritesItemClickListener mClickListener) {
        this.mList = mList;
        this.mClickListener = mClickListener;
    }

    public interface onFavoritesItemClickListener {
        void onFavoriteItemClicked(FavoritesViewHolder viewHolder, int position, Bundle bundle);

        void onFavoriteItemDeleteClicked(FavoritesViewHolder viewHolder, int position);

        void onFavoriteItemLongClicked(FavoritesViewHolder viewHolder, int position);
    }

    @Override
    public FavoritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite,
                parent, false);
        return new FavoritesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FavoritesViewHolder holder, int position) {
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

    public class FavoritesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
            , View.OnLongClickListener {
        public ImageView mCartImage;
        TextView mCartPrice;
        TextView mCartTitle;
        ProgressBar mCartProgress;
        ImageButton btnDelete;

        public FavoritesViewHolder(View itemView) {
            super(itemView);
            btnDelete = itemView.findViewById(R.id.image_button_delete);

            mCartImage = itemView.findViewById(R.id.image_favorite);
            mCartTitle = itemView.findViewById(R.id.text_favorite_title);
            mCartPrice = itemView.findViewById(R.id.text_favorite_price);
            mCartProgress = itemView.findViewById(android.R.id.progress);

            itemView.setOnClickListener(this);
            btnDelete.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == itemView) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("favoriteItem", mList.get(getAdapterPosition()));
                mClickListener.onFavoriteItemClicked(this, getAdapterPosition(), bundle);
            }
            if (v == btnDelete)
                mClickListener.onFavoriteItemDeleteClicked(this, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            mClickListener.onFavoriteItemLongClicked(this, getAdapterPosition());
            return false;
        }

    }
}
