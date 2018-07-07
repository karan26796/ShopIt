package com.google.codelabs.mdc.java.shrine.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.codelabs.mdc.java.shrine.R;
import com.google.codelabs.mdc.java.shrine.adapter.ImageIndividualSliderAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator;
import androidx.viewpager.widget.ViewPager;

public class ImageViewActivity extends AppCompatActivity implements ImageIndividualSliderAdapter.onIndividualImageClick
        , ViewPager.OnPageChangeListener {
    Toolbar mToolbar;
    ViewPager viewPager;
    ImageIndividualSliderAdapter sliderAdapter;
    boolean actionBar = true;
    LinearLayout dotsLinearLayout;
    TextView dots[];
    int mToolbarHeight, mAnimDuration = 200/* milliseconds */;
    ValueAnimator mVaActionBar;

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
            hideActionBar();
            dotsLinearLayout.setVisibility(View.INVISIBLE);
            actionBar = false;
        } else {
            showActionBar();
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

    //@SuppressLint("NewApi")
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
            dots[currentPage].setTextColor(getResources()
                    .getColor(R.color.textColorPrimary));

    }

    void hideActionBar() {
        // initialize `mToolbarHeight`
        if (mToolbarHeight == 0) {
            mToolbarHeight = mToolbar.getHeight();
        }

        if (mVaActionBar != null && mVaActionBar.isRunning()) {
            // we are already animating a transition - block here
            return;
        }

        // animate `Toolbar's` height to zero.
        mVaActionBar = ValueAnimator.ofInt(mToolbarHeight, 0);
        mVaActionBar.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // update LayoutParams
                ((AppBarLayout.LayoutParams) mToolbar.getLayoutParams()).height
                        = (Integer) animation.getAnimatedValue();
                mToolbar.requestLayout();
            }
        });

        mVaActionBar.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                if (getSupportActionBar() != null) { // sanity check
                    getSupportActionBar().hide();
                }
            }
        });

        mVaActionBar.setDuration(mAnimDuration);
        mVaActionBar.start();
    }

    void showActionBar() {
        if (mVaActionBar != null && mVaActionBar.isRunning()) {
            // we are already animating a transition - block here
            return;
        }

        // restore `Toolbar's` height
        mVaActionBar = ValueAnimator.ofInt(0, mToolbarHeight);
        mVaActionBar.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // update LayoutParams
                ((AppBarLayout.LayoutParams) mToolbar.getLayoutParams()).height
                        = (Integer) animation.getAnimatedValue();
                mToolbar.requestLayout();
            }
        });

        mVaActionBar.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);

                if (getSupportActionBar() != null) { // sanity check
                    getSupportActionBar().show();
                }
            }
        });

        mVaActionBar.setDuration(mAnimDuration);
        mVaActionBar.start();
    }
}


