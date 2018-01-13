package cn.chhd.news.model

import cn.chhd.news.bean.NewsArticle
import cn.chhd.news.bean.SearchData
import cn.chhd.news.contract.SearchContract
import cn.chhd.news.http.ApiService
import cn.chhd.news.http.RxHttpReponseCompat
import cn.chhd.news.model.base.BaseModel
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by 葱花滑蛋 on 2018/1/10.
 */
class SearchModel(apiService: ApiService) : BaseModel(apiService), SearchContract.Model {

    override fun getSearchList(appkey: String, keyword: String): Flowable<SearchData<NewsArticle>> {
        return apiService.getSearchList(appkey, keyword)
                .compose(RxHttpReponseCompat.transform())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}