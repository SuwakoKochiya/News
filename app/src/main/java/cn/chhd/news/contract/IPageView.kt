package cn.chhd.news.contract

/**
 * Created by 葱花滑蛋 on 2017/12/12.
 */
interface IPageView {

    fun showPageLoading()

    fun showPageSuccess()

    fun showPageEmpty()

    fun showPageError()

    fun showPageComplete()
}