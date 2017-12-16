package cn.chhd.news.di.component

import cn.chhd.news.di.AppScope
import cn.chhd.news.di.module.NewsModule
import cn.chhd.news.ui.fragment.NewsFragment
import dagger.Component

/**
 * Created by 葱花滑蛋 on 2017/12/12.
 */

@AppScope
@Component(modules = arrayOf(NewsModule::class), dependencies = arrayOf(AppComponent::class))
interface NewsComponent {

    fun inject(fragment: NewsFragment)
}