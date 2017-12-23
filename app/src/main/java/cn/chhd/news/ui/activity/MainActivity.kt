package cn.chhd.news.ui.activity

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatDelegate
import android.view.MenuItem
import cn.chhd.mylibrary.util.BottomNavigationViewHelper
import cn.chhd.news.R
import cn.chhd.news.http.SimpleSubscriber
import cn.chhd.news.ui.activity.base.BaseActivity
import cn.chhd.news.ui.fragment.EmptyFragment
import cn.chhd.news.ui.fragment.NewsFragment
import cn.chhd.news.util.SettingsUtils
import com.blankj.utilcode.util.LogUtils
import io.reactivex.Flowable
import kotlinx.android.synthetic.main.activity_main.*

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

    }

    private val oNavItemSelectedListener = NavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_theme -> {
                if (item.title == getString(R.string.nav_theme_low)) {
                    item.icon = resources.getDrawable(R.drawable.ic_brightness_high_black_24dp)
                    item.title = getString(R.string.nav_theme_hight)
                } else {
                    item.icon = resources.getDrawable(R.drawable.ic_brightness_low_black_24dp)
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
//                recreate()
                val intent = Intent(this@MainActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
            R.id.nav_settings -> {
                startActivity(Intent(mActivity, SettingsActivity::class.java))
            }
        }
        return@OnNavigationItemSelectedListener true
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
        }
        for (fragment in mFragmentList) {
            transaction.hide(fragment)
        }
        transaction.show(fragment)
        transaction.commit()
        return@OnNavigationItemSelectedListener true
    }
}
