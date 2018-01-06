package cn.chhd.news.presenter.base

/**
 * Created by 葱花滑蛋 on 2017/12/4.
 */
abstract class BasePresenter<M, V>(private val m: M, private val v: V) {

    abstract fun onDestroy()
}