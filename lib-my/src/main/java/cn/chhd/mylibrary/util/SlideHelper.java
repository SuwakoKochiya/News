package cn.chhd.mylibrary.util;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by 葱花滑蛋 on 2017/12/24.
 */

public class SlideHelper {

    private SlideHelper() {
    }

    public static void requestParentDisallowInterceptTouchEvent(View view) {
        view.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }
}
