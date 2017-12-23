package cn.chhd.news.ui.adapter

import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import cn.chhd.news.R
import cn.chhd.news.bean.NewsArticle
import cn.chhd.news.util.ImageLoader
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by 葱花滑蛋 on 2017/12/16.
 */

class NewsArticleAdapter(val fragment: Fragment, data: List<NewsArticle>?)
    : BaseMultiItemQuickAdapter<NewsArticle, BaseViewHolder>(data) {

    init {
        addItemType(NewsArticle.ITEM_NORMAL, R.layout.item_list_news_article)
        addItemType(NewsArticle.ITEM_REFRESH, R.layout.item_list_refresh)
    }

    override fun convert(helper: BaseViewHolder, item: NewsArticle) {
        when (helper.itemViewType) {
            NewsArticle.ITEM_NORMAL -> {
                helper
                        .setText(R.id.tv_title, item.title)
                        .setText(R.id.tv_src, item.src)
                        .setText(R.id.tv_time, item.time)

                val visibility = if (TextUtils.isEmpty(item.pic)) View.GONE else View.VISIBLE
                helper.getView<ImageView>(R.id.iv_pic).visibility = visibility

                ImageLoader.instance.with(fragment).load(item.pic).into(helper.getView(R.id.iv_pic))
            }
            NewsArticle.ITEM_REFRESH -> {
            }
        }
    }
}
