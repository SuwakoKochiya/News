package cn.chhd.news.ui.listener

import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by 葱花滑蛋 on 2017/12/24.
 */
interface OnItemClickListener {

    fun onItemClick(adapter: RecyclerView.Adapter<*>, view: View, position: Int)
}