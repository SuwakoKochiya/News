package cn.chhd.mylibrary.global;

import android.app.Application;

/**
 * Created by congh on 2017/11/24.
 */

public class BaseApplication extends Application {

    private static BaseApplication application;

    @Override
    public void onCreate() {
        super.onCreate();

        application = this;
    }

    public static BaseApplication getApplication() {
        return application;
    }
}
