package cn.chhd.news.ui.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.MenuItem
import cn.chhd.mylibrary.util.BottomNavigationViewHelper
import cn.chhd.news.R
import cn.chhd.news.ui.activity.base.BaseActivity
import cn.chhd.news.ui.fragment.EmptyFragment
import cn.chhd.news.ui.fragment.NewsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, NewsFragment.newInstance())
        transaction.commit()

        BottomNavigationViewHelper.disableShiftMode(navigation)
        navigation.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val transaction = supportFragmentManager.beginTransaction()
        when (item.itemId) {
            R.id.navigation_home -> {
                transaction.replace(R.id.container, NewsFragment.newInstance())
            }
            else -> {
                transaction.replace(R.id.container, EmptyFragment.newInstance())
            }
        }
        transaction.commit()
        return true
    }
}
