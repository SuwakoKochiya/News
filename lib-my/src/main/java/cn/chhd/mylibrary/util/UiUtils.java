package cn.chhd.mylibrary.util;

import android.content.Context;
import android.content.res.Resources;

import cn.chhd.mylibrary.global.BaseApplication;

/**
 * Created by congh on 2017/11/30.
 */

public class UiUtils {

    private UiUtils() {
    }

    public static Context getContext() {
        return BaseApplication.getApplication();
    }

    public static Resources getResources() {
        return getContext().getResources();
    }

    public static String getString(int resId) {
        return getContext().getString(resId);
    }

    public static String[] getStringArray(int id) {
        return getContext().getResources().getStringArray(id);
    }

    public static int getColor(int id) {
        return getResources().getColor(id);
    }
}
