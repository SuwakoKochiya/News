package cn.chhd.news.model

import cn.chhd.news.bean.ListData
import cn.chhd.news.bean.NewsArticle
import cn.chhd.news.contract.NewsArticleContract
import cn.chhd.news.http.ApiService
import cn.chhd.news.http.RxHttpResponseCompat
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by 葱花滑蛋 on 2017/12/4.
 */

class NewsArticleModel(private val apiService: ApiService) : NewsArticleContract.Model {

    override fun getNewsArticleList(appkey: String, channel: String, num: Int, start: Int)
            : Flowable<ListData<NewsArticle>> {
        return apiService.getNewsArticleList(appkey, channel, num, start)
                .compose(RxHttpResponseCompat.transform())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}