package cn.chhd.news.ui.view

import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.preference.ListPreference
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import android.widget.ImageView
import android.widget.TextView
import cn.chhd.news.R
import cn.chhd.news.ui.listener.OnItemClickListener
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.prefs.MaterialListPreference
import com.blankj.utilcode.util.LogUtils
import java.util.ArrayList

/**
 * Created by 葱花滑蛋 on 2017/12/19.
 */
class IconListPreference(context: Context, attrs: AttributeSet) : MaterialListPreference(context, attrs) {

    private val drawableList = ArrayList<Drawable>()

    init {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.IconListPreference)
        val drawables = typeArray.getTextArray(R.styleable.IconListPreference_app_icons)
        drawables
                .map { context.resources.getIdentifier(it.toString(), "mipmap", context.packageName) }
                .mapTo(drawableList) { ContextCompat.getDrawable(context,it) }
        typeArray.recycle()

        widgetLayoutResource = R.layout.widget_frame_icon
    }

    override fun onBindView(view: View?) {
        super.onBindView(view)
        val selectedIndex = findIndexOfValue(value)
        val drawable = drawableList[selectedIndex]
        (view?.findViewById<ImageView>(R.id.iv_icon) as ImageView).setImageDrawable(drawable)
    }

}