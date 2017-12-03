package cn.chhd.mylibrary.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by congh on 2017/11/30.
 */

public class ToastUtils {

    private static Toast toast;

    private ToastUtils() {
    }

    public static void showShort(CharSequence text) {
        if (toast == null) {
            toast = Toast.makeText(UiUtils.getContext(), text, Toast.LENGTH_SHORT);
        }
        toast.setText(text);
        toast.show();
    }

    public static void showShort(int resId) {
        if (toast == null) {
            toast = Toast.makeText(UiUtils.getContext(), resId, Toast.LENGTH_SHORT);
        }
        toast.setText(resId);
        toast.show();
    }

    public static void showLong(CharSequence text) {
        if (toast == null) {
            toast = Toast.makeText(UiUtils.getContext(), text, Toast.LENGTH_SHORT);
        }
        toast.setText(text);
        toast.show();
    }

    public static void showLong(int resId) {
        if (toast == null) {
            toast = Toast.makeText(UiUtils.getContext(), resId, Toast.LENGTH_SHORT);
        }
        toast.setText(resId);
        toast.show();
    }
}
