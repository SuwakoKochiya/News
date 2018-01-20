package cn.chhd.news.ui.activity.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatDelegate
import android.util.TypedValue
import android.view.MenuItem
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import cn.chhd.mylibrary.util.UiUtils
import cn.chhd.news.R
import cn.chhd.news.contract.IBaseView
import cn.chhd.news.global.Constant
import cn.chhd.news.util.SettingsUtils
import com.r0adkll.slidr.Slidr
import com.r0adkll.slidr.model.SlidrConfig
import com.r0adkll.slidr.model.SlidrPosition
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.FragmentEvent
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import android.view.View
import android.widget.EditText


/**
 * Created by congh on 2017/11/26.
 */

open class BaseActivity : RxAppCompatActivity(), IBaseView {

    protected val mMenuItemIdDefault = 10

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

    private var mIsDestroy = false

    override fun onDestroy() {
        mActivityList.remove(this)
        mIsDestroy = true
        super.onDestroy()
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
        reStart()
        mActivityList.remove(this)
        for (activity in mActivityList) {
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

    override fun <T> bindToLifecycle(event: ActivityEvent): LifecycleTransformer<T>? {
        return bindUntilEvent(event)
    }

    override fun <T> bindToLifecycle(event: FragmentEvent): LifecycleTransformer<T>? {
        return null
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action === MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (isShouldHideKeyboard(v, ev) && isAutoHideKeyboard()) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun isShouldHideKeyboard(v: View?, event: MotionEvent?): Boolean {
        if (v != null && v is EditText && event != null) {
            val l = intArrayOf(0, 0)
            v.getLocationInWindow(l)
            val left = l[0]
            val top = l[1]
            val bottom = top + v.getHeight()
            val right = left + v.getWidth()
            return !(event.x > left && event.x < right && event.y > top && event.y < bottom)
        }
        return false
    }

    open fun isAutoHideKeyboard(): Boolean {
        return false
    }
}
