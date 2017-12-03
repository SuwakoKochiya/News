package cn.chhd.news.ui.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.chhd.mylibrary.ui.adapter.FragmentAdapter
import cn.chhd.mylibrary.util.ToastUtils

import cn.chhd.news.R
import com.blankj.utilcode.util.LogUtils
import kotlinx.android.synthetic.main.fragment_news_article.*


class NewsArticleFragment : Fragment() {

    var title: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            title = arguments.getString(FragmentAdapter.KEY_TITLE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_news_article, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        text_view.text = title
        text_view.setOnClickListener {
            ToastUtils.showShort(title)
        }
    }

    companion object {

        fun newInstance(title: String): NewsArticleFragment {
            val fragment = NewsArticleFragment()
            val args = Bundle()
            args.putString(FragmentAdapter.KEY_TITLE, title)
            fragment.arguments = args
            return fragment
        }
    }

}
