package cn.chhd.news.di.component

import cn.chhd.news.di.AppScope
import cn.chhd.news.di.module.SearchModule
import cn.chhd.news.model.SearchModel
import cn.chhd.news.ui.activity.SearchActivity
import dagger.Component

/**
 * Created by 葱花滑蛋 on 2018/1/11.
 */
@AppScope
@Component(modules = arrayOf(SearchModule::class), dependencies = arrayOf(AppComponent::class))
interface SearchComponent {

    fun inject(activity: SearchActivity)
}