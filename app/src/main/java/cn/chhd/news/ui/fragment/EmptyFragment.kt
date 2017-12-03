package cn.chhd.news.ui.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import cn.chhd.news.R


class EmptyFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_empty, container, false)
    }

    companion object {

        fun newInstance(): EmptyFragment {
            val fragment = EmptyFragment()
            return fragment
        }
    }
}
