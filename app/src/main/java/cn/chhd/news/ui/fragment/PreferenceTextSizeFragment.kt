package cn.chhd.news.ui.fragment

import android.os.Bundle
import android.preference.PreferenceFragment
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chhd.android.common.util.SlideHelper
import cn.chhd.news.R
import cn.chhd.news.global.Constant
import cn.chhd.news.util.SettingsUtils
import com.jakewharton.rxbinding2.widget.RxSeekBar
import kotlinx.android.synthetic.main.fragment_preference_text_size.*

class PreferenceTextSizeFragment : PreferenceFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater?.inflate(R.layout.fragment_preference_text_size, container, false)!!
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        seek_bak.progress = (SettingsUtils.getTextSize() + 2).toInt()
        RxSeekBar.changes(seek_bak).subscribe({ progress ->
            val value = (progress - 2).toFloat()
            SettingsUtils.setTextSize(value)
            text_view.setTextSize(TypedValue.COMPLEX_UNIT_SP,
                    Constant.TEXT_SIZE_NORMAL + SettingsUtils.getTextSize())
        })
        SlideHelper.requestParentDisallowInterceptTouchEvent(seek_bak)
    }
}