package cn.chhd.news.contract

import cn.chhd.news.bean.NewsArticle
import cn.chhd.news.bean.SearchData
import io.reactivex.Flowable

/**
 * Created by 葱花滑蛋 on 2018/1/10.
 */
interface SearchContract {

    interface View : IPageView {
        fun showSearchList(list:MutableList<NewsArticle>)
    }

    interface Model {
        fun getSearchList(appkey: String,
                          keyword: String): Flowable<SearchData<NewsArticle>>
    }
}