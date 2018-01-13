package cn.chhd.news.contract

import cn.chhd.news.bean.ResponseData
import cn.chhd.news.bean.WechatChannel
import io.reactivex.Flowable
import retrofit2.http.Query

/**
 * Created by 葱花滑蛋 on 2018/1/12.
 */

interface WechatContract {

    interface View : IPageView {

        fun showWechatChannelList(list: MutableList<WechatChannel>)
    }

    interface Model {
        fun getWechatChannelList(appkey: String)
                : Flowable<MutableList<WechatChannel>>
    }
}
