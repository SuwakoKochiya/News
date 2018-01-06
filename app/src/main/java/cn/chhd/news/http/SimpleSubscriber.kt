package cn.chhd.news.http

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.support.v4.app.Fragment
import cn.chhd.news.contract.IPageView
import com.blankj.utilcode.util.LogUtils
import io.reactivex.subscribers.ResourceSubscriber
import java.util.*

/**
 * Created by 葱花滑蛋 on 2017/12/12.
 */
abstract class SimpleSubscriber<T> : ResourceSubscriber<T> {

    private var mStartTimeMillis: Long = 0

    private var mView: IPageView? = null

    constructor()

    constructor(view: IPageView) {
        this.mView = view
    }

    override final fun onStart() {
        super.onStart()
        mStartTimeMillis = System.currentTimeMillis()
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
    }

    override final fun onNext(t: T) {
        delayExcute(NextRun(t))
    }

    private inner class NextRun(private val t: T) : Runnable {

        override fun run() {
            if (t is String) {
                LogUtils.i("json: " + t)
            }
            mView?.showPageSuccess()
            success(t)
        }
    }

    override final fun onComplete() {
        delayExcute(CompleteRun())
    }

    private inner class CompleteRun : Runnable {

        override fun run() {
            mView?.showPageSuccess()
            mView?.showPageComplete()
            after()
        }
    }

    override final fun onError(e: Throwable) {
        delayExcute(ErrorRun(e))
    }

    private inner class ErrorRun(private val e: Throwable) : Runnable {

        override fun run() {
            error(e)
            mView?.showPageComplete()
            after()
        }
    }

    private fun delayExcute(r: Runnable) {
        val timeDif = getTimeDif()
        if (timeDif > delayMillis()) {
            Handler().post(r)
        } else {
            Handler().postDelayed({ Handler().post(r) }, delayMillis() - timeDif)
        }
    }

    private fun getTimeDif(): Long {
        return System.currentTimeMillis() - mStartTimeMillis
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

    protected open fun delayMillis(): Int {
        return 1000
    }
}