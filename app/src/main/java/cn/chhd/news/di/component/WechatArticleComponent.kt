package cn.chhd.news.di.component

import cn.chhd.news.di.AppScope
import cn.chhd.news.di.module.NewsArticleModule
import cn.chhd.news.di.module.WechatArticleModule
import cn.chhd.news.ui.fragment.NewsArticleFragment
import cn.chhd.news.ui.fragment.WechatArticleFragment
import dagger.Component

/**
 * Created by 葱花滑蛋 on 2017/12/7.
 */

@AppScope
@Component(modules = arrayOf(WechatArticleModule::class), dependencies = arrayOf(AppComponent::class))
interface WechatArticleComponent {

    fun inject(fragment: WechatArticleFragment)
}