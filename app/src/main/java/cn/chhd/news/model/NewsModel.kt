package cn.chhd.news.model

import cn.chhd.news.bean.ResponseData
import cn.chhd.news.contract.NewsContract
import cn.chhd.news.http.ApiService
import cn.chhd.news.model.base.BaseModel
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by 葱花滑蛋 on 2017/12/12.
 */
class NewsModel(apiService: ApiService) : BaseModel(apiService), NewsContract.Model {

    override fun getNewsChannelList(appkey: String): Flowable<ResponseData<ArrayList<String>>> {
        return apiService.getNewsChannelList(appkey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}