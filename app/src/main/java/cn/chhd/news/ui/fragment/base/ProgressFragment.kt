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

    private val viewList = ArrayList<View>()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_progress, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewList.add(loading)
        viewList.add(empty)
        viewList.add(error)
        viewList.add(content)

        if (getContentResId() != 0)
            LayoutInflater.from(activity).inflate(getContentResId(), content, true)

        btn_retry.setOnClickListener(this)
    }

    abstract fun getContentResId(): Int

    private fun showStatusView(viewId: Int) {
        for (view in viewList) {
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

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_retry -> {
                showLoadingView()
                retry()
            }
        }
    }

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
        showEmptyView()
    }

}