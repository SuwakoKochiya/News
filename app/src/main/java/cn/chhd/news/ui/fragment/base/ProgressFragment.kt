package cn.chhd.news.ui.fragment.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.chhd.news.R
import cn.chhd.news.contract.IPageView
import kotlinx.android.synthetic.main.content_progress.*
import kotlinx.android.synthetic.main.fragment_progress.*

/**
 * Created by 葱花滑蛋 on 2017/12/8.
 */
abstract class ProgressFragment : BaseFragment(), IPageView, View.OnClickListener {

    protected val mViewList = ArrayList<View>()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_progress, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewList.add(loading)
        mViewList.add(empty)
        mViewList.add(error)
        mViewList.add(content)

        if (getContentResId() != 0)
            LayoutInflater.from(activity).inflate(getContentResId(), content, true)

        btn_refresh.setOnClickListener(this)
        btn_retry.setOnClickListener(this)
    }

    private fun showStatusView(viewId: Int) {
        for (view in mViewList) {
            if (view.id == viewId) {
                view.visibility = View.VISIBLE
            } else {
                view.visibility = View.INVISIBLE
            }
        }
    }


    protected fun showLoadingView() {
        showStatusView(R.id.loading)
    }

    protected fun showEmptyView() {
        showStatusView(R.id.empty)
    }

    protected fun showErrorView() {
        showStatusView(R.id.error)
    }

    protected fun showContentView() {
        showStatusView(R.id.content)
    }

    abstract fun getContentResId(): Int

    abstract fun retry()

    override fun showPageLoading() {
        showLoadingView()
    }

    override fun showPageSuccess() {
        showContentView()
    }

    override fun showPageEmpty() {
        showEmptyView()
    }

    override fun showPageError() {
        showErrorView()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_refresh, R.id.btn_retry -> {
                showLoadingView()
                retry()
            }
        }
    }
}