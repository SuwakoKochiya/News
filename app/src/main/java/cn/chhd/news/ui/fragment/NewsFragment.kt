package cn.chhd.news.ui.fragment


import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.*
import cn.chhd.mylibrary.ui.adapter.FragmentAdapter
import cn.chhd.news.R
import cn.chhd.news.bean.NewsChannel
import cn.chhd.news.contract.NewsContract
import cn.chhd.news.di.component.DaggerNewsComponent
import cn.chhd.news.di.module.NewsModule
import cn.chhd.news.global.App
import cn.chhd.news.global.Constant
import cn.chhd.news.presenter.NewsPresenter
import cn.chhd.news.ui.fragment.base.BaseFragment
import cn.chhd.news.ui.listener.OnNewsChannelChangeListener
import cn.chhd.news.ui.view.NewsChannelDialog
import com.blankj.utilcode.util.SPUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_news.*
import javax.inject.Inject

class NewsFragment : BaseFragment(), NewsContract.View, View.OnClickListener {

    private val mFragmentList = ArrayList<Fragment>()
    private lateinit var mAdapter: FragmentAdapter
    private var mEnableList = ArrayList<NewsChannel>()
    private var mUnEnableList = ArrayList<NewsChannel>()
    private val mGson = Gson()

    @Inject
    lateinit var mPresenter: NewsPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DaggerNewsComponent.builder()
                .appComponent(App.mInstance.mComponent)
                .newsModule(NewsModule(this))
                .build().inject(this)

        var enableJson = SPUtils.getInstance().getString(Constant.KEY_ENABLE_NEWS_CHANNEL)
        var unEnableJson = SPUtils.getInstance().getString(Constant.KEY_ENABLE_NEWS_CHANNEL)
        if (TextUtils.isEmpty(enableJson) && TextUtils.isEmpty(unEnableJson)) {
            mPresenter.requestNewsChannelList()
        } else {
            initNewsChannelDatas()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }

    private fun initView() {
        tab_layout.setupWithViewPager(view_pager)

        iv_add.setOnClickListener(this)

        mEnableList.mapTo(mFragmentList) { NewsArticleFragment.newInstance(it.channelName!!) }
        mAdapter = FragmentAdapter(childFragmentManager, mFragmentList)
        view_pager.adapter = mAdapter
    }

    private fun initNewsChannelDatas() {
        var json = SPUtils.getInstance().getString(Constant.KEY_ENABLE_NEWS_CHANNEL)
        val type = object : TypeToken<ArrayList<NewsChannel>>() {}.type
        if (!TextUtils.isEmpty(json))
            mEnableList = mGson.fromJson<ArrayList<NewsChannel>>(json, type)
        json = SPUtils.getInstance().getString(Constant.KEY_UNENABLE_NEWS_CHANNEL)
        if (!TextUtils.isEmpty(json))
            mUnEnableList = mGson.fromJson<ArrayList<NewsChannel>>(json, type)

        initView()
    }

    override fun showNewsChannelList(list: ArrayList<String>) {
        initNewsChannelDatas()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(true)
        setHasOptionsMenu(true)

        val drawerLayout = activity.findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(activity, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.main, menu)
        val menuItem = menu?.findItem(R.id.action_search)
        val searchView = menuItem?.actionView
        searchView?.setOnClickListener {
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.iv_add -> {
                val dialog = NewsChannelDialog.newInstance()
                dialog.mOnNewsChannelChangeListener = mOnNewsChannelChangeListener
                dialog.show(fragmentManager)
                dialog.mOnDismissListener = DialogInterface.OnDismissListener {
                    if (mEnableList.isEmpty()) {
                        mPresenter.requestNewsChannelList()
                    }
                    mAdapter.notifyDataSetChanged()

                    SPUtils.getInstance().put(Constant.KEY_ENABLE_NEWS_CHANNEL, mGson.toJson(mEnableList))
                    SPUtils.getInstance().put(Constant.KEY_UNENABLE_NEWS_CHANNEL, mGson.toJson(mUnEnableList))
                }
            }
        }
    }

    private fun <T> listMove(datas: MutableList<T>, startPos: Int, endPos: Int) {
        val data = datas.get(startPos)
        datas.removeAt(startPos)
        datas.add(endPos, data)
    }

    private val mOnNewsChannelChangeListener = object : OnNewsChannelChangeListener {

        override fun onItemMove(starPos: Int, endPos: Int) {
            listMove(mEnableList, starPos, endPos)
            listMove(mFragmentList, starPos, endPos)
        }

        override fun onMoveToMyChannel(starPos: Int, endPos: Int) {
            val channel = mUnEnableList.removeAt(starPos)
            channel.isEnable = true
            mEnableList.add(endPos, channel)
            mFragmentList.add(NewsArticleFragment.newInstance(channel.channelName!!))
        }

        override fun onMoveToOtherChannel(starPos: Int, endPos: Int) {
            mFragmentList.removeAt(starPos)
            val channel = mEnableList.removeAt(starPos)
            channel.isEnable = false
            mUnEnableList.add(endPos, channel)
        }
    }

    companion object {

        fun newInstance(): NewsFragment {
            return NewsFragment()
        }
    }
}
