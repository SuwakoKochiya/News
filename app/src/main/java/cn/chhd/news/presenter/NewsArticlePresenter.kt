package cn.chhd.news.presenter

import cn.chhd.news.bean.ListData
import cn.chhd.news.bean.NewsArticle
import cn.chhd.news.contract.NewsArticleContract
import cn.chhd.news.global.Config
import cn.chhd.news.http.SimpleSubscriber
import cn.chhd.news.presenter.base.BasePresenter
import com.trello.rxlifecycle2.android.FragmentEvent
import javax.inject.Inject

/**
 * Created by 葱花滑蛋 on 2017/12/4.
 */
class NewsArticlePresenter
@Inject constructor(private val model: NewsArticleContract.Model, private val view: NewsArticleContract.View)
    : BasePresenter<NewsArticleContract.Model, NewsArticleContract.View>(model, view) {

    fun requestNewsArticleList(channel: String,
                               num: Int,
                               start: Int) {
        model.getNewsArticleList(Config.JISU_APP_KEY, channel, num, start)
                .compose(view.bindToLifecycle(FragmentEvent.DESTROY))
                .subscribe(object : SimpleSubscriber<ListData<NewsArticle>>(view) {

                    override fun success(t: ListData<NewsArticle>) {
                        if (t.list != null && !t.list?.isEmpty()!!) {
                            view.showNewsArticlelList(t.list!!)
                        } else {
                            view.showPageEmpty()
                        }
                    }

                    override fun error(e: Throwable) {
                        super.error(e)
                        view.showPageError()
                    }
                })
    }
}
