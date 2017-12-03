package cn.chhd.news.ui.listener

/**
 * Created by congh on 2017/12/2.
 */
interface OnNewsChannelChangeListener {

    fun onItemMove(starPos: Int, endPos: Int)

    fun onMoveToMyChannel(starPos: Int, endPos: Int)

    fun onMoveToOtherChannel(starPos: Int, endPos: Int)

}