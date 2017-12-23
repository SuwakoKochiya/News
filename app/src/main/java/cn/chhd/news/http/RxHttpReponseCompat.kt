package cn.chhd.news.http

import org.reactivestreams.Publisher

import cn.chhd.news.bean.ResponseData
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.Observable
import io.reactivex.functions.Function

/**
 * Created by 葱花滑蛋 on 2017/12/16.
 */

object RxHttpReponseCompat {

    fun <T> transform(): FlowableTransformer<ResponseData<T>, T> {
        return FlowableTransformer { upstream ->
            upstream.flatMap { responseData ->
                if (responseData.isSuccess) {
                    Flowable.just(responseData.result!!)
                } else {
                    Flowable.error(ApiException(responseData.status!!, responseData.msg!!))
                }
            }
        }
    }
}
