package com.google.codelabs.mdc.java.shrine.fragment.cart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.codelabs.mdc.java.shrine.R;
import com.google.codelabs.mdc.java.shrine.activities.DetailActivity;
import com.google.codelabs.mdc.java.shrine.adapter.CartAdapter;
import com.google.codelabs.mdc.java.shrine.model.ProductEntry;
import com.google.codelabs.mdc.java.shrine.utils.Divider;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class CartFragment extends CartBaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private CartAdapter cartAdapter;
    private AppCompatTextView textItems, textTotal;
    private AppCompatImageView imageError;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        refreshLayout = view.findViewById(R.id.swipeCart);
        imageError = view.findViewById(R.id.image_error);
        textItems = view.findViewById(R.id.text_items);
        textTotal = view.findViewById(R.id.text_total);
        recyclerView = view.findViewById(R.id.recycler_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new Divider(ContextCompat.getDrawable(getContext(), R.drawable.dividr)));
        setRecyclerView();
        refreshLayout.setOnRefreshListener(this);
        return view;
    }

    @SuppressLint("StaticFieldLeak")
    public void setRecyclerView() {
        final int[] total = {0};
        new AsyncTask<Void, Void, String>() {

            // Runs on Worker thread
            @Override
            protected String doInBackground(Void... params) {
                mCartList = productDBHelper.productList();
                for (int i = 0; i < mCartList.size(); i++) {
                    total[0] = total[0] + Integer.parseInt(mCartList.get(i).getPrice().substring(1));
                }
                return "";
            }

            // Runs on Ui thread
            @Override
            protected void onPostExecute(String data) {
                cartAdapter = new CartAdapter(mCartList, cartItemClickListener);

                recyclerView.setAdapter(cartAdapter);
                textItems.setText("No. of items: " + mCartList.size());
                textTotal.setText("Cart Total: " + "$" + total[0]);
                if (mCartList.size() != 0) {
                    imageError.setVisibility(View.GONE);
                } else {
                    imageError.setVisibility(View.VISIBLE);
                }
            }
        }.execute();
    }

    @Override

    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        setRecyclerView();
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onListUpdate() {

    }

    private CartAdapter.onCartItemClickListener cartItemClickListener = new CartAdapter.onCartItemClickListener() {
        @Override
        public void onCartItemClicked(CartAdapter.CartViewHolder viewHolder, int position, Bundle bundle) {
            startActivity(new Intent(getContext(), DetailActivity.class)
                    .putExtras(bundle));
        }

        @Override
        public void onCartItemDeleteClicked(CartAdapter.CartViewHolder viewHolder, int position) {
            boolean deleted = productDBHelper.deleteProduct(mCartList.get(position).getId());
            if (deleted) {
                Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                setRecyclerView();
            } else
                Toast.makeText(getContext(), "Deleted, Note", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCartItemFavoriteClicked(CartAdapter.CartViewHolder viewHolder, int position, ProductEntry productEntry) {
            productDBHelper.deleteProduct(mCartList.get(position).getId());
            favoritesDBHelper.newFavorite(mCartList.get(position));
            setRecyclerView();
            onFragmentRefreshListener.onFragmentRefreshed(1);
        }
    };
}
