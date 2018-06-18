package com.google.codelabs.mdc.java.shrine.activities;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.codelabs.mdc.java.shrine.R;
import com.google.codelabs.mdc.java.shrine.adapter.ImageIndividualSliderAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

public class ImageViewActivity extends AppCompatActivity implements ImageIndividualSliderAdapter.onIndividualImageClick
        , ViewPager.OnPageChangeListener {
    Toolbar mToolbar;
    ViewPager viewPager;
    ImageIndividualSliderAdapter sliderAdapter;
    boolean actionBar = true;
    LinearLayout dotsLinearLayout;
    TextView dots[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        mToolbar = findViewById(R.id.imageViewToolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        viewPager = findViewById(R.id.individualViewPager);
        sliderAdapter = new ImageIndividualSliderAdapter(ImageIndividualSliderAdapter.initProductEntryList(getResources())
                .subList(0, 5), this);
        dotsLinearLayout = findViewById(R.id.dotsLayout);
        viewPager.setAdapter(sliderAdapter);
        addBottomDots(0);
        viewPager.setOnPageChangeListener(this);

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
    public void onImageClicked(View view) {
        if (actionBar) {
            getSupportActionBar().hide();
            dotsLinearLayout.setVisibility(View.INVISIBLE);
            actionBar = false;
        } else {
            getSupportActionBar().show();
            dotsLinearLayout.setVisibility(View.VISIBLE);
            actionBar = true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        addBottomDots(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @SuppressLint("NewApi")
    private void addBottomDots(int currentPage) {
        dots = new TextView[5];
        dotsLinearLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setGravity(Gravity.CENTER_VERTICAL);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(30);
            dots[i].setTextColor(Color.parseColor("#d3d3d3"));
            dotsLinearLayout.addView(dots[i]);
        }
        if (dots.length > 0)
            dots[currentPage].setTextColor(getColor(R.color.textColorPrimary));

    }
}


