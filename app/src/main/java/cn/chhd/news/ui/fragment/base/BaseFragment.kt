package cn.chhd.news.ui.fragment.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import cn.chhd.news.contract.IBaseView
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.FragmentEvent
import com.trello.rxlifecycle2.components.support.RxFragment

/**
 * Created by congh on 2017/12/3.
 */
open class BaseFragment : RxFragment(), IBaseView {

    override fun <T> bindToLifecycle(event: ActivityEvent): LifecycleTransformer<T>? {
        return null
    }

    override fun <T> bindToLifecycle(event: FragmentEvent): LifecycleTransformer<T>? {
        return bindUntilEvent(event)
    }
}