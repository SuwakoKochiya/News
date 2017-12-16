package cn.chhd.news.bean

/**
 * Created by 葱花滑蛋 on 2017/12/12.
 */
class ResponseData<T> {

    /**
     * status : 0
     * msg : ok
     * result : ["头条","新闻","财经","体育","娱乐","军事","教育","科技","NBA","股票","星座","女性","健康","育儿"]
     */

    var status: String? = null
    var msg: String? = null
    var result: T? = null
}