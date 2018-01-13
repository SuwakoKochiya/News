package cn.chhd.news.ui.activity

import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import cn.chhd.news.R
import cn.chhd.news.global.Constant
import cn.chhd.news.ui.activity.base.BaseActivity
import cn.chhd.news.ui.activity.base.ToolbarActivity
import cn.chhd.news.ui.fragment.PreferenceGeneralFragment
import kotlinx.android.synthetic.main.toolbar.*


class SettingsActivity : ToolbarActivity() {

    companion object {

        fun start(activity: Activity, fragmentClassName: String, title: String) {
            val intent = Intent(activity, SettingsActivity::class.java)
            intent.putExtra(Constant.EXTRA_FRAGMENT_CLASS_NAME, fragmentClassName)
            intent.putExtra(Constant.EXTRA_FRAGMENT_TITLE, title)
            activity.startActivity(intent)
        }
    }

    override fun getContainerResId(): Int {
        return R.layout.activity_settings
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fragmentClassName = intent.getStringExtra(Constant.EXTRA_FRAGMENT_CLASS_NAME)
        val fragmentTitle = intent.getStringExtra(Constant.EXTRA_FRAGMENT_TITLE)

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuItem = menu?.add(0, mMenuItemIdDefault, 0, "Github")!!
        menuItem.setIcon(R.drawable.ic_github_white_24dp)
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            mMenuItemIdDefault -> {
                val intent = Intent()
                intent.action = "android.intent.action.VIEW"
                val url = Uri.parse(Constant.APP_GITHUB_URL)
                intent.data = url
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
