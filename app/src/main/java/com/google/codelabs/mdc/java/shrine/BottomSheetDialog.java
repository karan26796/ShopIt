package com.google.codelabs.mdc.java.shrine;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.annotation.Nullable;

public class BottomSheetDialog extends BottomSheetDialogFragment
        implements View.OnClickListener {

    private BottomSheetListener mListener;
    mBottomSheetClick mButtonListener;
    Button button1, button2;

    public interface mBottomSheetClick {
        void onClickLike(int position);
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_layout, container, false);
        button1 = v.findViewById(R.id.button1);
        button2 = v.findViewById(R.id.button2);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
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
}
