package com.google.codelabs.mdc.java.shrine.fragment.cart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.codelabs.mdc.java.shrine.OnFragmentRefreshListener;
import com.google.codelabs.mdc.java.shrine.R;
import com.google.codelabs.mdc.java.shrine.activities.DetailActivity;
import com.google.codelabs.mdc.java.shrine.adapter.FavoritesAdapter;
import com.google.codelabs.mdc.java.shrine.model.ProductEntry;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class FavoritesFragment extends CartBaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerFavorites;
    private FavoritesAdapter mFavoritesAdapter;
    private SwipeRefreshLayout refreshLayout;
    private OnFragmentRefreshListener mListener;

    @Override
    public void onListUpdate() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        recyclerFavorites = view.findViewById(R.id.favorites_recycler);
        refreshLayout = view.findViewById(R.id.swipeFavorites);
        recyclerFavorites.setLayoutManager(new LinearLayoutManager(getContext()));
        setRecyclerView();
        refreshLayout.setOnRefreshListener(this);
        return view;
    }

    @SuppressLint("StaticFieldLeak")
    public void setRecyclerView() {
        new AsyncTask<Void, Void, String>() {

            // Runs on Worker thread
            @Override
            protected String doInBackground(Void... params) {
                mFavoritesList = favoritesDBHelper.favoritesList();
                mFavoritesAdapter = new FavoritesAdapter(mFavoritesList, favoritesItemClickListener);
                return "";
            }

            // Runs on Ui thread
            @Override
            protected void onPostExecute(String data) {
                recyclerFavorites.setAdapter(mFavoritesAdapter);
            }
        }.execute();
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        setRecyclerView();
        refreshLayout.setRefreshing(false);
    }

    private FavoritesAdapter.onFavoritesItemClickListener favoritesItemClickListener = new FavoritesAdapter.onFavoritesItemClickListener() {
        @Override
        public void onFavoriteItemClicked(FavoritesAdapter.FavoritesViewHolder viewHolder, int position, Bundle bundle) {
            startActivity(new Intent(getContext(), DetailActivity.class)
                    .putExtras(bundle));
        }

        @Override
        public void onFavoriteItemDeleteClicked(FavoritesAdapter.FavoritesViewHolder viewHolder, int position) {
            boolean deleted = favoritesDBHelper.deleteFavorite(mFavoritesList.get(position).getId());
            if (deleted) {
                Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                setRecyclerView();
            } else {
                Toast.makeText(getContext(), "Deleted, Note", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFavoriteCartItemClicked(FavoritesAdapter.FavoritesViewHolder viewHolder, int position) {
            try {
                favoritesDBHelper.deleteFavorite(mFavoritesList.get(position).getId());
                productDBHelper.newProduct((ProductEntry) mFavoritesList.get(position));
                setRecyclerView();
                onFragmentRefreshListener.onFragmentRefreshed(0);
            } catch (Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    };
}
