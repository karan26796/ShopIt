package com.google.codelabs.mdc.java.shrine.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.codelabs.mdc.java.shrine.ProductEntry;
import com.google.codelabs.mdc.java.shrine.R;
import com.google.codelabs.mdc.java.shrine.adapter.CartAdapter;
import com.google.codelabs.mdc.java.shrine.sql.ProductDBHelper;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class CartActivity extends AppCompatActivity implements CartAdapter.onCartItemClickListener,
        SwipeRefreshLayout.OnRefreshListener {
    RecyclerView recyclerView;
    ProductDBHelper productDBHelper;
    SwipeRefreshLayout refreshLayout;
    CartAdapter cartAdapter;
    List<ProductEntry> mList;
    AppCompatTextView textItems, textTotal;
    AppCompatImageView imageError;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        toolbar = findViewById(R.id.cartToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        imageError = findViewById(R.id.image_error);
        refreshLayout = findViewById(R.id.swipeCart);
        productDBHelper = new ProductDBHelper(this);

        textItems = findViewById(R.id.text_items);
        textTotal = findViewById(R.id.text_total);
        recyclerView = findViewById(R.id.recycler_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        setRecyclerView();

        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onCartItemClicked(CartAdapter.CartViewHolder viewHolder, int position, Bundle bundle) {
        startActivity(new Intent(this, DetailActivity.class)
                .putExtras(bundle));
    }

    @Override
    public void onCartItemDeleteClicked(CartAdapter.CartViewHolder viewHolder, int position) {
        boolean deleted = productDBHelper.deleteProduct(mList.get(position).getId());
        if (deleted) {
            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(this, "Deleted, Note", Toast.LENGTH_SHORT).show();
        setRecyclerView();
    }

    @Override
    public void onCartItemLongClicked(CartAdapter.CartViewHolder viewHolder, int position) {
    }

    @Override
    public void onRefresh() {
        setRecyclerView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setRecyclerView() {
        refreshLayout.setRefreshing(true);
        mList = productDBHelper.productList();
        if (mList.size() != 0) {
            imageError.setVisibility(View.GONE);
        } else {
            imageError.setVisibility(View.VISIBLE);
        }
        textItems.setText("No. of items: " + mList.size());
        int total = 0;
        for (int i = 0; i < mList.size(); i++) {
            total = total + Integer.parseInt(mList.get(i).getPrice().substring(1));
        }
        textTotal.setText("Cart Total: " + "$" + total);
        cartAdapter = new CartAdapter(mList, this);
        recyclerView.setAdapter(cartAdapter);
        refreshLayout.setRefreshing(false);
    }
}
