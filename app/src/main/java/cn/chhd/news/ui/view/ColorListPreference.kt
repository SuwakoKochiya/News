package cn.chhd.news.ui.view

import android.content.Context
import android.util.AttributeSet
import cn.chhd.news.R
import com.afollestad.materialdialogs.prefs.MaterialListPreference

/**
 * Created by 葱花滑蛋 on 2017/12/19.
 */
class ColorListPreference(context: Context, attrs: AttributeSet) : MaterialListPreference(context, attrs) {

    private val colorStringList = ArrayList<String>()

    init {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.ColorListPreference)
        val colorStrings = typeArray.getTextArray(R.styleable.ColorListPreference_theme_colors)
        colorStrings.mapTo(colorStringList) { it.toString() }
        typeArray.recycle()

        widgetLayoutResource = R.layout.widget_frame_color
    }
}