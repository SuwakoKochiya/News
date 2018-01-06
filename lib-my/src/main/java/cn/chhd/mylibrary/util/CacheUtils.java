package cn.chhd.mylibrary.util;

import android.content.Context;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.os.RemoteException;
import android.util.Log;

import java.lang.reflect.Method;

import cn.chhd.mylibrary.global.BaseApplication;

/**
 * Created by 葱花滑蛋 on 2018/1/5.
 */

public class CacheUtils {

    private static final String TAG = "CacheUtils";

    private CacheUtils() {
    }

    private static Context getContext() {
        return BaseApplication.getApplication();
    }

    public static void getPackageSizeInfo(final IPackageStatsObserver observer) {
        try {
            Class<?> clazz = Class.forName("android.content.pm.PackageManager");
            Method method = clazz.getMethod("getPackageSizeInfo", String.class,
                    android.content.pm.IPackageStatsObserver.class);
            method.invoke(getContext().getPackageManager(), getContext().getPackageName(),
                    new android.content.pm.IPackageStatsObserver.Stub() {
                        @Override
                        public void onGetStatsCompleted(PackageStats pStats, boolean succeeded)
                                throws RemoteException {
                            Log.i(TAG, "onGetStatsCompleted: ");
                            observer.onGetStatsCompleted(pStats, succeeded);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface IPackageStatsObserver {
        void onGetStatsCompleted(PackageStats pStats, boolean succeeded);
    }

    public static void freeStorageAndNotify() {
        try {
            PackageManager packageManager = getContext().getPackageManager();
            Class<?> clazz = Class.forName("android.content.pm.PackageManager");
            Method method = clazz.getMethod("freeStorageAndNotify", long.class,
                    IPackageDataObserver.class);
            method.invoke(packageManager, Long.MAX_VALUE,
                    new android.content.pm.IPackageDataObserver.Stub() {
                        @Override
                        public void onRemoveCompleted(String packageName, boolean succeeded)
                                throws RemoteException {
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
