package cn.chhd.news.ui.fragment


import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import cn.chhd.mylibrary.ui.adapter.FragmentAdapter
import cn.chhd.news.R
import cn.chhd.news.bean.NewsArticle
import cn.chhd.news.contract.NewsArticleContract
import cn.chhd.news.di.component.DaggerNewsArticleComponent
import cn.chhd.news.di.module.NewsArticleModule
import cn.chhd.news.global.App
import cn.chhd.news.presenter.NewsArticlePresenter
import cn.chhd.news.ui.activity.WebViewActivity
import cn.chhd.news.ui.adapter.NewsArticleAdapter
import cn.chhd.news.ui.fragment.base.PullToRefreshFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_news_article.*
import javax.inject.Inject


class NewsArticleFragment : PullToRefreshFragment<NewsArticleAdapter, NewsArticle>(), NewsArticleContract.View {

    companion object {
        fun newInstance(title: String, id: String?): NewsArticleFragment {
            val fragment = NewsArticleFragment()
            val args = Bundle()
            args.putString(FragmentAdapter.KEY_TITLE, title)
            fragment.arguments = args
            return fragment
        }
    }

     var mTitle = ""
    private var mNum = 10
    private var mStart = 0

    @Inject
    lateinit var mPresenter: NewsArticlePresenter

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

        DaggerNewsArticleComponent.builder()
                .appComponent(App.mInstance.mComponent)
                .newsArticleModule(NewsArticleModule(this))
                .build().inject(this)
    }


    override fun onResume() {
        super.onResume()
        mAdapter.notifyDataSetChanged()
    }

    override fun showNewsArticlelList(list: ArrayList<NewsArticle>) {
        mStart += mNum
        if (!mItems.isEmpty()) {
            removeRefreshItem(mItems)
            mItems.add(0, NewsArticle())
        }
        mItems.addAll(0, list)
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

    override fun getAdapter(): NewsArticleAdapter {
        return NewsArticleAdapter(mItems)
    }

    override fun getLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(activity)
    }

    override fun load() {
        mPresenter.requestNewsArticleList(mTitle, mNum, mStart)
    }

    override fun loadMore() {
        mAdapter.loadMoreEnd()
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {

    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        if (adapter?.getItemViewType(position) == NewsArticle.ITEM_REFRESH) {
            refresh()
        } else {
            WebViewActivity.start(activity, mItems[position].url)
        }
    }

}
