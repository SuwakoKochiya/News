package cn.chhd.news.ui.activity

import android.app.Activity
import android.app.ActivityManager
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatDelegate
import android.view.Gravity
import cn.chhd.mylibrary.util.BottomNavigationViewHelper
import cn.chhd.mylibrary.util.CacheUtils
import cn.chhd.news.R
import cn.chhd.news.global.App
import cn.chhd.news.global.Constant
import cn.chhd.news.ui.activity.base.BaseActivity
import cn.chhd.news.ui.fragment.EmptyFragment
import cn.chhd.news.ui.fragment.NewsFragment
import cn.chhd.news.util.SettingsUtils
import com.blankj.utilcode.util.FragmentUtils
import com.blankj.utilcode.util.LogUtils
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity() {

    private val mFragmentList = ArrayList<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mFragmentList.add(NewsFragment.newInstance())
        mFragmentList.add(EmptyFragment.newInstance())

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.container, mFragmentList[0])
        transaction.commit()

        BottomNavigationViewHelper.disableShiftMode(navigation)
        navigation.setOnNavigationItemSelectedListener(onBottomNavItemSelectedListener)

        nav.setNavigationItemSelectedListener(oNavItemSelectedListener)
        val item = nav.menu.findItem(R.id.nav_theme)
        val mode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        if (mode == Configuration.UI_MODE_NIGHT_YES) {
            item.icon = ContextCompat.getDrawable(mActivity, R.drawable.ic_brightness_high_black_24dp)
            item.title = getString(R.string.nav_theme_hight)
        } else {
            item.icon = ContextCompat.getDrawable(mActivity, R.drawable.ic_brightness_low_black_24dp)
            item.title = getString(R.string.nav_theme_low)
        }

        SettingsUtils.setAppComponentClassName(componentName.className)
    }

    private val oNavItemSelectedListener = NavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_theme -> {
                if (item.title == getString(R.string.nav_theme_low)) {
                    item.icon = ContextCompat.getDrawable(mActivity, R.drawable.ic_brightness_high_black_24dp)
                    item.title = getString(R.string.nav_theme_hight)
                } else {
                    item.icon = ContextCompat.getDrawable(mActivity, R.drawable.ic_brightness_low_black_24dp)
                    item.title = getString(R.string.nav_theme_low)
                }
                val mode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
                if (mode == Configuration.UI_MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    SettingsUtils.setNightMode(false)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    SettingsUtils.setNightMode(true)
                }
                FragmentUtils.removeAll(supportFragmentManager)
                reCreate()
            }
            R.id.nav_settings -> {
                startActivity(Intent(mActivity, SettingsActivity::class.java))
            }
            R.id.nav_share -> {

            }
        }
        return@OnNavigationItemSelectedListener true
    }

    override fun getSlideReturnMode(): String {
        return Constant.SLIDE_RETURN_DISABLE
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(Gravity.LEFT)) {
            drawer_layout.closeDrawers()
        } else {
            App.mInstance.initNightMode()
            super.onBackPressed()
        }
    }

    private val onBottomNavItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = when (item.itemId) {
            R.id.navigation_home -> {
                mFragmentList[0]
            }
            else -> {
                mFragmentList[1]
            }
        }
        if (!fragment.isAdded) {
            transaction.add(R.id.container, fragment)
            // Fragment切换动画，淡入淡出
//            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        }
        for (fragment in mFragmentList) {
            transaction.hide(fragment)
        }
        transaction.show(fragment)
        transaction.commit()
        return@OnNavigationItemSelectedListener true
    }

}
