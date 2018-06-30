package com.google.codelabs.mdc.java.shrine.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.chip.ChipGroup;
import com.google.codelabs.mdc.java.shrine.R;
import com.google.codelabs.mdc.java.shrine.adapter.ColorsAdapter;
import com.google.codelabs.mdc.java.shrine.adapter.ImageSliderAdapter;
import com.google.codelabs.mdc.java.shrine.model.Favorites;
import com.google.codelabs.mdc.java.shrine.model.ProductColors;
import com.google.codelabs.mdc.java.shrine.model.ProductEntry;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

public class DetailActivity extends AppCompatActivity implements ImageSliderAdapter.onImageClick {
    AppCompatImageView imageView;
    ArrayList<ProductColors> mList;
    RecyclerView recyclerColor;
    Bundle bundle;
    Toolbar toolbar;
    ChipGroup chipGroup;
    TextView textView;
    ViewPager viewPager;
    ImageSliderAdapter viewPagerAdapter;
    ArrayList<String> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        toolbar = findViewById(R.id.toolbar_detail);
        viewPager = findViewById(R.id.viewPagerSlider);

        viewPagerAdapter = new ImageSliderAdapter(ImageSliderAdapter.initProductEntryList(getResources()), this);
        viewPager.setAdapter(viewPagerAdapter);
        //imageView = findViewById(R.id.image_detail);
        recyclerColor = findViewById(R.id.recycler_colors);
        setRecyclerColor();
        textView = findViewById(R.id.text_title_detail);

        /*chipGroup = new ChipGroup(this);
        for (int i = 0; i < 5; i++) {
            Chip chip = new Chip(this, null, R.style.TextChip);
            chip.setText("Chip" + i);
            chip.setWidth(200);
            chip.setTextAppearanceResource(R.style.TextChip);
            chipGroup.addView(chip);
        }
        ((ViewGroup) findViewById(R.id.chip_group)).addView(chipGroup);*/
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        bundle = getIntent().getExtras();
        if (bundle != null)
            if (bundle.containsKey("item") && bundle.containsKey("position")) {
                ProductEntry productEntry = bundle.getParcelable("item");
                assert productEntry != null;
                textView.setText(productEntry.getTitle());
                viewPager.setCurrentItem(bundle.getInt("position"));
            } else if (bundle.containsKey("cartItem")) {
                ProductEntry productEntry = bundle.getParcelable("cartItem");
                assert productEntry != null;
                textView.setText(productEntry.getTitle());
            } else if (bundle.containsKey("favoriteItem")) {
                Favorites favorites = bundle.getParcelable("favoriteItem");
                assert favorites != null;
                textView.setText(favorites.getTitle());
            } else {
                //imageView.setImageResource(R.drawable.shr_search);
                textView.setText("Not Available");
            }
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

    @Override
    public void onImageClick(View view) {
        startActivity(new Intent(this,
                ImageViewActivity.class));
    }

    public void setRecyclerColor() {
        recyclerColor.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mList = new ArrayList<>();

        int[] colors = new int[8];
        colors[0] = android.graphics.Color.parseColor("#ef5350");
        colors[1] = android.graphics.Color.parseColor("#ab47bc");
        colors[2] = android.graphics.Color.parseColor("#66bb6a");
        colors[3] = android.graphics.Color.parseColor("#42a5f5");
        colors[4] = android.graphics.Color.parseColor("#ec407a");
        colors[5] = android.graphics.Color.parseColor("#ffa726");
        colors[6] = android.graphics.Color.parseColor("#26a69a");
        colors[7] = android.graphics.Color.parseColor("#bdbdbd");

        for (int i = 0; i < 8; i++) {
            mList.add(new ProductColors(colors[i], false));
        }
        recyclerColor.setAdapter(new ColorsAdapter(mList));
    }
}
