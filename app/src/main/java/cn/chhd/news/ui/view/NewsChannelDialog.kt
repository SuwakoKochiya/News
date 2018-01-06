package cn.chhd.news.ui.view

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.chhd.news.R
import cn.chhd.news.bean.NewsChannel
import cn.chhd.news.global.Constant
import cn.chhd.news.ui.ItemDragHelperCallback
import cn.chhd.news.ui.adapter.NewsChannelAdapter
import cn.chhd.news.ui.listener.OnNewsChannelChangeListener
import cn.chhd.news.ui.listener.OnNewsChannelDragListener
import cn.chhd.news.util.SettingsUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.chad.library.adapter.base.BaseViewHolder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.dialog_news_channel.*

/**
 * Created by congh on 2017/11/26.
 */
class NewsChannelDialog : DialogFragment(), View.OnClickListener {

    private val mDatas = ArrayList<NewsChannel>()
    private var mEnableList = ArrayList<NewsChannel>()
    private var mUnEnableList = ArrayList<NewsChannel>()
    private lateinit var mAdapter: NewsChannelAdapter
    private lateinit var mHelper: ItemTouchHelper
    private val mGson = Gson()

    var mOnNewsChannelChangeListener: OnNewsChannelChangeListener? = null
    var mOnDismissListener: DialogInterface.OnDismissListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, SettingsUtils.getThemeColor())
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        dialog.window.setWindowAnimations(R.style.DialogBottomSlideAnim)
        return inflater?.inflate(R.layout.dialog_news_channel, container)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iv_close.setOnClickListener(this)

        mDatas.add(NewsChannel(getString(R.string.news_dialog_title_my), true))
        var json = SPUtils.getInstance().getString(Constant.KEY_ENABLE_NEWS_CHANNEL)
        val type = object : TypeToken<ArrayList<NewsChannel>>() {}.type
        if (!TextUtils.isEmpty(json))
            mEnableList = mGson.fromJson<ArrayList<NewsChannel>>(json, type)
        mDatas.addAll(mEnableList)
        mDatas.add(NewsChannel(getString(R.string.news_dialog_title_other), false))
        json = SPUtils.getInstance().getString(Constant.KEY_UNENABLE_NEWS_CHANNEL)
        if (!TextUtils.isEmpty(json))
            mUnEnableList = mGson.fromJson<ArrayList<NewsChannel>>(json, type)
        mDatas.addAll(mUnEnableList)

        mAdapter = NewsChannelAdapter(mDatas)
        mAdapter.mOnNewsChannelDragListener = mOnNewsChannelDragListener
        recycler_view.adapter = mAdapter
        val gridLayoutManager = GridLayoutManager(activity, 4)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = mAdapter.getItemViewType(position)
                return if (viewType == NewsChannel.TYPE_TITLE_MY
                        || viewType == NewsChannel.TYPE_TITLE_OTHER) 4 else 1
            }
        }
        recycler_view.layoutManager = gridLayoutManager
        val callback = ItemDragHelperCallback()
        callback.mOnNewsChannelDragListener = mOnNewsChannelDragListener
        mHelper = ItemTouchHelper(callback)
        mHelper.attachToRecyclerView(recycler_view)
    }

    private val mOnNewsChannelDragListener = object : OnNewsChannelDragListener {
        /**
         * 拖拽时，在我的频道之间移动
         *
         * @Author : 葱花滑蛋
         * @Date : 2017/12/3
         */
        override fun onItemMove(starPos: Int, endPos: Int) {
            onMove(starPos, endPos)
            mOnNewsChannelChangeListener?.onItemMove(starPos - 1, endPos - 1)
        }

        override fun onMoveToMyChannel(starPos: Int, endPos: Int) {
            onMove(starPos, endPos)
            mOnNewsChannelChangeListener?.onMoveToMyChannel(starPos - 1 - mAdapter.getMyChannelSize(),
                    endPos - 1)
        }

        override fun onMoveToOtherChannel(starPos: Int, endPos: Int) {
            onMove(starPos, endPos)
            mOnNewsChannelChangeListener?.onMoveToOtherChannel(starPos - 1,
                    endPos - 2 - mAdapter.getMyChannelSize())
        }

        override fun onStarDrag(helper: BaseViewHolder) {
            mHelper.startDrag(helper)
        }
    }

    private fun onMove(startPos: Int, endPos: Int) {
        val channel = mDatas[startPos]
        mDatas.removeAt(startPos)
        mDatas.add(endPos, channel)
        mAdapter.notifyItemMoved(startPos, endPos)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.iv_close -> dismiss()
        }
    }

    fun show(manager: FragmentManager) {
        show(manager, "")
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        mOnDismissListener?.onDismiss(dialog)
    }

    companion object {

        fun newInstance(): NewsChannelDialog {
            val dialog = NewsChannelDialog()
            return dialog
        }
    }

}