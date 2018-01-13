package cn.chhd.news.presenter

import cn.chhd.news.bean.NewsArticle
import cn.chhd.news.bean.SearchData
import cn.chhd.news.contract.SearchContract
import cn.chhd.news.global.Config
import cn.chhd.news.http.SimpleSubscriber
import cn.chhd.news.presenter.base.BasePresenter
import com.trello.rxlifecycle2.android.ActivityEvent
import javax.inject.Inject

/**
 * Created by 葱花滑蛋 on 2018/1/10.
 */

class SearchPresenter
@Inject constructor(private val model: SearchContract.Model, private val view: SearchContract.View)
    : BasePresenter<SearchContract.Model, SearchContract.View>(model, view) {

    fun requestSearchList(keyword: String) {
        model.getSearchList(Config.JISU_APP_KEY, keyword)
                .compose(view.bindToLifecycle(ActivityEvent.DESTROY))
                .subscribe(object : SimpleSubscriber<SearchData<NewsArticle>>(view) {
                    override fun before() {
                        super.before()
                        view.showPageLoading()
                    }

                    override fun success(t: SearchData<NewsArticle>) {
                        if (t.list.isEmpty()) {
                            view.showPageEmpty()
                        } else {
                            view.showSearchList(t.list)
                        }
                    }

                    override fun error(e: Throwable) {
                        super.error(e)
                        view.showPageError()
                    }
                })
    }
}
