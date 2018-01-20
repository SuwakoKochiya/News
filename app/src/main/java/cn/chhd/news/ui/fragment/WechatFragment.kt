package cn.chhd.news.ui.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import cn.chhd.mylibrary.ui.adapter.FragmentAdapter
import cn.chhd.news.R
import cn.chhd.news.bean.WechatChannel
import cn.chhd.news.contract.WechatContract
import cn.chhd.news.di.component.DaggerWechatComponent
import cn.chhd.news.di.module.WechatModule
import cn.chhd.news.global.App
import cn.chhd.news.presenter.WechatPresenter
import cn.chhd.news.ui.fragment.base.ProgressFragment
import kotlinx.android.synthetic.main.fragment_wechat.*
import javax.inject.Inject


class WechatFragment : ProgressFragment(), WechatContract.View {

    companion object {

        fun newInstance(): WechatFragment {
            return WechatFragment()
        }
    }

    private val mChannelList = ArrayList<WechatChannel>()
    private val mFragmentList = ArrayList<Fragment>()
    private lateinit var mAdapter: FragmentAdapter

    @Inject
    lateinit var mPresenter: WechatPresenter

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tab_layout.setupWithViewPager(view_pager)
        mAdapter = FragmentAdapter(childFragmentManager, mFragmentList)
        view_pager.adapter = mAdapter
        view_pager.offscreenPageLimit = 2

        DaggerWechatComponent.builder()
                .appComponent(App.mInstance.mComponent)
                .wechatModule(WechatModule(this))
                .build().inject(this)

        mPresenter.requestWechatChannelList()
    }

    override fun showWechatChannelList(list: MutableList<WechatChannel>) {
        mChannelList.addAll(list)
        mChannelList.mapTo(mFragmentList) {
            WechatArticleFragment.newInstance(it.channel, it.channelid)
        }
        mAdapter.notifyDataSetChanged()
    }

    override fun showPageComplete() {
    }

    override fun getContentResId(): Int {
        return R.layout.fragment_wechat
    }

    override fun retry() {
        mPresenter.requestWechatChannelList()
    }
}
