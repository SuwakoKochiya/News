package cn.chhd.news.util

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.support.v4.app.Fragment
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

/**
 * Created by 葱花滑蛋 on 2017/12/23.
 */

class ImageLoader private constructor() {

    val configuration = ImageLoaderConfiguration()

    private var activity: Activity? = null
    private var fragment: Fragment? = null
    private var model: Any? = null
    private var isAnimation = true
    private var placeholderId: Int = 0
    private var errorId: Int = 0

    fun with(activity: Activity): ImageLoader {
        this.activity = activity
        return this
    }

    fun with(fragment: Fragment): ImageLoader {
        this.fragment = fragment
        return this
    }

    fun load(model: Any?): ImageLoader {
        this.model = model
        return this
    }

    fun dontAnimate(): ImageLoader {
        this.isAnimation = false
        return this
    }

    fun placeholderId(placeholderId: Int): ImageLoader {
        this.placeholderId = placeholderId
        return this
    }

    fun errorId(errorId: Int): ImageLoader {
        this.errorId = errorId
        return this
    }

    fun into(imageView: ImageView) {

        val requestManager = when {
            fragment != null -> Glide.with(fragment!!)
            activity != null -> Glide.with(activity!!)
            else -> Glide.with(imageView)
        }

        val requestBuilder = requestManager.load(model)

        if (placeholderId != 0) {
            requestBuilder.apply(RequestOptions.placeholderOf(placeholderId))
        } else if (configuration.getPlaceholderId() != 0) {
            requestBuilder.apply(RequestOptions.placeholderOf(configuration!!.getPlaceholderId()))
        }
        if (errorId != 0) {
            requestBuilder.apply(RequestOptions.errorOf(errorId))
        } else if (configuration.getErrorId() != 0) {
            requestBuilder.apply(RequestOptions.errorOf(configuration!!.getErrorId()))
        }
        if (configuration!!.isAnimation() && isAnimation) {
            requestBuilder.transition(DrawableTransitionOptions.withCrossFade())
        }
        if (configuration!!.isNoPhoto() && isMobileConnected(imageView.context)) {
            requestBuilder.apply(RequestOptions().onlyRetrieveFromCache(true))
        }

        requestBuilder.into(imageView)

        reset()
    }

    private fun reset() {
        model = null
        placeholderId = 0
        errorId = 0
        isAnimation = true
    }

    private fun isMobileConnected(context: Context?): Boolean {
        if (context != null) {
            val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = manager.activeNetworkInfo
            if (null != networkInfo && networkInfo.type == ConnectivityManager.TYPE_MOBILE)
                return networkInfo.isAvailable
        }
        return false
    }

    companion object {

        val instance = ImageLoader()
    }
}
