package com.android.runintest;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.android.runintest.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xinsi on 17-5-23.
 */
public class DevelopmentAdapter  extends BaseAdapter{


    private List<HashMap<String, String>> mData;
    private LayoutInflater mInflater;

    public DevelopmentAdapter(Context context, List<HashMap<String, String>> mData){
        this.mData = mData;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(convertView == null){
          holder = new ViewHolder();
          convertView = mInflater.inflate(R.layout.development_lv_item, null);
          holder.mTextView =(TextView)convertView.findViewById(R.id.development_item_textview);
          convertView.setTag(holder);
        }else{
          holder = (ViewHolder)convertView.getTag();
        }
        holder.mTextView.setText(position +". "+ (String)mData.get(position).get("title"));
        return convertView;
    }

    private final class ViewHolder{
        TextView mTextView;
    }

}
