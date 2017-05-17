package com.android.runintest.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import com.android.runintest.TestService;

import java.util.List;

/**
 * Created by xinsi on 17-5-16.
 */
public class CommonUtils {
    /**
     *  public void setComponentEnabledSetting (ComponentName componentName, int newState, int flags)
     *  componentName：组件名称
     *  newState：组件新的状态，可以设置三个值，分别是如下：
     *  不可用状态：COMPONENT_ENABLED_STATE_DISABLED
     *  可用状态：COMPONENT_ENABLED_STATE_ENABLED
     *  默认状态：COMPONENT_ENABLED_STATE_DEFAULT
     *  flags:行为标签，值可以是DONT_KILL_APP或者0。 0说明杀死包含该组件的app
     *
     *
     *   public int getComponentEnabledSetting(ComponentName componentName)
     *   获取组件的状态
     */
    private static final String TAG = "CommonUtils";



    private static Intent testService;


    //Open TestService
    public static void startTestService(Context mContext, String TAG) {
        String className = "com.android.runintest.TestService";
        LogRuningTest.printInfo(TAG, "TestService isRunning:" +
                isRunningTestService(mContext, className), mContext);
        if(!isRunningTestService(mContext, className)) {
            /**
             * setAction ?????????????????
             */
            testService = new Intent(mContext, TestService.class);
            mContext.startService(testService);
            LogRuningTest.printInfo(TAG, TAG + "start TestService", mContext);
        }
    }

    //Stop TestService
    public static void stopTestService(Context mContext, String TAG){
        String className = "com.android.runintest.TestService";
        LogRuningTest.printInfo(TAG, "TestService isRunning:" +
                isRunningTestService(mContext, className), mContext);
        if(isRunningTestService(mContext, className)){
            if(testService != null) {
                mContext.stopService(testService);
                LogRuningTest.printInfo(TAG, TAG + "stop TestService", mContext);
            }
        }
    }


    /**
     * Determine whether the TestService service is started
     * @param mContext
     * @param className
     * @return
     */
    public static boolean isRunningTestService(Context mContext, String className){
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager
                .getRunningServices(40);
        if (serviceList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }



    /**
     * 禁用/启用组件
     * @param context
     * @param component
     * @param enabled
     */
    public static void setComponentEnabledSetting(Context context,ComponentName component,boolean enabled){
        PackageManager pm = context.getPackageManager();
        LogRuningTest.printDebug(TAG, "component: " + component, context);
        int state = pm.getComponentEnabledSetting(component);
        boolean isEnabled = state == PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
        if (isEnabled != enabled || state == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT) {
            pm.setComponentEnabledSetting(component, enabled
                            ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                            : PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP);
        }
    }

    /**
     * 获取组件状态
     * @param context
     * @param component
     * @return
     */
    public static String getComponentState(Context context, ComponentName component){
        PackageManager pm = context.getPackageManager();
        LogRuningTest.printDebug(TAG, "component: " + component, context);
        int state = pm.getComponentEnabledSetting(component);
        switch (state) {
            case PackageManager.COMPONENT_ENABLED_STATE_DEFAULT:
                return "COMPONENT_ENABLED_STATE_DEFAULT";
            case PackageManager.COMPONENT_ENABLED_STATE_ENABLED:
                return "COMPONENT_ENABLED_STATE_ENABLED";
            case PackageManager.COMPONENT_ENABLED_STATE_DISABLED:
                return "COMPONENT_ENABLED_STATE_DISABLED";
        }
        return null;
    }

}
