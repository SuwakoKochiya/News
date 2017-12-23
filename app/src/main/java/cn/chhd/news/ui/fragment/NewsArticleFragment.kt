package cn.chhd.news.ui.fragment


import android.graphics.Color
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import cn.chhd.mylibrary.ui.adapter.FragmentAdapter
import cn.chhd.mylibrary.util.ToastUtils
import cn.chhd.news.R
import cn.chhd.news.bean.NewsArticle
import cn.chhd.news.contract.NewsArticleContract
import cn.chhd.news.di.component.DaggerNewsArticleComponent
import cn.chhd.news.di.module.NewsArticleModule
import cn.chhd.news.global.App
import cn.chhd.news.global.Constant.Companion.SWIPE_REFRESH_LAYOUT_COLORS
import cn.chhd.news.presenter.NewsArticlePresenter
import cn.chhd.news.ui.adapter.NewsArticleAdapter
import cn.chhd.news.ui.fragment.base.ProgressFragment
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_news_article.*
import java.util.function.Predicate
import javax.inject.Inject


class NewsArticleFragment : ProgressFragment(), NewsArticleContract.View {

    private var mTitle: String? = null
    private var mNum = 10
    private var mStart = 0

    private val mNewsArticleList = ArrayList<NewsArticle>()
    private lateinit var mAdapter: NewsArticleAdapter

    @Inject
    lateinit var present: NewsArticlePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mTitle = arguments.getString(FragmentAdapter.KEY_TITLE)
        }

    }

    override fun getContentResId(): Int {
        return R.layout.fragment_news_article
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipe_refresh_layout.setProgressBackgroundColorSchemeResource(R.color.color_background_light)
        swipe_refresh_layout.setColorSchemeResources(*SWIPE_REFRESH_LAYOUT_COLORS)
        swipe_refresh_layout.setOnRefreshListener(onRefreshListener)

        mAdapter = NewsArticleAdapter(this, mNewsArticleList)
        mAdapter.openLoadAnimation()
        mAdapter.onItemClickListener = onItemClickListener
        recycler_view.layoutManager = LinearLayoutManager(activity)
        recycler_view.adapter = mAdapter

        text_view.text = mTitle
        text_view.setOnClickListener {
            ToastUtils.showShort(mTitle)
        }

        DaggerNewsArticleComponent.builder()
                .appComponent(App.mInstance.mComponent)
                .newsArticleModule(NewsArticleModule(this))
                .build().inject(this)

//        refresh()
    }

    private val onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
        if (adapter.getItemViewType(position) == NewsArticle.ITEM_REFRESH) {
            refresh()
        }
    }

    private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        present.requestNewsArticleList(mTitle!!, mNum, mStart)
    }

    private fun refresh() {
        swipe_refresh_layout.post {
            swipe_refresh_layout.isRefreshing = true
            onRefreshListener.onRefresh()
        }
    }

    override fun showNewsArticlelList(list: ArrayList<NewsArticle>) {
        mStart += mNum
        if (!mNewsArticleList.isEmpty()) {
            removeRefreshItem(mNewsArticleList)
            mNewsArticleList.add(0, NewsArticle())
        }
        mNewsArticleList.addAll(0, list)
        mAdapter.notifyDataSetChanged()
        recycler_view.scrollToPosition(0)
    }

    private fun removeRefreshItem(list: ArrayList<NewsArticle>) {
        val iterator = list.iterator()
        while (iterator.hasNext()) {
            val item = iterator.next()
            if (item.itemType == NewsArticle.ITEM_REFRESH) {
                iterator.remove()
            }
        }
    }

    override fun retry() {
        present.requestNewsArticleList(mTitle!!, mNum, mStart)
    }

    override fun showPageEmpty() {
        if (mNewsArticleList.isEmpty()) {
            super.showPageEmpty()
        }
    }

    override fun showPageError() {
        if (mNewsArticleList.isEmpty()) {
            super.showErrorView()
        } else {
            ToastUtils.showLong(R.string.network_connect_fail)
        }
    }

    override fun showPageComplete() {
        LogUtils.v("showPageComplete -> $mTitle: $swipe_refresh_layout")
        if (swipe_refresh_layout.isRefreshing) swipe_refresh_layout.isRefreshing = false
    }

    override fun onDestroy() {
        super.onDestroy()
        present.onDestroy()
    }

    companion object {

        fun newInstance(title: String): NewsArticleFragment {
            val fragment = NewsArticleFragment()
            val args = Bundle()
            args.putString(FragmentAdapter.KEY_TITLE, title)
            fragment.arguments = args
            return fragment
        }
    }

}
