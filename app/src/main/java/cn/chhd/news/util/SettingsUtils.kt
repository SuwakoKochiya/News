package cn.chhd.news.util

import android.annotation.SuppressLint
import android.content.Context
import android.preference.PreferenceManager
import cn.chhd.news.R
import cn.chhd.news.global.App
import cn.chhd.news.global.Constant
import com.blankj.utilcode.util.SPUtils

/**
 * Created by 葱花滑蛋 on 2017/12/17.
 */
object SettingsUtils {

    private var mPreferences = PreferenceManager.getDefaultSharedPreferences(getContext())

    private fun getContext(): Context {
        return App.mInstance
    }

    fun setNightMode(flag: Boolean) {
        SPUtils.getInstance().put(Constant.PREF_NIGHT_MODE, flag)
    }

    fun isNightMode(): Boolean {
        return SPUtils.getInstance().getBoolean(Constant.PREF_NIGHT_MODE)
    }

    fun setNoPhoto(flag: Boolean) {
        ImageLoader.instance.mConfiguration.setNoPhoto(flag)
    }

    fun isNoPhoto(): Boolean {
        return mPreferences.getBoolean(Constant.PREF_NO_PHOTO, false)
    }

    fun getSlideReturnMode(): String {
        return mPreferences.getString(Constant.PREF_SLIDE_RETURN, Constant.SLIDE_RETURN_DISABLE)
    }

    fun setTextSize(textSize: Float) {
        SPUtils.getInstance().put(Constant.PREF_TEXT_SIZE, textSize)
    }

    fun getTextSize(): Float {
        return SPUtils.getInstance().getFloat(Constant.PREF_TEXT_SIZE, 0f)
    }

    fun isAutoNightMode(): Boolean {
        return mPreferences.getBoolean(Constant.PREF_AUTO_NIGHT_MODE_SWITCH, false)
    }

    fun setAutoNightTime(hour: Int, minute: Int) {
        SPUtils.getInstance().put(Constant.PREF_AUTO_NIGHT_MODE_NIGHT + "_hour", hour)
        SPUtils.getInstance().put(Constant.PREF_AUTO_NIGHT_MODE_NIGHT + "_minute", minute)
    }

    fun getAutoNightTime(): IntArray {
        val ints = IntArray(2)
        ints[0] = SPUtils.getInstance().getInt(Constant.PREF_AUTO_NIGHT_MODE_NIGHT
                + "_hour", 23)
        ints[1] = SPUtils.getInstance().getInt(Constant.PREF_AUTO_NIGHT_MODE_NIGHT
                + "_minute", 0)
        return ints
    }

    fun setAutoDayTime(hour: Int, minute: Int) {
        SPUtils.getInstance().put(Constant.PREF_AUTO_NIGHT_MODE_DAY + "_hour", hour)
        SPUtils.getInstance().put(Constant.PREF_AUTO_NIGHT_MODE_DAY + "_minute", minute)
    }

    fun getAutoDayTime(): IntArray {
        val ints = IntArray(2)
        ints[0] = SPUtils.getInstance().getInt(Constant.PREF_AUTO_NIGHT_MODE_DAY
                + "_hour", 7)
        ints[1] = SPUtils.getInstance().getInt(Constant.PREF_AUTO_NIGHT_MODE_DAY
                + "_minute", 0)
        return ints
    }


    fun setAppComponentClassName(className: String) {
        SPUtils.getInstance().put(Constant.PREF_APP_COMPONENT_CLASS_NAME, className)
    }

    fun getAppComponentClassName(): String {
        return SPUtils.getInstance().getString(Constant.PREF_APP_COMPONENT_CLASS_NAME)
    }

    fun setThemeColor(resId: Int) {
        SPUtils.getInstance().put(Constant.PREF_THEME_COLOR, resId)
    }

    fun getThemeColor(): Int {
        return SPUtils.getInstance().getInt(Constant.PREF_THEME_COLOR, R.style.AppTheme_Red)
    }

    fun isNavigationBarColour(): Boolean {
        return mPreferences.getBoolean(Constant.PREF_NAVIGATION_BAR, true)
    }

}