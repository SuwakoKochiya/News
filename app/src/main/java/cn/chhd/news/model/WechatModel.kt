package cn.chhd.news.model

import cn.chhd.news.bean.WechatChannel
import cn.chhd.news.contract.WechatContract
import cn.chhd.news.http.ApiService
import cn.chhd.news.http.RxHttpResponseCompat
import cn.chhd.news.model.base.BaseModel
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by 葱花滑蛋 on 2018/1/12.
 */

class WechatModel(apiService: ApiService) : BaseModel(apiService), WechatContract.Model {

    override fun getWechatChannelList(appkey: String): Flowable<MutableList<WechatChannel>> {
        return apiService.getWechatChannelList(appkey)
                .compose(RxHttpResponseCompat.transform())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}
