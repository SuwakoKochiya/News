package cn.chhd.news.util

/**
 * Created by 葱花滑蛋 on 2017/12/23.
 */

class ImageLoaderConfiguration {

    private var placeholderId: Int = 0
    private var errorId: Int = 0
    private var isAnimation: Boolean = true
    private var isNoPhoto: Boolean = false

    fun setPlaceholderId(placeholderId: Int): ImageLoaderConfiguration {
        this.placeholderId = placeholderId
        return this
    }

    fun setErrorId(errorId: Int): ImageLoaderConfiguration {
        this.errorId = errorId
        return this
    }

    fun setAnimation(animation: Boolean): ImageLoaderConfiguration {
        isAnimation = animation
        return this
    }

    fun setNoPhoto(noPhoto: Boolean): ImageLoaderConfiguration {
        isNoPhoto = noPhoto
        return this
    }

    fun getPlaceholderId(): Int {
        return placeholderId
    }

    fun getErrorId(): Int {
        return errorId
    }

    fun isAnimation(): Boolean {
        return isAnimation
    }

    fun isNoPhoto(): Boolean {
        return isNoPhoto
    }
}
