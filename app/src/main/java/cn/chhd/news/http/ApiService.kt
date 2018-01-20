package cn.chhd.news.http

import cn.chhd.news.bean.*
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by 葱花滑蛋 on 2017/12/7.
 */
interface ApiService {

    @GET("news/channel")
    fun getNewsChannelList(@Query("appkey") appkey: String)
            : Flowable<ResponseData<ArrayList<String>>>


    @GET("news/get")
    fun getNewsArticleList(@Query("appkey") appkey: String,
                           @Query("channel") channel: String,
                           @Query("num") num: Int,
                           @Query("start") start: Int)
            : Flowable<ResponseData<ListData<NewsArticle>>>

    @GET("news/search")
    fun getSearchList(@Query("appkey") appkey: String,
                      @Query("keyword") keyword: String)
            : Flowable<ResponseData<SearchData<NewsArticle>>>

    @GET("weixinarticle/channel")
    fun getWechatChannelList(@Query("appkey") appkey: String)
            : Flowable<ResponseData<MutableList<WechatChannel>>>

    @GET("weixinarticle/get")
    fun getWechatArticleList(@Query("appkey") appkey: String,
                             @Query("channelid") id: String,
                             @Query("num") num: Int,
                             @Query("start") start: Int)
            : Flowable<ResponseData<ListData<WechatArticle>>>
}