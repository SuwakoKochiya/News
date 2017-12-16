package cn.chhd.news.di.module

import cn.chhd.news.contract.NewsContract
import cn.chhd.news.http.ApiService
import cn.chhd.news.model.NewsModel
import dagger.Module
import dagger.Provides

/**
 * Created by 葱花滑蛋 on 2017/12/12.
 */

@Module
class NewsModule(private val view: NewsContract.View) {

    @Provides
    fun provideView(): NewsContract.View {
        return view
    }

    @Provides
    fun provideModel(apiService: ApiService): NewsContract.Model {
        return NewsModel(apiService)
    }
}