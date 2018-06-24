package com.google.codelabs.mdc.java.shrine.activities;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.google.codelabs.mdc.java.shrine.OnFragmentRefreshListener;
import com.google.codelabs.mdc.java.shrine.R;
import com.google.codelabs.mdc.java.shrine.adapter.ViewPagerAdapter;
import com.google.codelabs.mdc.java.shrine.fragment.cart.CartFragment;
import com.google.codelabs.mdc.java.shrine.fragment.cart.FavoritesFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

public class CartActivity extends AppCompatActivity {
    //SwipeRefreshLayout refreshLayout;
    TabLayout cartTabs;
    ViewPager cartViewPager;
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

        //refreshLayout = findViewById(R.id.swipeCart);
        cartTabs = findViewById(R.id.cart_tab_layout);
        cartViewPager = findViewById(R.id.cart_viewpager);
        setCartViewPager(cartViewPager);
        cartTabs.setupWithViewPager(cartViewPager);
        //refreshLayout.setOnRefreshListener(this);
    }

    /*@Override
    public void onRefresh() {
        //refreshLayout.setRefreshing(true);
        //mListener.onFragmentRefreshed();
        //refreshLayout.setRefreshing(false);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setCartViewPager(ViewPager cartViewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CartFragment(), "Cart");
        adapter.addFragment(new FavoritesFragment(), "Favorites");
        cartViewPager.setAdapter(adapter);
    }
}
