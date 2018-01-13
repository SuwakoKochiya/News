package cn.chhd.news.ui.activity

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import cn.chhd.mylibrary.util.SoftKeyboardUtils
import cn.chhd.news.R
import cn.chhd.news.bean.NewsArticle
import cn.chhd.news.contract.SearchContract
import cn.chhd.news.di.component.DaggerSearchComponent
import cn.chhd.news.di.module.SearchModule
import cn.chhd.news.global.App
import cn.chhd.news.global.Constant
import cn.chhd.news.presenter.SearchPresenter
import cn.chhd.news.ui.activity.base.ProgressActivity
import cn.chhd.news.ui.adapter.NewsArticleAdapter
import cn.chhd.news.ui.adapter.SearchHistoryAdapter
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.SPUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.android.flexbox.FlexboxLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject


class SearchActivity : ProgressActivity(), SearchContract.View {

    private val mSearchLabels = arrayOf("周星驰电影", "英雄联盟", "小米手机", "Google", "杨幂", "头条", "电脑", "bilibili", "机械键盘", "广州", "创业", "老外评价甄子丹")
    private val mSearchHistoryList = ArrayList<String>()
    private val mGson = Gson()
    private val SP_SEARCH_HISTORY_LIST = "SearchHistoryList"
    private lateinit var mSearchHistoryAdapter: SearchHistoryAdapter
    private val mNewsArticleList = ArrayList<NewsArticle>()
    private lateinit var mNewsArticleAdapter: NewsArticleAdapter

    @Inject
    lateinit var mPresenter: SearchPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        for (label in mSearchLabels) {
            val view = View.inflate(this, R.layout.item_grid_search_label,
                    null) as CardView
            view.setOnClickListener { v ->
                val history = v.findViewById<TextView>(R.id.tv_name).text.toString()
                addSearchHistory(history)
                mEtSearch?.setText(history)
                mEtSearch?.setSelection(mEtSearch?.length() ?: 0)
                requestSearchList()
            }
            val index = (Math.random() * Constant.SWIPE_REFRESH_LAYOUT_COLORS.size).toInt()
            view.setCardBackgroundColor(ContextCompat.getColor(this,
                    Constant.SWIPE_REFRESH_LAYOUT_COLORS[index]))
            val params = FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT,
                    FlexboxLayout.LayoutParams.WRAP_CONTENT)
            params.setMargins(0, 0, ConvertUtils.dp2px(8f),
                    ConvertUtils.dp2px(8f))
            val tvName = view.findViewById<TextView>(R.id.tv_name)
            tvName.text = label
            flex_box.addView(view, params)
        }

        iv_delete_all.setOnClickListener {
            mSearchHistoryList.clear()
            mSearchHistoryAdapter.notifyDataSetChanged()
            SPUtils.getInstance().put(SP_SEARCH_HISTORY_LIST, mGson.toJson(mSearchHistoryList))
        }

        val json = SPUtils.getInstance().getString(SP_SEARCH_HISTORY_LIST)
        val type = object : TypeToken<ArrayList<String>>() {}.type
        val list = mGson.fromJson<ArrayList<String>>(json, type)
        if (list != null) {
            mSearchHistoryList.addAll(list)
        }
        mSearchHistoryAdapter = SearchHistoryAdapter(mSearchHistoryList)
        mSearchHistoryAdapter.onItemClickListener = onItemClickListener
        mSearchHistoryAdapter.onItemChildClickListener = onItemChildClickListener
        rv_history.layoutManager = LinearLayoutManager(this)
        rv_history.adapter = mSearchHistoryAdapter

        mNewsArticleAdapter = NewsArticleAdapter(mNewsArticleList)
        mNewsArticleAdapter.openLoadAnimation()
        mNewsArticleAdapter.setOnItemClickListener { adapter, view, position ->
            WebViewActivity.start(this, mNewsArticleAdapter.data[position].url ?: "")
        }
        rv_news_article.layoutManager = LinearLayoutManager(this)
        rv_news_article.adapter = mNewsArticleAdapter

        DaggerSearchComponent.builder()
                .appComponent(App.mInstance.mComponent)
                .searchModule(SearchModule(this))
                .build().inject(this)
    }

    override fun getContentResId(): Int {
        return R.layout.activity_search
    }

    override fun showPageComplete() {
    }

    override fun retry() {
        requestSearchList()
    }

    override fun onResume() {
        super.onResume()
        mNewsArticleAdapter.notifyDataSetChanged()
    }

    override fun isAutoHideKeyboard(): Boolean {
        return true
    }

    override fun showSearchList(list: MutableList<NewsArticle>) {
        mNewsArticleList.clear()
        mNewsArticleList.addAll(list)
        mNewsArticleAdapter.notifyDataSetChanged()
    }

    private fun addSearchHistory(history: String) {
        if (mSearchHistoryList.contains(history)) {
            mSearchHistoryList.remove(history)
            mSearchHistoryList.add(0, history)
        } else {
            mSearchHistoryList.add(0, history)
        }
        if (mSearchHistoryList.size > 5) {
            mSearchHistoryList.removeAt(mSearchHistoryList.lastIndex)
        }
        SPUtils.getInstance().put(SP_SEARCH_HISTORY_LIST, mGson.toJson(mSearchHistoryList))
        mSearchHistoryAdapter.notifyDataSetChanged()
    }

    private val onItemClickListener
            = BaseQuickAdapter.OnItemClickListener { _, _, position ->
        mEtSearch?.setText(mSearchHistoryList[position])
        mEtSearch?.setSelection(mEtSearch?.length() ?: 0)
        requestSearchList()
    }

    private val onItemChildClickListener
            = BaseQuickAdapter.OnItemChildClickListener { adapter, _, position ->
        adapter.remove(position)
        SPUtils.getInstance().put(SP_SEARCH_HISTORY_LIST, mGson.toJson(mSearchHistoryList))
    }

    private var mEtSearch: EditText? = null

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val menuItem = menu?.findItem(R.id.action_search)
        val searchView = menuItem?.actionView
        mEtSearch = searchView?.findViewById(R.id.edit_text)
        mEtSearch?.let {
            SoftKeyboardUtils.showSoftInput(it)
            RxTextView
                    .textChanges(it)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { charSequence ->
                        if (TextUtils.isEmpty(charSequence)) {
                            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
                            showContentView()
                            showMainView()
                        } else {
                            toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp)
                        }
                    }
            it.setOnEditorActionListener { _, _, _ ->
                if (!TextUtils.isEmpty(it.text.toString())) {
                    mEtSearch?.setSelection(mEtSearch?.length() ?: 0)
                    addSearchHistory(it.text.toString())
                    SoftKeyboardUtils.hideSoftInput(this)
                }
                true
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    private fun showMainView() {
        vg_main.visibility = View.VISIBLE
        rv_news_article.visibility = View.GONE
    }

    private fun showSearchListView() {
        vg_main.visibility = View.GONE
        rv_news_article.visibility = View.VISIBLE
    }

    private fun requestSearchList() {
        showSearchListView()
        mPresenter.requestSearchList(mEtSearch?.text.toString())
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                if (TextUtils.isEmpty(mEtSearch?.text)) {
                    finish()
                } else {
                    mEtSearch?.setText("")
                }
            }
        }
        return true
    }
}
