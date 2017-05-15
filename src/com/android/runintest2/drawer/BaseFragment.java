package com.android.runintest2.drawer;

import com.android.runintest2.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by xinsi on 17-5-15.
 */
public class BaseFragment extends Fragment{
    private TextView tv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.runintestfragment, container, false);
        tv = (TextView)view.findViewById(R.id.runintest_tv);
        tv.setText(getTestItem().title);
        tv.setTextSize(50);
        return view;
    }

    protected  TestItem getTestItem(){
        Bundle bundle = getArguments();
        TestItem item = bundle.getParcelable("testitem");
        return item;
    }
}
