package cn.chhd.news.ui.fragment.base

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import cn.chhd.mylibrary.util.ToastUtils
import cn.chhd.news.R
import cn.chhd.news.global.Constant
import com.chad.library.adapter.base.BaseQuickAdapter


/**
 * Created by 葱花滑蛋 on 2018/1/12.
 */
abstract class PullToRefreshFragment<Adapter : BaseQuickAdapter<*, *>, Bean>
    : LazyFragment(), BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

    protected lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    protected lateinit var mRecyclerView: RecyclerView

    protected val mItems = ArrayList<Bean>()
    protected lateinit var mAdapter: Adapter

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            mSwipeRefreshLayout = view?.findViewById(R.id.swipe_refresh_layout)!!
        } catch (e: Exception) {
            throw RuntimeException("Layout must have one SwipeRefreshLayout, and id must set swipe_refresh_layout.")
        }

        try {
            mRecyclerView = view?.findViewById(R.id.recycler_view)!!
        } catch (e: Exception) {
            throw RuntimeException("Layout must have one RecyclerView, and id must set recycler_view.")
        }

        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.color_background_light)
        mSwipeRefreshLayout.setColorSchemeResources(*Constant.SWIPE_REFRESH_LAYOUT_COLORS)
        mSwipeRefreshLayout.setOnRefreshListener(onRefreshListener)

        mAdapter = getAdapter()
        mAdapter.openLoadAnimation()
        mAdapter.setEnableLoadMore(true)
        mAdapter.setOnLoadMoreListener({ loadMore() }, mRecyclerView)
        mAdapter.onItemClickListener = this
        mRecyclerView.layoutManager = getLayoutManager()
        mRecyclerView.adapter = mAdapter
    }

    private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        load()
    }

    fun refresh() {
        mSwipeRefreshLayout.post {
            mSwipeRefreshLayout.isRefreshing = true
            onRefreshListener.onRefresh()
        }
    }

    override fun onLazyLoad() {
        refresh()
    }

    override fun showPageEmpty() {
        if (mItems.isEmpty()) {
            super.showPageEmpty()
        }
    }

    override fun showPageError() {
        if (mItems.isEmpty()) {
            super.showPageError()
        } else {
            ToastUtils.showLong(R.string.network_connect_fail)
        }
    }


    override fun showPageComplete() {
        if (mSwipeRefreshLayout.isRefreshing) mSwipeRefreshLayout.isRefreshing = false
    }

    override fun retry() {
        load()
    }

    abstract fun getAdapter(): Adapter

    abstract fun getLayoutManager(): RecyclerView.LayoutManager

    abstract fun load()

    abstract fun loadMore()
}