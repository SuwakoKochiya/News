package cn.chhd.news.bean

/**
 * Created by 葱花滑蛋 on 2018/1/10.
 */
open class SearchData<T>(
        var keyword: String = "",
        var list: MutableList<T> = ArrayList(),
        var num: String = "")