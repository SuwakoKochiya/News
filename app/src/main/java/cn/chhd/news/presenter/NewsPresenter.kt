package cn.chhd.news.presenter

import cn.chhd.news.bean.NewsChannel
import cn.chhd.news.contract.NewsContract
import cn.chhd.news.global.Config
import cn.chhd.news.global.Constant
import cn.chhd.news.http.SimpleSubscriber
import cn.chhd.news.presenter.base.BasePresenter
import com.blankj.utilcode.util.SPUtils
import com.google.gson.Gson
import com.trello.rxlifecycle2.android.FragmentEvent
import javax.inject.Inject

class NewsPresenter
@Inject constructor(private val model: NewsContract.Model, private val view: NewsContract.View)
    : BasePresenter<NewsContract.Model, NewsContract.View>(model, view) {

    fun requestNewsChannelList() {
        model.getNewsChannelList(Config.JISU_APP_KEY)
                .compose(view.bindToLifecycle(FragmentEvent.DESTROY))
                .subscribe(object : SimpleSubscriber<ArrayList<String>>(view) {

                    override fun success(t: ArrayList<String>) {
                        val enableList = ArrayList<NewsChannel>()
                        val unEnableList = ArrayList<NewsChannel>()
                        for (i in 0..5) {
                            val newsChannel = NewsChannel()
                            newsChannel.channelName = t[i]
                            newsChannel.channelId = t[i]
                            newsChannel.isEnable = true
                            enableList.add(newsChannel)
                        }
                        for (i in 6 until t.size) { // until：i in [1, names.size)
                            val newsChannel = NewsChannel()
                            newsChannel.channelName = t[i]
                            newsChannel.channelId = t[i]
                            newsChannel.isEnable = false
                            unEnableList.add(newsChannel)
                        }
                        SPUtils.getInstance().put(Constant.KEY_ENABLE_NEWS_CHANNEL,
                                Gson().toJson(enableList))
                        SPUtils.getInstance().put(Constant.KEY_UNENABLE_NEWS_CHANNEL,
                                Gson().toJson(unEnableList))
                        view.showNewsChannelList(t)
                    }
                })
    }
}