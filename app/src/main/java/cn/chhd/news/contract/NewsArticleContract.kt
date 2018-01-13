package cn.chhd.news.contract

import cn.chhd.news.bean.ListData
import cn.chhd.news.bean.NewsArticle
import cn.chhd.news.bean.ResponseData
import io.reactivex.Flowable
import io.reactivex.Observable
import retrofit2.http.Query

/**
 * Created by 葱花滑蛋 on 2017/12/4.
 */
interface NewsArticleContract {

    interface View : IPageView {
        fun showNewsArticlelList(list:ArrayList<NewsArticle>)
    }

    interface Model {

        fun getNewsArticleList(appkey: String,
                               channel: String,
                               num: Int,
                               start: Int): Flowable<ListData<NewsArticle>>
    }
}