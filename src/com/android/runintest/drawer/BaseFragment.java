package com.android.runintest.drawer;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.UserHandle;
import android.util.Log;
import com.android.internal.widget.LockPatternUtils;
import com.android.runintest.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.runintest.itemdata.RunInTestData;
import com.android.runintest.utils.LogRuningTest;

/**
 * Created by xinsi on 17-5-15.
 */
public class BaseFragment extends Fragment{

    private static final String TAG = "BaseFragment";
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

    protected SharedPreferences getDefaultSharedPreferences(){
        return getActivity().getSharedPreferences(RunInTestData.RUNINTEST_PREFERENCE, Context.MODE_PRIVATE);
    }

}
