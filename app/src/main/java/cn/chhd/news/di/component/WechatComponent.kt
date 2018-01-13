package cn.chhd.news.di.component

import cn.chhd.news.di.AppScope
import cn.chhd.news.di.module.WechatModule
import cn.chhd.news.ui.fragment.WechatFragment
import dagger.Component

/**
 * Created by 葱花滑蛋 on 2018/1/12.
 */
@AppScope
@Component(modules = arrayOf(WechatModule::class), dependencies = arrayOf(AppComponent::class))
interface WechatComponent {

    fun inject(fragment: WechatFragment)
}