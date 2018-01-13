package cn.chhd.news.presenter

import cn.chhd.news.bean.WechatChannel
import cn.chhd.news.contract.WechatContract
import cn.chhd.news.global.Config
import cn.chhd.news.http.SimpleSubscriber
import cn.chhd.news.presenter.base.BasePresenter
import com.trello.rxlifecycle2.android.FragmentEvent
import javax.inject.Inject

/**
 * Created by 葱花滑蛋 on 2018/1/12.
 */
class WechatPresenter
@Inject constructor(private val model: WechatContract.Model, private val view: WechatContract.View)
    : BasePresenter<WechatContract.Model, WechatContract.View>(model, view) {

    fun requestWechatChannelList() {
        model.getWechatChannelList(Config.JISU_APP_KEY)
                .compose(view.bindToLifecycle(FragmentEvent.DESTROY))
                .subscribe(object : SimpleSubscriber<MutableList<WechatChannel>>(view) {

                    override fun success(t: MutableList<WechatChannel>) {
                        view.showWechatChannelList(t)
                    }

                    override fun error(e: Throwable) {
                        super.error(e)
                        view.showPageEmpty()
                    }
                })
    }
}