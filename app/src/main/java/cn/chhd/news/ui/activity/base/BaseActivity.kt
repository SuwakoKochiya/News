package cn.chhd.news.ui.activity.base

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.view.MenuItem
import cn.chhd.mylibrary.util.UiUtils
import cn.chhd.news.R
import cn.chhd.news.global.Constant
import cn.chhd.news.util.SettingsUtils
import com.r0adkll.slidr.Slidr
import com.r0adkll.slidr.model.SlidrConfig
import com.r0adkll.slidr.model.SlidrPosition
import android.util.TypedValue
import com.blankj.utilcode.util.LogUtils


/**
 * Created by congh on 2017/11/26.
 */

open class BaseActivity : AppCompatActivity() {

    companion object {
        private val mActivityList = ArrayList<Activity>()
    }

    protected lateinit var mActivity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(SettingsUtils.getThemeColor())
        super.onCreate(savedInstanceState)
        mActivity = this
        mActivityList.add(this)

        handleSlideReturn()

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

    }

    override fun onResume() {
        super.onResume()
        setNavigationBarColor()
    }

    override fun onDestroy() {
        super.onDestroy()
        mActivityList.remove(this)
    }

    fun setNavigationBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (SettingsUtils.isNavigationBarColour()) {
                val outValue = TypedValue()
                theme.resolveAttribute(R.attr.color_navigation_bar, outValue, true)
                window.navigationBarColor = UiUtils.getColor(outValue.resourceId)
            } else {
                window.navigationBarColor = Color.BLACK
            }
        }
    }

    fun reCreateToAll() {
        window.setWindowAnimations(R.style.WindowAnimationFadeInOut)
        for (activity in mActivityList) {
            LogUtils.i(activity)
            activity.recreate()
        }
    }

    fun reCreate() {
        window.setWindowAnimations(R.style.WindowAnimationFadeInOut)
        recreate()
    }

    fun reStart() {
        val intent = intent
        overridePendingTransition(0, 0)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        finish()
        overridePendingTransition(0, 0)
        startActivity(intent)
    }

    /**
     * 处理滑动返回
     */
    private fun handleSlideReturn() {
        val mode = getSlideReturnMode()
        if (mode != Constant.SLIDE_RETURN_DISABLE) {
            val edge = mode == Constant.SLIDE_RETURN_EDGE
            val config = SlidrConfig.Builder()
                    .position(SlidrPosition.LEFT)
                    .edge(edge)
                    .build()
            Slidr.attach(this, config).unlock()
        }
    }

    open fun getSlideReturnMode(): String {
        return SettingsUtils.getSlideReturnMode()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                super.onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
