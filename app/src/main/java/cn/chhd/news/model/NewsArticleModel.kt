package cn.chhd.news.model

import cn.chhd.news.bean.ListData
import cn.chhd.news.bean.NewsArticle
import cn.chhd.news.bean.ResponseData
import cn.chhd.news.contract.NewsArticleContract
import cn.chhd.news.http.ApiService
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by 葱花滑蛋 on 2017/12/4.
 */

class NewsArticleModel(private val apiService: ApiService) : NewsArticleContract.Model {

    override fun getNewsArticlelList(appkey: String, channel: String, num: Int, start: Int)
            : Flowable<ResponseData<ListData<NewsArticle>>> {
        return apiService.getNewsArticlelList(appkey, channel, num, start)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}