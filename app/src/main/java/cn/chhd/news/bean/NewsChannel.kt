package cn.chhd.news.bean

import android.text.TextUtils
import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * Created by congh on 2017/11/26.
 */

class NewsChannel : MultiItemEntity {

    var title: String? = null

    var channelName: String? = null
    var channelId: String? = null
    var isEnable: Boolean = false
    var position: Int = 0

    constructor()

    constructor(title: String, isEnable: Boolean) {
        this.title = title
        this.isEnable = isEnable
    }

    constructor(channelName: String?, channelId: String?, isEnable: Boolean, position: Int) {
        this.channelName = channelName
        this.channelId = channelId
        this.isEnable = isEnable
        this.position = position
    }

    override fun getItemType(): Int {
        when {
            !TextUtils.isEmpty(title) && isEnable -> {
                return TYPE_TITLE_MY
            }
            !TextUtils.isEmpty(title) && !isEnable -> {
                return TYPE_TITLE_OTHER
            }
            TextUtils.isEmpty(title) && isEnable -> {
                return TYPE_CHANNEL_MY
            }
            TextUtils.isEmpty(title) && !isEnable -> {
                return TYPE_CHANNEL_OTHER
            }
        }
        return TYPE_CHANNEL_MY
    }

    companion object {
        val TYPE_TITLE_MY = 1
        val TYPE_TITLE_OTHER = 2
        val TYPE_CHANNEL_MY = 3
        val TYPE_CHANNEL_OTHER = 4
    }
}
