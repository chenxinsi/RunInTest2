package com.android.runintest;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.android.runintest.R;

import com.android.runintest.drawer.BaseFragment;
import com.android.runintest.drawer.TestItem;
import com.android.runintest.drawer.TestItemUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class RunInTestFragment extends BaseFragment {

    ArrayList<HashMap<String, String>> mylist;
    ArrayList<TestItem>  items;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TestItem item = (TestItem)getArguments().getParcelable("testitem");

        /**
         * development
         */
        if(item.title != null){
            initData();
            View view = inflater.inflate(R.layout.development_runintest, container, false);
            ListView listview = (ListView) view.findViewById(R.id.development_lv);
            Log.d("xinsi1", "mylist:" + mylist);

            DevelopmentAdapter adapter = new DevelopmentAdapter(getActivity(), mylist);
            listview.setAdapter(adapter);
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    startActivity(items.get(i).intent);
                }
            });
            return view;
        }

        /**
         * factory
         */
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void initData(){
        mylist = new ArrayList<HashMap<String, String>>();
        items = (ArrayList<TestItem>)TestItemUtils.getTestItems(
                    getActivity(), TestItemUtils.ACTION_TESTITEM);
        for(int i = 0; i < items.size(); i++){
            TestItem item = items.get(i);
            HashMap<String, String> map = new HashMap<String, String>();
            if(item.title != null){
                map.put("title", item.title);
                mylist.add(map);
            }
        }
    }

}
