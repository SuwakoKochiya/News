package cn.chhd.news.model

import cn.chhd.news.bean.ListData
import cn.chhd.news.bean.WechatArticle
import cn.chhd.news.contract.WechatArticleContract
import cn.chhd.news.http.ApiService
import cn.chhd.news.http.RxHttpResponseCompat
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by 葱花滑蛋 on 2017/12/4.
 */

class WechatArticleModel(private val apiService: ApiService) : WechatArticleContract.Model {

    override fun getWechatArticleList(appkey: String, id: String, num: Int, start: Int)
            : Flowable<ListData<WechatArticle>> {
        return apiService.getWechatArticleList(appkey, id, num, start)
                .compose(RxHttpResponseCompat.transform())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}