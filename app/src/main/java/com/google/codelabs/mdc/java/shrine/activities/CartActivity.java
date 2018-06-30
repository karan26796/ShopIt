package com.google.codelabs.mdc.java.shrine.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.codelabs.mdc.java.shrine.OnFragmentRefreshListener;
import com.google.codelabs.mdc.java.shrine.R;
import com.google.codelabs.mdc.java.shrine.adapter.ViewPagerAdapter;
import com.google.codelabs.mdc.java.shrine.fragment.cart.CartFragment;
import com.google.codelabs.mdc.java.shrine.fragment.cart.FavoritesFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class CartActivity extends AppCompatActivity implements OnFragmentRefreshListener {
    //SwipeRefreshLayout refreshLayout;
    TabLayout cartTabs;
    ViewPager cartViewPager;
    Toolbar toolbar;
    ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        toolbar = findViewById(R.id.cartToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        //refreshLayout = findViewById(R.id.swipeCart);
        cartTabs = findViewById(R.id.cart_tab_layout);
        cartViewPager = findViewById(R.id.cart_viewpager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        setCartViewPager(cartViewPager, adapter);
        cartTabs.setupWithViewPager(cartViewPager);
        //refreshLayout.setOnRefreshListener(this);
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

    public void setCartViewPager(ViewPager cartViewPager, ViewPagerAdapter adapter) {
        adapter.addFragment(new CartFragment(), "Cart");
        adapter.addFragment(new FavoritesFragment(), "Favorites");
        cartViewPager.setAdapter(adapter);
    }

    @Override
    public void onFragmentRefreshed(int fragment) {
        Fragment page;
        try {
            if (fragment == 1) {
                page = adapter.getItem(1);
                FavoritesFragment favoritesFragment = (FavoritesFragment) page;
                favoritesFragment.setRecyclerView();
            } else if (fragment == 0){
                page = adapter.getItem(0);
                CartFragment cartFragment = (CartFragment) page;
                cartFragment.setRecyclerView();
            }

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }
}
