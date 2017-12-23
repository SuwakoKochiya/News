package cn.chhd.news.ui.activity

import android.app.Fragment
import android.os.Bundle
import cn.chhd.news.R
import cn.chhd.news.ui.activity.base.BaseActivity
import cn.chhd.news.ui.fragment.GeneralPreferenceFragment
import kotlinx.android.synthetic.main.toolbar.*


class SettingsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.settings_title)

        val fragment = Fragment.instantiate(this, GeneralPreferenceFragment::class.java.name)
        val transaction = fragmentManager.beginTransaction()
        transaction.setTransition(android.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.replace(R.id.container, fragment)
        transaction.commitAllowingStateLoss()
    }

}
