package cn.chhd.news.ui

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import cn.chhd.news.ui.listener.OnNewsChannelDragListener

/**
 * Created by congh on 2017/12/2.
 */
class ItemDragHelperCallback : ItemTouchHelper.Callback() {

    var mOnNewsChannelDragListener: OnNewsChannelDragListener? = null

    override fun getMovementFlags(recyclerView: RecyclerView?,
                                  viewHolder: RecyclerView.ViewHolder?): Int {
        val manager = recyclerView?.layoutManager
        var dragFlags: Int
        if (manager is GridLayoutManager || manager is StaggeredGridLayoutManager) {
            dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        } else {
            dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        }
        return makeMovementFlags(dragFlags, 0)
    }

    override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?,
                        target: RecyclerView.ViewHolder?): Boolean {
        // 不同Type之间不可移动
        if (viewHolder?.getItemViewType() != target?.getItemViewType()) {
            return false
        }
        mOnNewsChannelDragListener?.onItemMove(viewHolder?.adapterPosition!!, target?.adapterPosition!!)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
    }

    override fun isLongPressDragEnabled(): Boolean {
//        return super.isLongPressDragEnabled()
        // 不需要长按拖动，因为我们的 标题 和 频道推荐 是不需要拖动的，所以手动控制
        return false
    }

    override fun isItemViewSwipeEnabled(): Boolean {
//        return super.isItemViewSwipeEnabled()
        // 不需要侧滑
        return false
    }
}