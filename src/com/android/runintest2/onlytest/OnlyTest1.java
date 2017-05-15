package com.android.runintest2.onlytest;

import android.app.Fragment;
import com.android.runintest2.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class OnlyTest1 extends Fragment {

    private TextView tv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.runintestfragment, container, false);
        tv= (TextView) view.findViewById(R.id.runintest_tv);
        tv.setText("OnlyTest");
        return view;
    }
}
