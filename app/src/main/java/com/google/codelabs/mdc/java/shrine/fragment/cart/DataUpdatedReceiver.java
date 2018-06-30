package com.google.codelabs.mdc.java.shrine.fragment.cart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class DataUpdatedReceiver extends BroadcastReceiver {

    OnListUpdateListener mListener;

    public DataUpdatedReceiver(OnListUpdateListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Broadcast Message", Toast.LENGTH_SHORT).show();
        mListener.onListUpdate();
    }

    public interface OnListUpdateListener {
        void onListUpdate();
    }
}
