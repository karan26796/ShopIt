package com.google.codelabs.mdc.java.shrine.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.codelabs.mdc.java.shrine.R;
import com.google.codelabs.mdc.java.shrine.model.ProductEntry;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.RecyclerView;

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

        void onCartItemFavoriteClicked(CartViewHolder viewHolder, int position, ProductEntry productEntry);
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart,
                parent, false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CartViewHolder holder, int position) {

        ProductEntry productEntry = mList.get(position);
        holder.mCartTitle.setText(productEntry.getTitle());
        holder.mCartPrice.setText(productEntry.getPrice());
        holder.mCartProgress.setVisibility(View.VISIBLE);
        List<String> size = new ArrayList<>();

        size.add("14");
        size.add("16");
        size.add("18");
        size.add("20");

        List<String> colors = new ArrayList<>(productEntry
                .getColor());

        Toast.makeText(holder.itemView.getContext(), productEntry.getTitle(), Toast.LENGTH_SHORT).show();
        holder.spinnerColor.setAdapter(new ColorSpinnerAdapter(
                holder.itemView.getContext(), android.R.layout.simple_spinner_item, colors));

        //Toast.makeText(holder.itemView.getContext(), colors.size(), Toast.LENGTH_SHORT).show();

        ArrayAdapter<String>
                sizeAdapter = new ArrayAdapter<>
                (holder.itemView.getContext(), android.R.layout.simple_spinner_item, size);

        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spinnerQty.setAdapter(sizeAdapter);

        Picasso.get()
                .load(productEntry.getUrl())
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
        ImageButton btnDelete, btnFavorite;
        AppCompatSpinner spinnerQty, spinnerColor;

        public CartViewHolder(View itemView) {
            super(itemView);
            btnDelete = itemView.findViewById(R.id.image_button_delete);
            btnFavorite = itemView.findViewById(R.id.image_button_favorite);
            spinnerColor = itemView.findViewById(R.id.spinner_color);
            spinnerQty = itemView.findViewById(R.id.spinner_qty);

            mCartImage = itemView.findViewById(R.id.image_cart);
            mCartTitle = itemView.findViewById(R.id.text_cart_title);
            mCartPrice = itemView.findViewById(R.id.text_cart_price);
            mCartProgress = itemView.findViewById(android.R.id.progress);

            btnDelete.setOnClickListener(this);
            btnFavorite.setOnClickListener(this);
            itemView.setOnClickListener(this);
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
            if (v == btnFavorite) {
                ProductEntry productEntry = mList.get(getAdapterPosition());
                mClickListener.onCartItemFavoriteClicked(this, getAdapterPosition(), productEntry);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            try {
                Toast.makeText(v.getContext(), "Color: " + mList.get(getAdapterPosition()).getPrice(), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(v.getContext(), "Exception: " +
                        e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            return false;
        }

    }
}
