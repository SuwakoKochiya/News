package cn.chhd.news.ui.adapter

import cn.chhd.news.R
import cn.chhd.news.bean.NewsArticle
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by 葱花滑蛋 on 2017/12/16.
 */

class NewsArticleAdapter(data: List<NewsArticle>?)
    : BaseQuickAdapter<NewsArticle, BaseViewHolder>(R.layout.item_list_news_article, data) {

    override fun convert(helper: BaseViewHolder, item: NewsArticle) {
        helper
                .setText(R.id.tv_title, item.title)
                .setText(R.id.tv_src, item.src)
                .setText(R.id.tv_time, item.time)

    }
}
