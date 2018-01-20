package cn.chhd.news.http

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import cn.chhd.news.contract.IPageView
import com.blankj.utilcode.util.LogUtils
import io.reactivex.subscribers.ResourceSubscriber

/**
 * Created by 葱花滑蛋 on 2017/12/12.
 */
abstract class SimpleSubscriber<T> : ResourceSubscriber<T> {

    private var mView: IPageView? = null

    constructor()

    constructor(view: IPageView) {
        this.mView = view
    }

    override final fun onStart() {
        super.onStart()
        if (isShowDialog() && mView != null) {
            var context: Context? = null
            if (mView is Fragment) {
                context = (mView as Fragment).activity
            } else if (mView is Activity) {
                context = mView as Activity
            }
            if (context != null) {
            }
        }
        before()
    }

    override final fun onNext(t: T) {
        if (t is String) {
            LogUtils.i("json: " + t)
        }
        mView?.showPageSuccess()
        success(t)
    }

    override final fun onComplete() {
        mView?.showPageSuccess()
        mView?.showPageComplete()
        after()
    }

    override final fun onError(e: Throwable) {
        mView?.showPageComplete()
        error(e)
        after()
    }

    protected open fun before() {

    }

    abstract fun success(t: T)

    protected open fun error(e: Throwable) {

    }

    protected open fun after() {
    }

    protected open fun isShowDialog(): Boolean {
        return false
    }
}