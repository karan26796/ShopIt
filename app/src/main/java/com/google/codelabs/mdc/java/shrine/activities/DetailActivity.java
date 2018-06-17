package com.google.codelabs.mdc.java.shrine.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.codelabs.mdc.java.shrine.ProductEntry;
import com.google.codelabs.mdc.java.shrine.R;
import com.google.codelabs.mdc.java.shrine.adapter.ImageSliderAdapter;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

public class DetailActivity extends AppCompatActivity {
    AppCompatImageView imageView;
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

        viewPagerAdapter = new ImageSliderAdapter(ImageSliderAdapter.initProductEntryList(getResources()));
        viewPager.setAdapter(viewPagerAdapter);
        //imageView = findViewById(R.id.image_detail);
        textView = findViewById(R.id.text_title_detail);

        chipGroup = new ChipGroup(this);
        for (int i = 0; i < 5; i++) {
            Chip chip = new Chip(this, null, R.style.TextChip);
            chip.setText("Chip" + i);
            chip.setWidth(200);
            chip.setTextAppearanceResource(R.style.TextChip);
            chipGroup.addView(chip);
        }
        ((ViewGroup) findViewById(R.id.chip_group)).addView(chipGroup);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        bundle = getIntent().getExtras();
        if (bundle != null)
            if (bundle.containsKey("item") && bundle.containsKey("position")) {
                ProductEntry productEntry = bundle.getParcelable("item");
                assert productEntry != null;
                textView.setText(productEntry.title);
                viewPager.setCurrentItem(bundle.getInt("position"));
            } else if (bundle.containsKey("cartItem")) {
                ProductEntry productEntry = bundle.getParcelable("cartItem");
                assert productEntry != null;
                textView.setText(productEntry.title);
            } else {
                imageView.setImageResource(R.drawable.shr_search);
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
}
