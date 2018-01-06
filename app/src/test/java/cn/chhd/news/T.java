package cn.chhd.news;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.jakewharton.rxbinding2.widget.RxSeekBar;

import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.functions.Consumer;

/**
 * Created by 葱花滑蛋 on 2017/12/24.
 */

public class T extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }
}
