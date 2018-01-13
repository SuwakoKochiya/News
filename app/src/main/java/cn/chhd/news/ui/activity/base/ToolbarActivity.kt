package cn.chhd.news.ui.activity.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import cn.chhd.news.R
import kotlinx.android.synthetic.main.activity_toolbar.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by 葱花滑蛋 on 2018/1/8.
 */

abstract class ToolbarActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toolbar)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (getContainerResId() != 0)
            LayoutInflater.from(this).inflate(getContainerResId(), container, true)
    }

    fun setToolbarContainer(view: View) {
        toolbar_container.addView(view)
    }

    fun setToolbarContainer(view: View, isFill: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        toolbar_container.addView(view)
    }

    abstract fun getContainerResId(): Int
}
