package cn.chhd.news.di.module

import cn.chhd.news.contract.NewsArticleContract
import cn.chhd.news.http.ApiService
import cn.chhd.news.model.NewsArticleModel
import dagger.Module
import dagger.Provides

/**
 * Created by 葱花滑蛋 on 2017/12/7.
 */

@Module
class NewsArticleModule(private val view: NewsArticleContract.View) {

    @Provides
    fun provideView(): NewsArticleContract.View {
        return view
    }

    @Provides
    fun provideModel(apiService: ApiService): NewsArticleContract.Model {
        return NewsArticleModel(apiService)
    }
}