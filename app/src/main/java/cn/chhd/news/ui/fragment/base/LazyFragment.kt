package cn.chhd.news.ui.fragment.base

import android.os.Bundle


/**
 * Created by 葱花滑蛋 on 2018/1/8.
 */
abstract class LazyFragment : ProgressFragment() {

    protected var mIsVisibleToUser: Boolean = false
    protected var mHasViewCreate: Boolean = false
    protected var mHasLazyLoad: Boolean = false

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        mIsVisibleToUser = isVisibleToUser
        onPreLazyLoad()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mHasViewCreate = true
        onPreLazyLoad()
    }

    override fun onDestroy() {
        mHasViewCreate = false
        mHasLazyLoad = false
        super.onDestroy()
    }

    private fun onPreLazyLoad() {
        if (mIsVisibleToUser && mHasViewCreate && !mHasLazyLoad) {
            mHasLazyLoad = true
            onLazyLoad()
        }
    }

    protected abstract fun onLazyLoad()
}