package cn.chhd.news.util

/**
 * Created by 葱花滑蛋 on 2017/12/23.
 */

class ImageLoaderConfiguration {

    private var mPlaceholderId: Int = 0
    private var mErrorId: Int = 0
    private var mIsAnimation: Boolean = true
    private var mIsNoPhoto: Boolean = false

    fun setPlaceholderId(placeholderId: Int): ImageLoaderConfiguration {
        this.mPlaceholderId = placeholderId
        return this
    }

    fun setErrorId(errorId: Int): ImageLoaderConfiguration {
        this.mErrorId = errorId
        return this
    }

    fun setAnimation(animation: Boolean): ImageLoaderConfiguration {
        mIsAnimation = animation
        return this
    }

    fun setNoPhoto(noPhoto: Boolean): ImageLoaderConfiguration {
        mIsNoPhoto = noPhoto
        return this
    }

    fun getPlaceholderId(): Int {
        return mPlaceholderId
    }

    fun getErrorId(): Int {
        return mErrorId
    }

    fun isAnimation(): Boolean {
        return mIsAnimation
    }

    fun isNoPhoto(): Boolean {
        return mIsNoPhoto
    }
}
