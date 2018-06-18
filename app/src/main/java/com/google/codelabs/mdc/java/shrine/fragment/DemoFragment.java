package com.google.codelabs.mdc.java.shrine.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.codelabs.mdc.java.shrine.BottomSheetDialog;
import com.google.codelabs.mdc.java.shrine.model.ProductEntry;
import com.google.codelabs.mdc.java.shrine.R;
import com.google.codelabs.mdc.java.shrine.activities.DetailActivity;
import com.google.codelabs.mdc.java.shrine.activities.DummyActivity;
import com.google.codelabs.mdc.java.shrine.adapter.RecyclerViewAdapter;
import com.google.codelabs.mdc.java.shrine.sql.ProductDBHelper;
import com.google.codelabs.mdc.java.shrine.utils.NavigationIconClickListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DemoFragment extends Fragment
        implements RecyclerViewAdapter.onClickListener, BottomSheetDialog.BottomSheetListener, View.OnClickListener {

    MaterialButton myAccBtn;
    private RecyclerView recyclerView;
    private ProductDBHelper productDBHelper;

    public static DemoFragment newInstance() {
        Bundle args = new Bundle();

        DemoFragment fragment = new DemoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setUpToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.app_bar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
        }
        toolbar.setNavigationOnClickListener(new NavigationIconClickListener(
                getContext(),
                view.findViewById(R.id.product_grid),
                new AccelerateDecelerateInterpolator(),
                getContext().getResources().getDrawable(R.drawable.shr_branded_menu), // Menu open icon
                getContext().getResources().getDrawable(R.drawable.shr_close_menu))); // Menu close icon

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        setUpToolbar(view);
        myAccBtn = view.findViewById(R.id.button_my_account);
        productDBHelper = new ProductDBHelper(getContext());
        recyclerView = view.findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.HORIZONTAL, false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position % 3 == 2 ? 2 : 1;
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new RecyclerViewAdapter(ProductEntry.initProductEntryList(getResources()), this));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.findViewById(R.id.product_grid).setBackground(getContext().getDrawable(R.drawable.shr_product_grid_background_shape));
        }
        myAccBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onItemClicked(int position, RecyclerViewAdapter.RecyclerViewHolder viewHolder, Bundle bundle) {
        Pair<View, String> p1 = Pair.create(viewHolder.itemView.findViewById(R.id.image_recycler), "img");
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), p1);
        startActivity(new Intent(getContext(), DetailActivity.class)
                .putExtras(bundle), optionsCompat.toBundle());
    }

    @Override
    public void onItemLongClicked(int position, RecyclerViewAdapter.RecyclerViewHolder viewHolder, Bundle bundle) {
        BottomSheetDialogFragment fragment = new BottomSheetDialog();
        fragment.show(getActivity().getSupportFragmentManager(), "ex");
    }

    @Override
    public void onOptionsClicked(int position, View view, ProductEntry productEntry) {
        boolean added = productDBHelper.newProduct(productEntry);
        if (added) {
            Toast.makeText(getContext(), "Added", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(getContext(), "Added, Not", Toast.LENGTH_SHORT).show();
        /*BottomSheetDialogFragment fragment = new BottomSheetDialog();
        fragment.show(getActivity().getSupportFragmentManager(), "ex");*/
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.shr_toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override
    public void onButtonClicked(String text) {
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(getContext(), DummyActivity.class));
    }
}
