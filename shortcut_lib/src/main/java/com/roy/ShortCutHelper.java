package com.roy;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;

import com.roy.receiver.ShortCutReceiver;
import com.roy.shortcut_lib.FlowEntranceUtil;
import com.roy.shortcut_lib.ShortcutActivity;
import com.roy.shortcut_lib.ShortcutUtils;
import com.xys.shortcut_lib.R;

import static android.content.ContentValues.TAG;

/**
 * @ProjectName: ShortcutHelpe
 * @Package: com.roy
 * @ClassName: ShortCutHelper
 * @Description: ShortCutHelper
 * @Author: Roy
 * @CreateDate: 2020/5/27 14:24
 */

public class ShortCutHelper {

    /**
     * 追加shortCut
     *
     * @param context
     * @param launcherActivity
     * @param targetActivity
     * @param shortCutName
     * @param allRepeat
     * @param iconResId
     */
    public static void addShortcut(Context context, Activity launcherActivity, Class targetActivity, String shortCutName, boolean allRepeat, int iconResId) {
        if (context == null) {
            throw new IllegalArgumentException("addShortcut context must not be null");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            testShortCut(context, targetActivity, shortCutName, iconResId);
        } else {
            ShortcutUtils.addShortcut(context, getShortCutIntent(launcherActivity, targetActivity), shortCutName, allRepeat,
                    BitmapFactory.decodeResource(context.getResources(), iconResId));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static void testShortCut(Context context, Class targetActivity, String shortCutName, int iconResId) {
        ShortcutManager shortcutManager = (ShortcutManager) context.getSystemService(Context.SHORTCUT_SERVICE);
        boolean requestPinShortcutSupported = shortcutManager.isRequestPinShortcutSupported();
        Log.i(TAG, "启动器是否支持固定快捷方式: " + requestPinShortcutSupported);
        if (requestPinShortcutSupported) {
            Intent shortcutInfoIntent = new Intent(context, targetActivity);
            shortcutInfoIntent.setAction(Intent.ACTION_VIEW);
            ShortcutInfo info = new ShortcutInfo.Builder(context, shortCutName)
                    .setIcon(Icon.createWithResource(context, iconResId))
                    .setIntent(shortcutInfoIntent)
                    .build();
            //当添加快捷方式的确认弹框弹出来时，将被回调CallBackReceiver里面的onReceive方法
            PendingIntent shortcutCallbackIntent = PendingIntent.getBroadcast(context, 0, new Intent(context, ShortCutReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);
            shortcutManager.requestPinShortcut(info, shortcutCallbackIntent.getIntentSender());
        }
    }



    private static Intent getShortCutIntent(Context launcherActivity, Class targetActivity) {
        // 使用MAIN，可以避免部分手机(比如华为、HTC部分机型)删除应用时无法删除快捷方式的问题
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setClass(launcherActivity, targetActivity);
        return intent;
    }

    /**
     * 隐藏图标
     * @param activity
     */
    public static void hideAppIcon(Activity activity) {
        if (activity == null) {
            throw new IllegalArgumentException("hideAppIcon must be an activity");
        }
        PackageManager p = activity.getPackageManager();
        p.setComponentEnabledSetting(activity.getComponentName(), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }

    /**
     * 设置shortcut入口是否可用
     * @param context
     * @param targetActivity
     */
    public static void setEntranceVisibale(Context context,Class targetActivity,boolean visible) {
        FlowEntranceUtil.toggleFlowEntrance(context, targetActivity,visible);
    }
}
