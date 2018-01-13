package cn.chhd.news.ui.adapter

import cn.chhd.news.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by 葱花滑蛋 on 2018/1/10.
 */
class SearchHistoryAdapter(items: MutableList<String>)
    : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_list_search_history, items) {

    override fun convert(helper: BaseViewHolder, item: String?) {
        helper
                .setText(R.id.tv_name, item)
                .addOnClickListener(R.id.iv_delete)
    }
}