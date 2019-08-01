package com.zhoug.widget.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;


import com.zhoug.widget.provider.MFileProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 描述：android app 工具 需要用到context
 * zhougan
 * 2019/3/16
 **/
public class AppUtil {
    private static final String TAG = "AppUtil";

    /**
     * 得到当前系统时间
     *
     * @param format0 时间格式  默认：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getCurrentTime(String format0) {
        if (format0 == null) {
            format0 = "yyyy-MM-dd HH:mm:ss";
        }
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat(format0);
        return format.format(date);
    }


    /**
     * 取得屏幕宽高
     *
     * @param context
     * @return [0]为宽, [1]为高
     */
    public static int[] getWindowSize(Context context) {
        int[] size = new int[2];
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        size[0] = outMetrics.widthPixels;//宽
        size[1] = outMetrics.heightPixels;//高
        return size;
    }


    /**
     * 获取设备序列号
     *
     * @return
     */
    @SuppressLint("MissingPermission")
    public static String getDeviceId(Context context) {
        String deviceId = "000";
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            deviceId = telephonyManager.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return deviceId;
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 获取版本名字
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 判断app是否在运行
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAppAlive(Context context, String packageName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processesInErrorState = am.getRunningAppProcesses();
        if (processesInErrorState != null && processesInErrorState.size() > 0) {
            for (ActivityManager.RunningAppProcessInfo info : processesInErrorState) {
                if (info.processName.equals(packageName)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 判断应用是否在前台运行
     *
     * @param context
     * @return 在前台返回true(包括在前台是息屏)
     */
    public static boolean isAppInFront(Context context) {
        boolean isFront = false;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
        if (runningAppProcesses != null && runningAppProcesses.size() > 0) {
            for (ActivityManager.RunningAppProcessInfo processInfo : runningAppProcesses) {
                //前台的应用
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    if (processInfo.pkgList != null) {
                        for (String pkg : processInfo.pkgList) {
                            if (pkg.equals(context.getPackageName())) {
                                isFront = true;
                            }
                        }
                    }
                }
            }
        }
        return isFront;
    }


    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param context
     * @param pxValue （DisplayMetrics类中属性density）
     * @return
     */
    public static int pxTodip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param context
     * @param dipValue （DisplayMetrics类中属性density）
     * @return
     */
    public static int dipTopx(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param context
     * @param pxValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int pxTosp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param context
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int spTopx(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }



    /**
     * 调用第三方app打开文件的Intent
     * manifest中配置的FileProvider要继承 MFileProvider 重写属性AUTHORITY
     * @param context
     * @param path
     * @param minitype "audio/*" "video/*"
     * @return
     */
    public static Intent getFileProvideIntent(Context context, String path, String minitype) {
        if (minitype == null) {
            minitype = "*/*";
        }
        Log.i(TAG, "getFileProvideIntent: path=" + path);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//授予临时权限别忘了
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri contentUri = MFileProvider.getUriForFile(context, MFileProvider.AUTHORITY, new File(path));
            Log.i(TAG, "getFileProvideIntent: " + contentUri);
            intent.setDataAndType(contentUri, minitype);
        } else {
            intent.setDataAndType(Uri.fromFile(new File(path)), minitype);
        }
        return intent;
    }



    /**
     * 获取自定义属性的值 int
     *
     * @param theme
     * @param id
     * @return
     */
    public static int getAttrValueIn(Resources.Theme theme, @AttrRes int id) {
        TypedValue typedValue = new TypedValue();
        theme.resolveAttribute(id, typedValue, true);
        return typedValue.data;
    }

    /**
     * 获取自定义属性的值 String
     *
     * @param theme
     * @param id
     * @return
     */
    public static String getAttrValueString(Resources.Theme theme, @AttrRes int id) {
        TypedValue typedValue1 = new TypedValue();
        theme.resolveAttribute(id, typedValue1, true);
        return typedValue1.toString();
    }

    /**
     * 测量组件的大小
     *
     * @param view
     */
    public static void measureView(View view) {
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int HeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthMeasureSpec, HeightMeasureSpec);
    }

    /**
     * 获取状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    /**
     * 获取底部导航栏的高度
     *
     * @param context
     * @return
     */
    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    /**
     * 安装apk文件
     */
    public static void installApk(Context context, File file) throws FileNotFoundException {
        if (!file.exists()) {
            throw new FileNotFoundException("apk文件不存在" + file);
        } else {
            Uri contentUri=null;
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//授予临时权限别忘了
                 contentUri = MFileProvider.getUriForFile(context, MFileProvider.AUTHORITY, file);
                Log.i(TAG, "getFileProvideIntent: " + contentUri);
            } else {
                contentUri= Uri.fromFile(file);
            }
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            context.startActivity(intent);
            Log.e(TAG, "installApk: 安装apk");
        }
    }

    /**
     * 检测程序是否安装
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isInstalled(Context context,String packageName) {
        PackageManager manager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> installedPackages = manager.getInstalledPackages(0);
        if (installedPackages != null) {
            for (PackageInfo info : installedPackages) {
                if (info.packageName.equals(packageName))
                    return true;
            }
        }
        return false;
    }


    /**
     * 打电话
     * @param context
     * @param phone
     */
    public static void callPhone(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        context.startActivity(intent);
    }

    /**
     * 发短信
     * @param phoneNumber
     * @param message
     */
    public static void sendSMS(Context context,String phoneNumber,String message){
//        if(PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)){
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+phoneNumber));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if(message!=null){
                intent.putExtra("sms_body", message);
            }
            context.startActivity(intent);
//        }
    }


    /**
     * 系统所认为的最小滑动距离TouchSlop
     * @param context
     * @return
     */
    public static int getTouchSlop(Context context){
        return ViewConfiguration.get(context).getScaledTouchSlop();
    }

    /**
     * 拍摄照片和视频后相册中没有文件，的解决办法
     * @param context
     * @param path
     */
    public static void scannerFile(Context context,String path){
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(new File(path));
        intent.setData(uri);
        context.sendBroadcast(intent);
    }

    /**
     * 隐藏键盘
     */
    public static void hideSoftKeyboard(Activity activity) {
        /**
         * 另外，避免软键盘弹出会覆盖底部控件的方法是在布局文件根布局加上一个属性：
         * android:fitsSystemWindows="true"
         */
        if (activity == null) return;
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

        if (null == imm || !imm.isActive()) return;
        View currentFocus = activity.getCurrentFocus();

        if (currentFocus != null) {
            //有焦点关闭
            imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } else {
            //无焦点关闭
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        }

    }

    /**
     * 滚动到recyclerView的指定位置
     * @param recyclerView
     * @param position
     */
    public static void scrollTo(RecyclerView recyclerView, int position){
        if(recyclerView==null) return;
        recyclerView.scrollToPosition(position);
        Log.d(TAG, "scrollTo: position="+position);
        try {
            LinearLayoutManager mLayoutManager =
                    (LinearLayoutManager) recyclerView.getLayoutManager();
            mLayoutManager.scrollToPositionWithOffset(position, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
