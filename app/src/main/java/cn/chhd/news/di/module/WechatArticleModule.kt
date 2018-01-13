package cn.chhd.news.di.module

import cn.chhd.news.contract.NewsArticleContract
import cn.chhd.news.contract.WechatArticleContract
import cn.chhd.news.http.ApiService
import cn.chhd.news.model.NewsArticleModel
import cn.chhd.news.model.WechatArticleModel
import dagger.Module
import dagger.Provides

/**
 * Created by 葱花滑蛋 on 2017/12/7.
 */

@Module
class WechatArticleModule(private val view: WechatArticleContract.View) {

    @Provides
    fun provideView(): WechatArticleContract.View {
        return view
    }

    @Provides
    fun provideModel(apiService: ApiService): WechatArticleContract.Model {
        return WechatArticleModel(apiService)
    }
}