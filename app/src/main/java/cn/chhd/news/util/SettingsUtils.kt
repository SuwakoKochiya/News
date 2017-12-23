package cn.chhd.news.util

import android.content.Context
import android.preference.PreferenceManager
import cn.chhd.news.R
import cn.chhd.news.global.App
import com.blankj.utilcode.util.SPUtils

/**
 * Created by 葱花滑蛋 on 2017/12/17.
 */
object SettingsUtils {

    private fun getContext(): Context {
        return App.mInstance
    }

    private val PREF_NIGHT_MODE = getContext().getString(R.string.pref_night_mode)
    private val PREF_NO_PHOTO = getContext().getString(R.string.pref_no_photo)

    fun setNightMode(flag: Boolean) {
        SPUtils.getInstance().put(PREF_NIGHT_MODE, flag)
    }

    fun isNightMode(): Boolean {
        return SPUtils.getInstance().getBoolean(PREF_NIGHT_MODE)
    }

    fun setNoPhoto(flag: Boolean) {
        ImageLoader.instance.configuration.setNoPhoto(flag)
    }

    fun isNoPhoto(): Boolean {
        var setting = PreferenceManager.getDefaultSharedPreferences(getContext())
        return setting.getBoolean(PREF_NO_PHOTO, false)
    }
}