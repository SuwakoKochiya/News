package cn.chhd.news.di.module

import cn.chhd.news.contract.WechatContract
import cn.chhd.news.http.ApiService
import cn.chhd.news.model.WechatModel
import dagger.Module
import dagger.Provides

/**
 * Created by 葱花滑蛋 on 2018/1/12.
 */
@Module
class WechatModule(private val view: WechatContract.View) {

    @Provides
    fun provideView(): WechatContract.View {
        return view
    }

    @Provides
    fun provideModel(apiService: ApiService): WechatContract.Model {
        return WechatModel(apiService)
    }
}