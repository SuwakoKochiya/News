package cn.chhd.news.ui.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.chhd.android.common.ui.adapter.FragmentAdapter
import cn.chhd.news.R
import cn.chhd.news.bean.WechatArticle
import cn.chhd.news.contract.WechatArticleContract
import cn.chhd.news.di.component.DaggerWechatArticleComponent
import cn.chhd.news.di.module.WechatArticleModule
import cn.chhd.news.global.App
import cn.chhd.news.presenter.WechatArticlePresenter
import cn.chhd.news.ui.activity.WebViewActivity
import cn.chhd.news.ui.adapter.WechatArticleAdapter
import cn.chhd.news.ui.fragment.base.PullToRefreshFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_wechat_article.*
import javax.inject.Inject


class WechatArticleFragment : PullToRefreshFragment<WechatArticleAdapter, WechatArticle>(), WechatArticleContract.View {
    companion object {

        fun newInstance(title: String, channelId: String): Fragment {
            val fragment = WechatArticleFragment()
            val data = Bundle()
            data.putString(FragmentAdapter.KEY_TITLE, title)
            data.putString("channelId", channelId)
            fragment.arguments = data
            return fragment
        }
    }

    private var mTitle = ""
    private var mChannelId = ""
    private var mNum = 10
    private var mStart = 0

    @Inject
    lateinit var mPresenter: WechatArticlePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mTitle = arguments.getString(FragmentAdapter.KEY_TITLE)
            mChannelId = arguments.getString("channelId")
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DaggerWechatArticleComponent.builder()
                .appComponent(App.mInstance.mComponent)
                .wechatArticleModule(WechatArticleModule(this))
                .build().inject(this)
    }

    override fun onResume() {
        super.onResume()
        mAdapter.notifyDataSetChanged()
    }

    override fun getContentResId(): Int {
        return R.layout.fragment_wechat_article
    }

    override fun showWechatArticlelList(list: ArrayList<WechatArticle>) {
        mStart += mNum
        if (!mItems.isEmpty()) {
            removeRefreshItem(mItems)
            mItems.add(0, WechatArticle())
        }
        mItems.addAll(0, list)
        mAdapter.notifyDataSetChanged()
        recycler_view.scrollToPosition(0)
    }

    private fun removeRefreshItem(list: ArrayList<WechatArticle>) {
        val iterator = list.iterator()
        while (iterator.hasNext()) {
            val item = iterator.next()
            if (item.itemType == WechatArticle.ITEM_REFRESH) {
                iterator.remove()
            }
        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        if (adapter?.getItemViewType(position) == WechatArticle.ITEM_REFRESH) {
            refresh()
        } else {
            WebViewActivity.start(activity, mItems[position].url)
        }
    }

    override fun getAdapter(): WechatArticleAdapter {
        return WechatArticleAdapter(mItems)
    }

    override fun getLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(activity)
    }

    override fun loadMore() {
        mAdapter.loadMoreEnd()
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
    }

    override fun load() {
        mPresenter.requestWechatArticleList(mChannelId, mNum, mStart)
    }
}
