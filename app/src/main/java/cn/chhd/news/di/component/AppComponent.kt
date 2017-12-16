package cn.chhd.news.di.component

import cn.chhd.news.di.module.AppModule
import cn.chhd.news.di.module.HttpModule
import cn.chhd.news.global.App
import cn.chhd.news.http.ApiService
import dagger.Component
import javax.inject.Singleton

/**
 * Created by 葱花滑蛋 on 2017/12/4.
 */

@Singleton
@Component(modules = arrayOf(AppModule::class, HttpModule::class))
interface AppComponent {

    fun getApp(): App

    fun getApiService(): ApiService
}