package cn.chhd.news.ui.activity

import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import cn.chhd.news.R
import cn.chhd.news.global.Constant
import cn.chhd.news.ui.activity.base.BaseActivity
import cn.chhd.news.ui.fragment.PreferenceGeneralFragment
import kotlinx.android.synthetic.main.toolbar.*


class SettingsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val fragmentClassName = intent.getStringExtra(Constant.EXTRA_FRAGMENT_CLASS_NAME)
        val fragmentTitle = intent.getStringExtra(Constant.EXTRA_FRAGMENT_TITLE)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = if (TextUtils.isEmpty(fragmentTitle)) getString(R.string.settings_title) else fragmentTitle

        if (TextUtils.isEmpty(fragmentClassName)) {
            setupFragment(PreferenceGeneralFragment::class.java.name)
        } else {
            setupFragment(fragmentClassName)
        }
    }

    private fun setupFragment(fragmentClassName: String) {
        val fragment = Fragment.instantiate(this, fragmentClassName)
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commitAllowingStateLoss()
    }

    companion object {

        fun start(activity: Activity, fragmentClassName: String, title: String) {
            val intent = Intent(activity, SettingsActivity::class.java)
            intent.putExtra(Constant.EXTRA_FRAGMENT_CLASS_NAME, fragmentClassName)
            intent.putExtra(Constant.EXTRA_FRAGMENT_TITLE, title)
            activity.startActivity(intent)
        }
    }
}
