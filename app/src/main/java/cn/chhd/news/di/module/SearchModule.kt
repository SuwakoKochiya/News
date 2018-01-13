package cn.chhd.news.di.module

import cn.chhd.news.contract.SearchContract
import cn.chhd.news.http.ApiService
import cn.chhd.news.model.SearchModel
import dagger.Module
import dagger.Provides

/**
 * Created by 葱花滑蛋 on 2018/1/11.
 */
@Module
class SearchModule(private val view: SearchContract.View){

    @Provides
    fun provideView(): SearchContract.View {
        return view
    }

    @Provides
    fun provideModel(apiService: ApiService): SearchContract.Model {
        return SearchModel(apiService)
    }
}