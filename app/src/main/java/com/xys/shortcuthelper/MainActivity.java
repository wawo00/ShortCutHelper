package com.xys.shortcuthelper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.roy.ShortCutHelper;
import com.roy.badge_lib.BadgeUtil;
import com.roy.shortcut_lib.ShortcutSuperUtils;
import com.roy.shortcut_lib.ShortcutUtils;

import java.util.List;

import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.READ_SYNC_SETTINGS;
import static android.Manifest.permission.REQUEST_INSTALL_PACKAGES;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.ContentValues.TAG;

public class MainActivity extends Activity {

    // 快捷方式名
    private String mShortcutName = "学习工具";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(MainActivity.this, REQUEST_INSTALL_PACKAGES) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(MainActivity.this, READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(MainActivity.this, READ_SYNC_SETTINGS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{WRITE_EXTERNAL_STORAGE, REQUEST_INSTALL_PACKAGES, READ_PHONE_STATE}, 001);
            }
        }
    }

    public void addShortcutTest(View view) {
        // 系统方式创建
        // ShortcutUtils.addShortcut(this, getShortCutIntent(), mShortcutName);

        // 创建前判断是否存在
//        if (!ShortcutSuperUtils.isShortCutExist(this, mShortcutName, getShortCutIntent())) {
//            ShortcutUtils.addShortcut(this, getShortCutIntent(), mShortcutName, false,
//                    BitmapFactory.decodeResource(getResources(), com.xys.shortcut_lib.R.drawable.ocsplayer));
//            finish();
//        } else {
//            Toast.makeText(this, "Shortcut is exist!", Toast.LENGTH_SHORT).show();
//        }
//        ShortcutUtils.addShortcut(this, getShortCutIntent(), mShortcutName, true,
//                BitmapFactory.decodeResource(getResources(), com.xys.shortcut_lib.R.drawable.ocsplayer));
        // 为某个包创建快捷方式
        // ShortcutSuperUtils.addShortcutByPackageName(this, this.getPackageName());
//      if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
//          testShortCut(this);
//      }else{
//          ShortcutUtils.addShortcut(this, getShortCutIntent(), mShortcutName, true,
//                BitmapFactory.decodeResource(getResources(), com.xys.shortcut_lib.R.drawable.ocsplayer));
//      }
      ShortCutHelper.addShortcut(this,MainActivity.this, ShortcutActivity.class,"学习任务",true,R.drawable.ocsplayer);
    }

    public void removeShortcutTest(View view) {
        ShortcutUtils.removeShortcut(this, getShortCutIntent(), mShortcutName);
    }

    public void updateShortcutTest(View view) {
        ShortcutSuperUtils.updateShortcutIcon(this, mShortcutName, getShortCutIntent(),
                BitmapFactory.decodeResource(getResources(), com.xys.shortcut_lib.R.mipmap.ic_launcher));
    }

    public void toggleFlowEntrance(View view) {
//        FlowEntranceUtil.toggleFlowEntrance(this, ShortcutActivity.class);
        ShortCutHelper.setEntranceVisibale(this,ShortcutActivity.class,false);
    }

    private Intent getShortCutIntent() {
        // 使用MAIN，可以避免部分手机(比如华为、HTC部分机型)删除应用时无法删除快捷方式的问题
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setClass(MainActivity.this, ShortcutActivity.class);
        return intent;
    }

    public void addBadgeInIcon(View view) {
        // 添加角标测试
//        BadgeUtil.with(getApplicationContext()).count(9);
//        BadgeUtil.setBadgeCount(this, 4,R.drawable.ocsplayer);
    }

//    public void delBadgeInIcon(View view) {
//        BadgeUtil.resetBadgeCount(getApplicationContext());
//    }

    /**
     * Bug利用测试,请勿滥用
     *
     * @param view view
     */
    public void madMode(View view) {
        madMode(99);
    }

    /**
     * 清除Bug角标
     *
     * @param view view
     */
    public void cleanMadMode(View view) {
        madMode(0);
    }

    /**
     * 获取所有App的包名和启动类名
     *
     * @param count count
     */
    private void madMode(int count) {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        @SuppressLint("WrongConstant") List<ResolveInfo> list = getPackageManager().queryIntentActivities(
                intent, PackageManager.GET_ACTIVITIES);
        for (int i = 0; i < list.size(); i++) {
            ActivityInfo activityInfo = list.get(i).activityInfo;
            String activityName = activityInfo.name;
            String packageName = activityInfo.applicationInfo.packageName;
            BadgeUtil.setBadgeOfMadMode(getApplicationContext(), count, packageName, activityName);
        }
    }

    public void hideIcon(View view) {
//        if (Build.VERSION.SDK_INT <Build.VERSION_CODES.O){
//            PackageManager p = getPackageManager();
//            p.setComponentEnabledSetting(getComponentName(), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
//        }else{
//            PackageManager p = getPackageManager();
//            p.setComponentEnabledSetting(getComponentName(), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
//        }
        ShortCutHelper.hideAppIcon(this);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void testShortCut(Context context) {
        ShortcutManager shortcutManager = (ShortcutManager) context.getSystemService(Context.SHORTCUT_SERVICE);
        boolean requestPinShortcutSupported = shortcutManager.isRequestPinShortcutSupported();
        Log.i(TAG, "启动器是否支持固定快捷方式: "+requestPinShortcutSupported);

        if (requestPinShortcutSupported) {

            Intent shortcutInfoIntent = new Intent(context, ShortcutActivity.class);

            shortcutInfoIntent.setAction(Intent.ACTION_VIEW);

            ShortcutInfo info = new ShortcutInfo.Builder(context, "tzw")
                    .setIcon(Icon.createWithResource(context, R.drawable.ocsplayer))
                    .setShortLabel("O系统短")
                    .setLongLabel("O系统长")
                    .setIntent(shortcutInfoIntent)
                    .build();

            //当添加快捷方式的确认弹框弹出来时，将被回调CallBackReceiver里面的onReceive方法
            PendingIntent shortcutCallbackIntent = PendingIntent.getBroadcast(context, 0, new Intent(context, CallBackReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);

            shortcutManager.requestPinShortcut(info, shortcutCallbackIntent.getIntentSender());

        }
    }
}

