package cn.chhd.news.presenter

import cn.chhd.news.bean.ListData
import cn.chhd.news.bean.NewsArticle
import cn.chhd.news.bean.WechatArticle
import cn.chhd.news.contract.NewsArticleContract
import cn.chhd.news.contract.WechatArticleContract
import cn.chhd.news.global.Config
import cn.chhd.news.http.SimpleSubscriber
import cn.chhd.news.presenter.base.BasePresenter
import com.trello.rxlifecycle2.android.FragmentEvent
import javax.inject.Inject

/**
 * Created by 葱花滑蛋 on 2017/12/4.
 */
class WechatArticlePresenter
@Inject constructor(private val model: WechatArticleContract.Model, private val view: WechatArticleContract.View)
    : BasePresenter<WechatArticleContract.Model, WechatArticleContract.View>(model, view) {

    fun requestWechatArticleList(id: String,
                               num: Int,
                               start: Int) {
        model.getWechatArticleList(Config.JISU_APP_KEY, id, num, start)
                .compose(view.bindToLifecycle(FragmentEvent.DESTROY))
                .subscribe(object : SimpleSubscriber<ListData<WechatArticle>>(view) {

                    override fun success(t: ListData<WechatArticle>) {
                        if (t.list != null && !t.list?.isEmpty()!!) {
                            view.showWechatArticlelList(t.list!!)
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
