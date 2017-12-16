package cn.chhd.news.presenter

import cn.chhd.news.bean.ListData
import cn.chhd.news.bean.NewsArticle
import cn.chhd.news.contract.NewsArticleContract
import cn.chhd.news.global.Config
import cn.chhd.news.http.SimpleSubscriber
import cn.chhd.news.presenter.base.BasePresenter
import javax.inject.Inject

/**
 * Created by 葱花滑蛋 on 2017/12/4.
 */
class NewsArticlePresenter
@Inject constructor(private val model: NewsArticleContract.Model, private val view: NewsArticleContract.View)
    : BasePresenter<NewsArticleContract.Model, NewsArticleContract.View>(model, view) {

    private lateinit var mSubscriber: SimpleSubscriber<ListData<NewsArticle>>

    fun requestNewsArticleList(channel: String,
                               num: Int,
                               start: Int) {
        mSubscriber = getSubscriber()
        model.getNewsArticlelList(Config.JISU_APP_KEY, channel, num, start).subscribe(mSubscriber)
    }

    private fun getSubscriber(): SimpleSubscriber<ListData<NewsArticle>> {
        return object : SimpleSubscriber<ListData<NewsArticle>>() {

            override fun success(t: ListData<NewsArticle>) {
                t.list?.let {
                    view.showNewsArticlelList(it)
                }
            }
        }
    }

    override fun onDestroy() {
        mSubscriber.dispose()
    }
}