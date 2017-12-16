package cn.chhd.news.ui.adapter

import android.graphics.Bitmap
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import cn.chhd.news.R
import cn.chhd.news.bean.NewsChannel
import cn.chhd.news.ui.listener.OnNewsChannelDragListener
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_grid_news_channel_my.view.*
import kotlinx.android.synthetic.main.item_list_news_channel_title.view.*

/**
 * Created by 葱花滑蛋 on 2017/11/30.
 */

class NewsChannelAdapter(data: List<NewsChannel>) : BaseMultiItemQuickAdapter<NewsChannel,
        BaseViewHolder>(data) {

    var mOnNewsChannelDragListener: OnNewsChannelDragListener? = null
    private var mIsEdit = false
    private lateinit var titleMyHelp: BaseViewHolder

    init {
        addItemType(NewsChannel.TYPE_TITLE_MY, R.layout.item_list_news_channel_title)
        addItemType(NewsChannel.TYPE_CHANNEL_MY, R.layout.item_grid_news_channel_my)
        addItemType(NewsChannel.TYPE_TITLE_OTHER, R.layout.item_list_news_channel_title)
        addItemType(NewsChannel.TYPE_CHANNEL_OTHER, R.layout.item_grid_news_channel_other)
    }

    override fun convert(helper: BaseViewHolder, item: NewsChannel) {
        when (helper.itemViewType) {
            NewsChannel.TYPE_TITLE_MY -> {
                titleMyHelp = helper
                helper.itemView.tv_title.text = item.title
                helper.itemView.tv_hint_click.text = mContext.getString(R.string.news_dialog_enter)
                helper.itemView.tv_edit.visibility = View.VISIBLE
                helper.itemView.tv_edit.setOnClickListener { view ->
                    if (!mIsEdit) {
                        (view as TextView).text = mContext.getString(R.string.complete)
                        startEditMode(true)
                    } else {
                        (view as TextView).text = mContext.getString(R.string.edit)
                        startEditMode(false)
                    }
                }
            }
            NewsChannel.TYPE_CHANNEL_MY -> {
                helper.itemView.tv_name.text = item.channelName
                helper.itemView.setOnLongClickListener {
                    if (!mIsEdit) {
                        titleMyHelp.itemView.tv_edit.text = mContext.getString(R.string.complete)
                        startEditMode(true)
                    }
                    mOnNewsChannelDragListener?.onStarDrag(helper)
                    true
                }
                helper.itemView.iv_delete.setOnClickListener {
                    // 执行删除，移动到推荐频道列表
                    if (mIsEdit) {
                        moveToOterChannel(helper, item)
                    }
                }
                helper.itemView.setOnClickListener {
                    if (mIsEdit) {
                        moveToOterChannel(helper, item)
                    }
                }
            }
            NewsChannel.TYPE_TITLE_OTHER -> {
                helper.itemView.tv_title.text = item.title
                helper.itemView.tv_hint_click.text = mContext.getString(R.string.news_dialog_add)
                helper.itemView.tv_edit.visibility = View.GONE
            }
            NewsChannel.TYPE_CHANNEL_OTHER -> {
                helper.itemView.tv_name.text = item.channelName
                helper.itemView.setOnClickListener {
                    moveToMyChannel(helper, item)
                }
            }
        }
    }

    private fun moveToOterChannel(helper: BaseViewHolder, item: NewsChannel) {
        var otherFirstPosition = getOtherFirstPosition()
        val currentPosition = helper.layoutPosition
        // 获取到目标View
        val targetView = mRecyclerView.layoutManager
                .findViewByPosition(otherFirstPosition)
        // 获取当前需要移动的View
        val currentView = mRecyclerView.layoutManager
                .findViewByPosition(currentPosition)
        // 如果targetView不在屏幕内，则indexOfChild为-1，此时不需要添加动画，因为此时notifyItemMoved自带一个向目标移动的动画
        // 如果在屏幕内,则添加一个位移动画
        if (mRecyclerView.indexOfChild(targetView) >= 0 && otherFirstPosition != -1) {
            val manager = mRecyclerView.layoutManager
            val spanCount = (manager as GridLayoutManager).spanCount
            val targetX = targetView.left
            var targetY = targetView.top
            val myChannelSize = getMyChannelSize()//这里我是为了偷懒 ，算出来我的频道的大小
            if (myChannelSize % spanCount == 1) {
                // 我的频道最后一行 之后一个，移动后
                targetY -= targetView.height
            }
            item.isEnable = false
            if (mOnNewsChannelDragListener != null)
                mOnNewsChannelDragListener?.onMoveToOtherChannel(currentPosition,
                        otherFirstPosition - 1)
            startAnimation(mRecyclerView, currentView, targetX, targetY)
        } else {
            item.isEnable = false
            if (mOnNewsChannelDragListener != null)
                mOnNewsChannelDragListener?.onMoveToOtherChannel(currentPosition,
                        otherFirstPosition - 1)
        }
    }

    private fun moveToMyChannel(helper: BaseViewHolder, item: NewsChannel) {
        var myLastPosition = getMyLastPosition()
        val currentPosition = helper.layoutPosition
        val targetView = mRecyclerView.layoutManager.findViewByPosition(myLastPosition)
        val currentView = mRecyclerView.layoutManager.findViewByPosition(currentPosition)
        if (mRecyclerView.indexOfChild(targetView) >= 0 && myLastPosition != -1) {
            val manager = mRecyclerView.layoutManager
            val spanCount = (manager as GridLayoutManager).spanCount
            var targetX = targetView.left + targetView.width
            var targetY = targetView.top

            val myChannelSize = getMyChannelSize()
            if (myChannelSize % spanCount == 0) {
                val lastFourthView = mRecyclerView.layoutManager
                        .findViewByPosition(getMyLastPosition() - 3)
                targetX = lastFourthView.left
                targetY = lastFourthView.top + lastFourthView.height
            }
            item.isEnable = true
            startAnimation(mRecyclerView, currentView, targetX, targetY)
            mOnNewsChannelDragListener?.onMoveToMyChannel(currentPosition, myLastPosition + 1)
        } else {
            item.isEnable = true
            if (myLastPosition == -1) myLastPosition = 0
            mOnNewsChannelDragListener?.onMoveToMyChannel(currentPosition, myLastPosition + 1)
        }
    }

    private lateinit var mRecyclerView: RecyclerView

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder {
        mRecyclerView = parent as RecyclerView
        return super.onCreateViewHolder(parent, viewType)
    }

    private fun getOtherFirstPosition(): Int {
        for (i in 0..mData.lastIndex) {
            val channel = mData[i]
            if (channel.itemType == NewsChannel.TYPE_CHANNEL_OTHER) {
                return i
            }
        }
        return -1
    }

    /**
     * 开启编辑模式
     *
     * @Author : 葱花滑蛋
     * @Date : 2017/12/2
     */
    private fun startEditMode(isEdit: Boolean) {
        mIsEdit = isEdit
        val visibleChildCount = mRecyclerView.childCount
        (0 until visibleChildCount)
                .map { mRecyclerView.getChildAt(it) }
                .mapNotNull { it.findViewById<ImageView>(R.id.iv_delete) }
                .forEach { it.visibility = if (isEdit) View.VISIBLE else View.INVISIBLE }
        /*for (i in 0 until visibleChildCount) {
            val View = mRecyclerView.getChildAt(i)
            val iv_delete = View.findViewById<ImageView>(R.id.iv_delete)
            if (iv_delete != null) {
                iv_delete.visibility = if (isEdit) View.VISIBLE else View.INVISIBLE
            }
        }*/
    }

    /**
     * 返回我的频道最后一个的position
     *
     * @Author : congh
     * @Date : 2017/12/1
     */
    private fun getMyLastPosition(): Int {
        for (i in mData.lastIndex downTo 0) {
            val channel = mData[i]
            if (channel.itemType == NewsChannel.TYPE_CHANNEL_MY) {
                return i
            }
        }
        return -1
    }

    /**
     * 返回我的频道个数
     *
     * @Author : 葱花滑蛋
     * @Date : 2017/12/1
     */
    fun getMyChannelSize(): Int {
        var size = 0
        for (i in 0..mData.lastIndex) {
            val channel = mData[i]
            if (channel.itemType == NewsChannel.TYPE_CHANNEL_MY) {
                size++
            }
        }
        return size
    }

    private fun startAnimation(recyclerView: RecyclerView, currentView: View, targetX: Int, targetY: Int) {
        val parent = recyclerView.parent as ViewGroup
        val mirrorView = addMirrorView(recyclerView, parent, currentView)
        val animator = getTranslateAnimator((targetX - currentView.left).toFloat(),
                (targetY - currentView.top).toFloat())
        currentView.visibility = View.INVISIBLE
        mirrorView.startAnimation(animator)
        animator.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                parent.removeView(mirrorView)
                if (currentView.visibility == View.INVISIBLE) {
                    currentView.visibility = View.VISIBLE
                }
            }

            override fun onAnimationStart(p0: Animation?) {
            }
        })
    }

    /**
     *
     * 添加需要移动的镜像View
     *
     * @Author : 葱花滑蛋
     * @Date : 2017/12/1
     */
    private fun addMirrorView(recyclerView: RecyclerView, parent: ViewGroup, view: View): ImageView {
        view.destroyDrawingCache()
        // 首先开启Cache图片 ，然后调用view.getDrawingCache()就可以获取Cache图片
        view.isDrawingCacheEnabled = true
        val mirrorView = ImageView(view.context)
        // 获取该view的Cache图片
        val bitmap = Bitmap.createBitmap(view.drawingCache)
        mirrorView.setImageBitmap(bitmap)
        // 销毁掉cache图片
        view.isDrawingCacheEnabled = false
        val locations = IntArray(2)
        view.getLocationOnScreen(locations) // 获取当前View的坐标
        val parenLocations = IntArray(2)
        recyclerView.getLocationOnScreen(parenLocations) // 获取RecyclerView所在坐标
        val params = FrameLayout.LayoutParams(bitmap.width, bitmap.height)
        params.setMargins(locations[0], locations[1] - parenLocations[1], 0, 0)
        // 在RecyclerView的Parent添加我们的镜像View，parent要是FrameLayout这样才可以放到那个坐标点
        parent.addView(mirrorView, params)
        return mirrorView
    }

    private val ANIM_TIME = 360

    private fun getTranslateAnimator(targetX: Float, targetY: Float): TranslateAnimation {
        val translateAnimation = TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.ABSOLUTE, targetX,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.ABSOLUTE, targetY)
        // RecyclerView默认移动动画250ms 这里设置360ms 是为了防止在位移动画结束后 remove(View)过早 导致闪烁
        translateAnimation.duration = ANIM_TIME.toLong()
        translateAnimation.fillAfter = true
        return translateAnimation
    }


}
