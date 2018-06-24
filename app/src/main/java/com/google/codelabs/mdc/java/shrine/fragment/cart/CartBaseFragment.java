package com.google.codelabs.mdc.java.shrine.fragment.cart;

import android.os.Bundle;

import com.google.codelabs.mdc.java.shrine.model.Favorites;
import com.google.codelabs.mdc.java.shrine.model.ProductEntry;
import com.google.codelabs.mdc.java.shrine.sql.FavoritesDBHelper;
import com.google.codelabs.mdc.java.shrine.sql.ProductDBHelper;

import java.util.List;

import androidx.fragment.app.Fragment;

public class CartBaseFragment extends Fragment {
    ProductDBHelper productDBHelper;
    FavoritesDBHelper favoritesDBHelper;
    List<Favorites> mFavoritesList;
    List<ProductEntry> mCartList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productDBHelper = new ProductDBHelper(getContext());
        favoritesDBHelper = new FavoritesDBHelper(getContext());

        mCartList = productDBHelper.productList();
        mFavoritesList = favoritesDBHelper.favoritesList();
    }
}
