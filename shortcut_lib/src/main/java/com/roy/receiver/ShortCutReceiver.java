package com.roy.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * @ProjectName: ShortcutHelpe
 * @Package: com.roy.receiver
 * @ClassName: ShortCutReceiver
 * @Description: ShortCutReceiver
 * @Author: Roy
 * @CreateDate: 2020/5/27 14:35
 */
public class ShortCutReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("roy", "onReceive: 固定快捷方式的回调");
    }
}