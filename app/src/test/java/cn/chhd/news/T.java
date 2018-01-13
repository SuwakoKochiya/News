package cn.chhd.news;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxSeekBar;

import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.functions.Consumer;

/**
 * Created by 葱花滑蛋 on 2017/12/24.
 */

public class T implements Parcelable {

    private String s;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.s);
    }

    public T() {
    }

    protected T(Parcel in) {
        this.s = in.readString();
    }

    public static final Parcelable.Creator<T> CREATOR = new Parcelable.Creator<T>() {
        @Override
        public T createFromParcel(Parcel source) {
            return new T(source);
        }

        @Override
        public T[] newArray(int size) {
            return new T[size];
        }
    };
}
