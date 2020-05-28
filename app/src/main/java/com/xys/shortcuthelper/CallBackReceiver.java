package com.xys.shortcuthelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * @ProjectName: ShortcutHelpe
 * @Package: com.xys.shortcuthelper
 * @ClassName: CallBackReceiver
 * @Description: CallBackReceiver
 * @Author: Roy
 * @CreateDate: 2020/5/26 21:39
 */

class CallBackReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("roy", "onReceive: 固定快捷方式的回调");
    }
}