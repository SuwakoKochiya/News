package cn.chhd.news.global

import android.content.Context
import cn.chhd.news.R
import com.blankj.utilcode.util.ConvertUtils

/**
 * Created by congh on 2017/12/3.
 */
object Constant {

    private fun getContext(): Context {
        return App.mInstance
    }

    val KEY_ENABLE_NEWS_CHANNEL = "enable_news_channel"
    val KEY_UNENABLE_NEWS_CHANNEL = "unenable_news_channel"

    var SWIPE_REFRESH_LAYOUT_COLORS = intArrayOf(
            android.R.color.holo_green_light, android.R.color.holo_blue_light,
            android.R.color.holo_orange_light, android.R.color.holo_red_light)

    val SLIDE_RETURN_DISABLE = "0"
    val SLIDE_RETURN_EDGE = "1"
    val SLIDE_RETURN_FULL_PAGE = "2"

    val PREF_NO_PHOTO = getContext().getString(R.string.pref_no_photo)!!
    val PREF_SLIDE_RETURN = getContext().getString(R.string.pref_slide_return)!!
    val PREF_TEXT_SIZE = getContext().getString(R.string.pref_text_size)!!
    val PREF_TEXT_SIZE_WEB_VIEW = "pref_text_size_web_view"
    val PREF_NIGHT_MODE = getContext().getString(R.string.pref_night_mode)!!
    val PREF_AUTO_NIGHT_MODE = getContext().getString(R.string.pref_auto_night_mode)!!
    val PREF_AUTO_NIGHT_MODE_SWITCH = getContext().getString(R.string.pref_auto_night_mode_switch)!!
    val PREF_AUTO_NIGHT_MODE_NIGHT = getContext().getString(R.string.pref_auto_night_mode_night)!!
    val PREF_AUTO_NIGHT_MODE_DAY = getContext().getString(R.string.pref_auto_night_mode_day)!!
    val PREF_CACHE_SIZE = getContext().getString(R.string.pref_cache_size)!!

    val PREF_APP_ICON = getContext().getString(R.string.pref_app_icon)!!
    val PREF_APP_COMPONENT_CLASS_NAME = "pref_app_component_class_name"
    val PREF_THEME_COLOR = getContext().getString(R.string.pref_theme_color)!!
    val PREF_NAVIGATION_BAR = getContext().getString(R.string.pref_navigation_bar)!!

    val PREF_LICENSE = getContext().getString(R.string.pref_license)!!
    val PREF_UPGRADE = getContext().getString(R.string.pref_upgrade)!!

    val EXTRA_FRAGMENT_CLASS_NAME = "fragment_class_name"
    val EXTRA_FRAGMENT_TITLE = "fragment_title"

    val TEXT_SIZE_SMALL = ConvertUtils.px2sp(getContext().resources.getDimension(R.dimen.sp_12))
    val TEXT_SIZE_NORMAL = ConvertUtils.px2sp(getContext().resources.getDimension(R.dimen.sp_14))
    val TEXT_SIZE_BIG = ConvertUtils.px2sp(getContext().resources.getDimension(R.dimen.sp_16))

    val APP_GITHUB_URL = "https://github.com/conghuahuadan/News"
}