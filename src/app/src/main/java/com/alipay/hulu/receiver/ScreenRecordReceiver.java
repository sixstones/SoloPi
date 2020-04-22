package com.alipay.hulu.receiver;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.alipay.hulu.screenRecord.RecordService;
import com.alipay.hulu.screenRecord.RecorderConfigActivity;

import java.util.List;

public class ScreenRecordReceiver extends BroadcastReceiver {

    private static final String TAG = "ScreenRecordReceiver";

    public ScreenRecordReceiver(){

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String switcher = intent.getStringExtra("switcher");

        Log.i(TAG, "onReceive: screen record " + switcher);
        Intent service_intent = new Intent(context, RecordService.class);
        service_intent.setPackage(context.getPackageName());

        if(isScreenRecordServiceWorking(context)){
            if(switcher.equalsIgnoreCase("off")){
                service_intent.setAction(RecordService.INTENT_END);
            }else {
                Log.i(TAG, "onReceive: start service");
                service_intent.setAction(RecordService.INTENT_START);
            }
            context.startService(service_intent);
        }else {
            Log.e(TAG, "onReceive: screenreord service not runnint");
        }


    }

    private int[] getSelectedWidthHeight(String selected){

        String[] xes = selected.split("x");
        if (xes.length != 2) {
            throw new IllegalArgumentException();
        }
        return new int[]{Integer.parseInt(xes[0]), Integer.parseInt(xes[1])};
    }


    private boolean isScreenRecordServiceWorking(Context context){
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(40);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals("com.alipay.hulu.screenRecord.RecordService")) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }
}
