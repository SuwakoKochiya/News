package cn.chhd.news.bean

import android.text.TextUtils
import com.chad.library.adapter.base.entity.MultiItemEntity
import java.io.Serializable

/**
 * Created by 葱花滑蛋 on 2017/12/16.
 */

class NewsArticle : MultiItemEntity, Serializable {

    companion object {
        val ITEM_NORMAL = 10
        val ITEM_REFRESH = 11
    }

    var category = ""
    var content = ""
    var pic = ""
    var src = ""
    var time = ""
    var title = ""
    var url = ""
    var weburl = ""

    override fun getItemType(): Int {
        return if (TextUtils.isEmpty(title)) ITEM_REFRESH else ITEM_NORMAL
    }
}
