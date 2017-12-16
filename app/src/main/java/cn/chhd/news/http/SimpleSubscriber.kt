package cn.chhd.news.http

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.support.v4.app.Fragment
import cn.chhd.news.bean.ResponseData
import cn.chhd.news.contract.IPageView
import com.blankj.utilcode.util.LogUtils
import io.reactivex.subscribers.ResourceSubscriber

/**
 * Created by 葱花滑蛋 on 2017/12/12.
 */
open abstract class SimpleSubscriber<T> : ResourceSubscriber<ResponseData<T>> {

    private val delayMillis = 2000
    private var startTimeMillis: Long = 0

    private var view: IPageView? = null

    constructor()

    constructor(view: IPageView) {
        this.view = view
    }

    override final fun onStart() {
        super.onStart()
        startTimeMillis = System.currentTimeMillis()
        if (isShowDialog() && view != null) {
            var context: Context? = null
            if (view is Fragment) {
                context = (view as Fragment).activity
            } else if (view is Activity) {
                context = view as Activity
            }
            if (context != null) {
            }
        }
    }

    override final fun onNext(t: ResponseData<T>) {
        delayExcute(NextRun(t))
    }

    private inner class NextRun(private val t: ResponseData<T>) : Runnable {

        override fun run() {
            if (t.result is String) {
                LogUtils.i("json: " + t)
            }
            view?.showPageSuccess()
            t.result?.let {
                success(it)
            }
        }
    }

    override final fun onComplete() {
        after()
    }

    override final fun onError(e: Throwable) {
        delayExcute(ErrorRun(e))
    }

    private inner class ErrorRun(private val e: Throwable) : Runnable {

        override fun run() {
            view?.showPageError()
            error(e)
            after()
        }
    }

    private fun delayExcute(r: Runnable) {
        val timeDif = getTimeDif()
        if (timeDif > delayMillis) {
            Handler().post(r)
        } else {
            Handler().postDelayed({ Handler().post(r) }, delayMillis - timeDif)
        }
    }

    private fun getTimeDif(): Long {
        return System.currentTimeMillis() - startTimeMillis
    }

    protected fun before() {

    }

    abstract fun success(t: T)

    protected fun error(e: Throwable) {

    }

    protected fun after() {

    }

    protected fun isShowDialog(): Boolean {
        return false
    }
}