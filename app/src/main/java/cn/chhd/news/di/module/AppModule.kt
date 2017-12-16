package cn.chhd.news.di.module

import cn.chhd.news.global.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by congh on 2017/12/4.
 */
@Module
class AppModule(private val app: App) {

    @Singleton
    @Provides
    fun provideApp(): App {
        return app
    }
}