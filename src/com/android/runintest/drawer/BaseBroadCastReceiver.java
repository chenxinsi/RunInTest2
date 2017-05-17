package com.android.runintest.drawer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.android.runintest.itemdata.RunInTestData;

/**
 * Created by xinsi on 17-5-17.
 */
public class BaseBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

    }

    protected SharedPreferences getDefaultSharedPreferences(Context context){
        return context.getSharedPreferences(getDefaultSharedPreferencesName(), Context.MODE_PRIVATE);
    }

    protected String getDefaultSharedPreferencesName(){
        return RunInTestData.RUNINTEST_PREFERENCE;
    }

}
