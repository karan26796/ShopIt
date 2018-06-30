package com.google.codelabs.mdc.java.shrine.fragment.cart;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.google.codelabs.mdc.java.shrine.OnFragmentRefreshListener;
import com.google.codelabs.mdc.java.shrine.model.Favorites;
import com.google.codelabs.mdc.java.shrine.model.ProductEntry;
import com.google.codelabs.mdc.java.shrine.sql.FavoritesDBHelper;
import com.google.codelabs.mdc.java.shrine.sql.ProductDBHelper;

import java.util.List;

import androidx.fragment.app.Fragment;

public abstract class CartBaseFragment extends Fragment implements DataUpdatedReceiver.OnListUpdateListener {
    ProductDBHelper productDBHelper;
    FavoritesDBHelper favoritesDBHelper;
    List<Favorites> mFavoritesList;
    List<ProductEntry> mCartList;
    OnFragmentRefreshListener onFragmentRefreshListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productDBHelper = new ProductDBHelper(getContext());
        favoritesDBHelper = new FavoritesDBHelper(getContext());

        mCartList = productDBHelper.productList();
        mFavoritesList = favoritesDBHelper.favoritesList();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onFragmentRefreshListener = (OnFragmentRefreshListener) context;
        } catch (ClassCastException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onFragmentRefreshListener = null;
    }
}
