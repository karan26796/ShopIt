package com.google.codelabs.mdc.java.shrine;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.codelabs.mdc.java.shrine.adapter.OptionsAdapter;
import com.google.codelabs.mdc.java.shrine.model.Options;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BottomSheetDialog extends BottomSheetDialogFragment
        implements View.OnClickListener {

    private BottomSheetListener mListener;
    RecyclerView recyclerView;
    ArrayList<Options> optionsArrayList;

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

    public interface BottomSheetListener {
        void onButtonClicked(String text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
        }
    }

    private void setRecyclerView() {
        optionsArrayList = new ArrayList<>();
        int[] drawable = {R.drawable.ic_favorite, R.drawable.ic_delete, R.drawable.ic_home,
                R.drawable.ic_hourglass, R.drawable.ic_settings};
        String[] titles = {"Favorite", "Delete", "Home", "Hourglass", "Search", ""};

        for (int i = 0; i < drawable.length; i++) {
            optionsArrayList.add(new Options(drawable[i], titles[i]));
        }
        recyclerView.setAdapter(new OptionsAdapter(optionsArrayList));
    }
}
