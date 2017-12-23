package cn.chhd.news.bean

import android.text.TextUtils
import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * Created by 葱花滑蛋 on 2017/12/16.
 */

class NewsArticle : MultiItemEntity {

    var category: String? = null
    var content: String? = null
    var pic: String? = null
    var src: String? = null
    var time: String? = null
    var title: String? = null
    var url: String? = null
    var weburl: String? = null

    override fun getItemType(): Int {
        return if (TextUtils.isEmpty(title)) ITEM_REFRESH else ITEM_NORMAL
    }

    companion object {

        val ITEM_NORMAL = 10
        val ITEM_REFRESH = 11
    }
}
