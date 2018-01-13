package cn.chhd.news.contract

import cn.chhd.news.bean.ListData
import cn.chhd.news.bean.NewsArticle
import cn.chhd.news.bean.ResponseData
import cn.chhd.news.bean.WechatArticle
import io.reactivex.Flowable
import io.reactivex.Observable
import retrofit2.http.Query

/**
 * Created by 葱花滑蛋 on 2017/12/4.
 */
interface WechatArticleContract {

    interface View : IPageView {
        fun showWechatArticlelList(list:ArrayList<WechatArticle>)
    }

    interface Model {

        fun getWechatArticleList(appkey: String,
                               channel: String,
                               num: Int,
                               start: Int): Flowable<ListData<WechatArticle>>
    }
}