package cn.chhd.news.global

/**
 * Created by congh on 2017/12/3.
 */
interface Constant {

    companion object {

        val KEY_ENABLE_NEWS_CHANNEL = "enable_news_channel"
        val KEY_UNENABLE_NEWS_CHANNEL = "unenable_news_channel"

        var SWIPE_REFRESH_LAYOUT_COLORS = intArrayOf(
                android.R.color.holo_green_light, android.R.color.holo_blue_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light)
    }
}