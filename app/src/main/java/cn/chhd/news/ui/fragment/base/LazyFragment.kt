package cn.chhd.news.ui.fragment.base

import android.os.Bundle
import android.view.View
import cn.chhd.mylibrary.ui.adapter.FragmentAdapter
import com.blankj.utilcode.util.LogUtils


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
        preLazyLoad()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mHasViewCreate = true
        preLazyLoad()
    }

    override fun onDestroy() {
        mHasViewCreate = false
        mHasLazyLoad = false
        super.onDestroy()
    }

    private fun preLazyLoad() {
        if (mIsVisibleToUser && mHasViewCreate && !mHasLazyLoad) {
            mHasLazyLoad = true
            lazyLoad()
        }
    }

    protected abstract fun lazyLoad()
}