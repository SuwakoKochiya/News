package cn.chhd.news.ui.adapter

import android.app.Activity
import android.support.v7.widget.PopupMenu
import android.text.TextUtils
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import cn.chhd.news.R
import cn.chhd.news.bean.NewsArticle
import cn.chhd.news.global.Constant
import cn.chhd.news.ui.activity.ImageActivity
import cn.chhd.news.util.ImageLoader
import cn.chhd.news.util.SettingsUtils
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by 葱花滑蛋 on 2017/12/16.
 */

class NewsArticleAdapter(data: List<NewsArticle>?)
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

                helper.getView<TextView>(R.id.tv_title).setTextSize(TypedValue.COMPLEX_UNIT_SP,
                        Constant.TEXT_SIZE_NORMAL + SettingsUtils.getTextSize())
                helper.getView<TextView>(R.id.tv_src).setTextSize(TypedValue.COMPLEX_UNIT_SP,
                        Constant.TEXT_SIZE_SMALL + SettingsUtils.getTextSize())
                helper.getView<TextView>(R.id.tv_comment).setTextSize(TypedValue.COMPLEX_UNIT_SP,
                        Constant.TEXT_SIZE_SMALL + SettingsUtils.getTextSize())
                helper.getView<TextView>(R.id.tv_time).setTextSize(TypedValue.COMPLEX_UNIT_SP,
                        Constant.TEXT_SIZE_SMALL + SettingsUtils.getTextSize())

                val visibility = if (TextUtils.isEmpty(item.pic)) View.GONE else View.VISIBLE
                helper.getView<ImageView>(R.id.iv_pic).visibility = visibility

                ImageLoader.instance.with(mContext as Activity).load(item.pic)
                        .into(helper.getView(R.id.iv_pic))

                helper.getView<ImageView>(R.id.iv_pic).setOnClickListener {
                    ImageActivity.start(mContext, item.pic)
                }

                helper.getView<ImageView>(R.id.iv_more).setOnClickListener { v ->
                    val popupMenu = PopupMenu(mContext, v, Gravity.END, 0, R.style.PopupMenuStyle)
                    popupMenu.menuInflater.inflate(R.menu.popup_menu_new_acticle, popupMenu.menu)
                    popupMenu.setOnMenuItemClickListener {
                        remove(helper.adapterPosition)
                        false
                    }
                    popupMenu.show()
                }
            }
        }
    }
}
