package cn.chhd.news.util

import android.annotation.SuppressLint
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

    val mConfiguration = ImageLoaderConfiguration()

    private var mActivity: Activity? = null
    private var mFragment: Fragment? = null
    private var mModel: Any? = null
    private var mIsAnimation = true
    private var mPlaceholderId: Int = 0
    private var mErrorId: Int = 0

    fun with(activity: Activity): ImageLoader {
        this.mActivity = activity
        return this
    }

    fun with(fragment: Fragment): ImageLoader {
        this.mFragment = fragment
        return this
    }

    fun load(model: Any?): ImageLoader {
        this.mModel = model
        return this
    }

    fun dontAnimate(): ImageLoader {
        this.mIsAnimation = false
        return this
    }

    fun placeholderId(placeholderId: Int): ImageLoader {
        this.mPlaceholderId = placeholderId
        return this
    }

    fun errorId(errorId: Int): ImageLoader {
        this.mErrorId = errorId
        return this
    }

    fun into(imageView: ImageView) {

        val requestManager = when {
            mFragment != null -> Glide.with(mFragment!!)
            mActivity != null -> Glide.with(mActivity!!)
            else -> Glide.with(imageView)
        }

        val requestBuilder = requestManager.load(mModel)

        if (mPlaceholderId != 0) {
            requestBuilder.apply(RequestOptions.placeholderOf(mPlaceholderId))
        } else if (mConfiguration.getPlaceholderId() != 0) {
            requestBuilder.apply(RequestOptions.placeholderOf(mConfiguration.getPlaceholderId()))
        }
        if (mErrorId != 0) {
            requestBuilder.apply(RequestOptions.errorOf(mErrorId))
        } else if (mConfiguration.getErrorId() != 0) {
            requestBuilder.apply(RequestOptions.errorOf(mConfiguration.getErrorId()))
        }
        if (mConfiguration.isAnimation() && mIsAnimation) {
            requestBuilder.transition(DrawableTransitionOptions.withCrossFade())
        }
        if (mConfiguration.isNoPhoto() && isMobileConnected(imageView.context)) {
            requestBuilder.apply(RequestOptions().onlyRetrieveFromCache(true))
        }

        requestBuilder.into(imageView)

        reset()
    }

    private fun reset() {
        mModel = null
        mPlaceholderId = 0
        mErrorId = 0
        mIsAnimation = true
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

        @SuppressLint("StaticFieldLeak")
        val instance = ImageLoader()
    }
}
