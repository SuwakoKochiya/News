package cn.chhd.news.http

import cn.chhd.news.bean.ListData
import cn.chhd.news.bean.NewsArticle
import cn.chhd.news.bean.ResponseData
import io.reactivex.Flowable
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by 葱花滑蛋 on 2017/12/7.
 */
interface ApiService {

    @GET("news/channel")
    fun getNewsChannelList(@Query("appkey") appkey: String): Flowable<ResponseData<ArrayList<String>>>


    @GET("news/get")
    fun getNewsArticlelList(@Query("appkey") appkey: String,
                            @Query("channel") channel: String,
                            @Query("num") num: Int,
                            @Query("start") start: Int): Flowable<ResponseData<ListData<NewsArticle>>>
}