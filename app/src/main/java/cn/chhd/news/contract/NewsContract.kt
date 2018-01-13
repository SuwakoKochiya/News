package cn.chhd.news.contract

import cn.chhd.news.bean.ResponseData
import io.reactivex.Flowable
import io.reactivex.Observable

/**
 * Created by 葱花滑蛋 on 2017/12/12.
 */
interface NewsContract {

    interface View : IPageView {
        fun showNewsChannelList(list: ArrayList<String>)
    }

    interface Model {
        fun getNewsChannelList(appkey: String): Flowable<ArrayList<String>>
    }
}