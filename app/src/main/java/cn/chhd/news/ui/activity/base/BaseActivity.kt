package cn.chhd.news.ui.activity.base

import android.os.Build
import android.support.annotation.ColorInt
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import cn.chhd.news.global.Constant

/**
 * Created by congh on 2017/11/26.
 */

open class BaseActivity : AppCompatActivity(), Constant {

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                super.onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun setStatusBarColor(@ColorInt color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(color);
        }
    }
}
