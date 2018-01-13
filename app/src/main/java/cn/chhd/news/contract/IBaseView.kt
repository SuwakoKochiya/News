package cn.chhd.news.contract

import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.FragmentEvent

/**
 * Created by 葱花滑蛋 on 2018/1/7.
 */

interface IBaseView {

    fun <T> bindToLifecycle(event: ActivityEvent): LifecycleTransformer<T>?

    fun <T> bindToLifecycle(event: FragmentEvent): LifecycleTransformer<T>?

}
