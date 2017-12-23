package cn.chhd.news.http

import java.lang.Exception

/**
 * Created by 葱花滑蛋 on 2017/12/16.
 */
class ApiException : Exception {

    var status: String? = null
    var msg: String? = null

    constructor()

    constructor(status: String, msg: String)
}