package cn.chhd.news.ui.listener

import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by congh on 2017/12/2.
 */
interface OnNewsChannelDragListener : OnNewsChannelChangeListener {

    fun onStarDrag(helper: BaseViewHolder)
}