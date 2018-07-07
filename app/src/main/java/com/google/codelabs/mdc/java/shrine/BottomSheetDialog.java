package com.google.codelabs.mdc.java.shrine;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.codelabs.mdc.java.shrine.adapter.OptionsAdapter;
import com.google.codelabs.mdc.java.shrine.model.Favorites;
import com.google.codelabs.mdc.java.shrine.model.Options;
import com.google.codelabs.mdc.java.shrine.model.ProductEntry;
import com.google.codelabs.mdc.java.shrine.sql.FavoritesDBHelper;
import com.google.codelabs.mdc.java.shrine.sql.ProductDBHelper;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BottomSheetDialog extends BottomSheetDialogFragment
        implements View.OnClickListener, OptionsAdapter.optionClickListener {

    private BottomSheetListener mListener;
    RecyclerView recyclerView;
    private ProductEntry productEntry;
    private ProductDBHelper productDBHelper;
    private FavoritesDBHelper favoritesDBHelper;

    public BottomSheetDialog(BottomSheetListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productDBHelper = new ProductDBHelper(getContext());
        favoritesDBHelper = new FavoritesDBHelper(getContext());

        if (getArguments().containsKey("item"))
            productEntry = getArguments().getParcelable("item");
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_layout, container, false);
        recyclerView = v.findViewById(R.id.options_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        setRecyclerView();
        return v;
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    public void onOptionClicked(View view, int position) {
        boolean added;
        switch (position) {
            case 0:
                added = productDBHelper.newProduct(productEntry);
                if (added) {
                    Toast.makeText(getContext(), "Added to Cart", Toast.LENGTH_SHORT).show();
                } else Toast.makeText(getContext(), "Added, Not", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Favorites favorites = productEntry;
                added = favoritesDBHelper.newFavorite(favorites);
                if (added) {
                    Toast.makeText(getContext(), "Added to Favorites", Toast.LENGTH_SHORT).show();
                } else Toast.makeText(getContext(), "Added, Not", Toast.LENGTH_SHORT).show();
                break;
            case 2:
            case 3:
            case 4:
                Toast.makeText(getContext(), "Ntn", Toast.LENGTH_SHORT).show();
                break;
        }
        mListener.onButtonClicked();
    }

    public interface BottomSheetListener {
        void onButtonClicked();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (BottomSheetListener) this;
        } catch (ClassCastException e) {
            /*Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();*/
        }
    }

    private void setRecyclerView() {
        ArrayList<Options> optionsArrayList = new ArrayList<>();
        int[] drawable = {R.drawable.ic_cart, R.drawable.ic_favorite, R.drawable.ic_save_for_later,
                R.drawable.ic_hourglass, R.drawable.ic_settings};
        String[] titles = {"Add To Cart", "Add To Favorites", "Save For Later", "Hourglass", "Search", ""};

        for (int i = 0; i < drawable.length; i++) {
            optionsArrayList.add(new Options(drawable[i], titles[i]));
        }
        recyclerView.setAdapter(new OptionsAdapter(optionsArrayList, this));
    }
}
