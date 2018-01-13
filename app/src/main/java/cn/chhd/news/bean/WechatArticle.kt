package cn.chhd.news.bean

import android.text.TextUtils
import com.chad.library.adapter.base.entity.MultiItemEntity
import java.io.Serializable

/**
 * Created by 葱花滑蛋 on 2018/1/12.
 */
class WechatArticle : MultiItemEntity, Serializable {

    companion object {
        val ITEM_NORMAL = 10
        val ITEM_REFRESH = 11
    }

    var channelid = ""

    var content = ""

    var likenum = ""
        get() = if (field == "") "0" else field

    var pic = ""

    var readnum = ""
        get() = if (field == "") "0" else field

    var time = ""

    var title = ""

    var url = ""

    var weixinaccount = ""

    var weixinname = ""

    var weixinsummary = ""

    override fun getItemType(): Int {
        return if (TextUtils.isEmpty(title)) ITEM_REFRESH else ITEM_NORMAL
    }
}