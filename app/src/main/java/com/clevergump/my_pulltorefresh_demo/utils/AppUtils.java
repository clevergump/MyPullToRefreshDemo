package com.clevergump.my_pulltorefresh_demo.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import com.clevergump.my_pulltorefresh_demo.MainApplication;


public class AppUtils {

    public final static String ACTION_APPLICATION_DETAILS = "android.settings.APPLICATION_DETAILS_SETTINGS";

    private final static String TAG = AppUtils.class.getSimpleName();

    /**
     * 构造函数
     */
    private AppUtils() {
        throw new UnsupportedOperationException(TAG + " cannot be instantiated");
    }

    public static boolean launchAppForResult(Activity from, Intent intent, int requestCode) {
        if (from == null)
            throw new NullPointerException("from Activity is null");
        try {
            from.startActivityForResult(intent, requestCode);
            return true;
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 安全地调用 startActivityForResult()方法
     * @param from
     * @param intent
     * @param requestCode
     * @param msgResId 找不到目标Activity时, 弹出toast中要显示的文字的资源id.
     * @return
     */
    public static boolean launchAppForResult(Activity from, Intent intent, int requestCode, int msgResId) {
        String actNotFoundMsg = MainApplication.get().getResources().getString(msgResId);
        return launchAppForResult(from, intent, requestCode, actNotFoundMsg);
    }

    /**
     * 安全地调用 startActivityForResult()方法
     * @param from
     * @param intent
     * @param requestCode
     * @param actNotFoundMsg 找不到目标Activity时, 弹出toast中要显示的文字.
     * @return
     */
    public static boolean launchAppForResult(Activity from, Intent intent, int requestCode, String actNotFoundMsg) {
        if (from == null)
            throw new NullPointerException("ctx is null");
        try {
            from.startActivityForResult(intent, requestCode);
            return true;
        } catch (ActivityNotFoundException e) {
            if (!TextUtils.isEmpty(actNotFoundMsg)) {
                ToastUtils.showShort(actNotFoundMsg);
            }
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 安全的启动APP
     *
     * @param from
     * @param intent
     */
    public static boolean launchApp(Context from, Intent intent) {
        if (from == null)
            throw new NullPointerException("ctx is null");
        try {
            from.startActivity(intent);
            return true;
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 启动APP
     *
     * @param from
     * @param clazz
     */
    public static void launchApp(Context from, Class<?> clazz) {
        if (clazz == null)
            throw new NullPointerException("the parameter is null");
        Intent intent = new Intent(from, clazz);
        launchApp(from, intent);
    }

    /**
     * 启动APP
     *
     * @param ctx         上下文
     * @param packageName 包名
     * @param className   类名
     */
    public static void launchApp(Context from, String packageName, String className) {
        if (packageName == null || className == null)
            throw new NullPointerException("from Context is null");
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setComponent(new ComponentName(packageName, className));
        launchApp(from, intent);
    }

    /**
     * 卸载APP
     *
     * @param ctx        上下文参数
     * @param packageUri 包URI
     */
    public static void uninstallApp(Context from, Uri packageUri) {
        if (from == null || packageUri == null)
            throw new NullPointerException("the parameter is null");
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageUri);
        launchApp(from, uninstallIntent);
    }

    /**
     * 卸载APP
     */
    public static void uninstallApp(Context from, String pkgName) {
        if (pkgName == null)
            throw new NullPointerException("the parameter is null");
        Uri packageUri = Uri.parse("package:" + pkgName);
        uninstallApp(from, packageUri);
    }

    /**
     * 启动系统设置页面
     */
    public static void launchSettings(Context from) {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        launchApp(from, intent);
    }

    /**
     * 启动系统浏览器页面
     */
    public static void launchBrowser(Context from, String url) {
        Uri browserUri = Uri.parse(url);
        if (null != browserUri) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, browserUri);
            browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            launchApp(from, browserIntent);
        }
    }

    /**
     * 启动APP详情页
     */
    public static void launchAppDetails(Context from, String packageName) {
        if (Build.VERSION.SDK_INT >= 9) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setAction(ACTION_APPLICATION_DETAILS);
            Uri uri = Uri.fromParts("package", packageName, null);
            intent.setData(uri);
            launchApp(from, intent);
        }
    }

    /**
     * 获取包信息
     *
     * @return 包信息
     */
    public static PackageInfo getPackageInfo(Context ctx) {
        final PackageManager pm = ctx.getPackageManager();
        try {
            return pm.getPackageInfo(ctx.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取指定APP上下文
     *
     * @param context     上下文
     * @param packageName 包名
     * @return
     */
    public static Context getAppContext(Context context, String packageName) {
        if (context == null || packageName == null)
            throw new NullPointerException("the parameter is null");
        Context ctx = null;
        try {
            ctx = context.createPackageContext(packageName, Context.CONTEXT_IGNORE_SECURITY);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return ctx;
    }

    /**
     * 判断是否为系统APP
     *
     * @return
     */
    public static boolean isSystemApp(Context ctx, String packageName) {
        PackageManager packageManager = ctx.getPackageManager();
        boolean isSystemApp = false;
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 0);
            if (applicationInfo != null) {
                isSystemApp = ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0)
                        || ((applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return isSystemApp;
    }

    /**
     * 获取版本Code
     *
     * @param ctx
     * @param packageName
     * @return
     */
    public static int getVersionCode(Context ctx, String packageName) {
        final PackageManager packageManager = ctx.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int getVersionCode(Context ctx) {
        final PackageManager packageManager = ctx.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(ctx.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 获取VersonName
     *
     * @param ctx
     * @param packageName
     * @return
     */
    public static String getVersionName(Context ctx, String packageName) {
        final PackageManager packageManager = ctx.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取VersonName
     *
     * @param ctx
     * @return
     */
    public static String getVersionName(Context ctx) {
        final PackageManager packageManager = ctx.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(ctx.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

}
