package cn.chhd.news.ui.activity

import android.os.Bundle
import cn.chhd.mylibrary.ui.activity.AppCompatPreferenceActivity
import cn.chhd.news.R
import com.blankj.utilcode.util.LogUtils
import kotlinx.android.synthetic.main.app_bar_layout.*


class SettingsActivity : AppCompatPreferenceActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        app_bar.viewTreeObserver.addOnGlobalLayoutListener {
            LogUtils.i(app_bar.elevation)
        }
    }
}
