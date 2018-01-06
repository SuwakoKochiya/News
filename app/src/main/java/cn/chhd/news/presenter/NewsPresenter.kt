package cn.chhd.news.presenter

import cn.chhd.news.bean.NewsChannel
import cn.chhd.news.bean.ResponseData
import cn.chhd.news.contract.NewsContract
import cn.chhd.news.global.Config
import cn.chhd.news.global.Constant
import cn.chhd.news.http.RxHttpReponseCompat
import cn.chhd.news.http.SimpleSubscriber
import cn.chhd.news.presenter.base.BasePresenter
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.google.gson.Gson
import javax.inject.Inject

/**
 * Created by 葱花滑蛋 on 2017/12/12.
 */
class NewsPresenter
@Inject constructor(private val model: NewsContract.Model, private val view: NewsContract.View)
    : BasePresenter<NewsContract.Model, NewsContract.View>(model, view) {

    private var mSubscriber: SimpleSubscriber<ArrayList<String>>? = null

    fun requestNewsChannelList() {
        mSubscriber = getSubscriber()
        model.getNewsChannelList(Config.JISU_APP_KEY).compose(RxHttpReponseCompat.transform()).subscribe(mSubscriber)
    }

    private fun getSubscriber(): SimpleSubscriber<ArrayList<String>> {
        return object : SimpleSubscriber<ArrayList<String>>() {

            override fun success(list: ArrayList<String>) {

                var enableList = ArrayList<NewsChannel>()
                var unEnableList = ArrayList<NewsChannel>()

                for (i in 0..5) {
                    val newsChannel = NewsChannel()
                    newsChannel.channelName = list[i]
                    newsChannel.channelId = list[i]
                    newsChannel.isEnable = true
                    newsChannel.position = i
                    enableList.add(newsChannel)
                }
                for (i in 6 until list.size) { // until：i in [1, names.size)
                    val newsChannel = NewsChannel()
                    newsChannel.channelName = list[i]
                    newsChannel.channelId = list[i]
                    newsChannel.isEnable = false
                    newsChannel.position = i
                    unEnableList.add(newsChannel)
                }

                SPUtils.getInstance().put(Constant.KEY_ENABLE_NEWS_CHANNEL, Gson().toJson(enableList))
                SPUtils.getInstance().put(Constant.KEY_UNENABLE_NEWS_CHANNEL, Gson().toJson(unEnableList))

                view.showNewsChannelList(list)
            }

            override fun delayMillis(): Int {
                return 0
            }
        }
    }

    override fun onDestroy() {
        mSubscriber?.dispose()
    }
}