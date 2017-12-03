package cn.chhd.news.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout

import cn.chhd.news.R

/**
 * Created by congh on 2017/11/26.
 */

class SearchView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : RelativeLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(getContext()).inflate(R.layout.view_search, this, true)
    }
}
