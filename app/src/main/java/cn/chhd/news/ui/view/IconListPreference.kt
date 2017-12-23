package cn.chhd.news.ui.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.preference.ListPreference
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckedTextView
import android.widget.ImageView
import android.widget.TextView
import cn.chhd.news.R
import java.util.ArrayList

/**
 * Created by 葱花滑蛋 on 2017/12/19.
 */
class IconListPreference(context: Context, attrs: AttributeSet) : ListPreference(context, attrs) {

    private val drawableList = ArrayList<Drawable>()

    init {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.IconListPreference)
        val drawables = typeArray.getTextArray(R.styleable.IconListPreference_app_icons)
        drawables
                .map { context.resources.getIdentifier(it.toString(), "mipmap", context.packageName) }
                .mapTo(drawableList) { context.resources.getDrawable(it) }
        typeArray.recycle()

        widgetLayoutResource = R.layout.preference_icon
    }

    override fun onBindView(view: View?) {
        super.onBindView(view)
        val selectedIndex = findIndexOfValue(value)
        val drawable = drawableList[selectedIndex]
        (view?.findViewById<ImageView>(R.id.iv_icon) as ImageView).setImageDrawable(drawable)
    }

    override fun onPrepareDialogBuilder(builder: AlertDialog.Builder?) {
        builder?.setAdapter(createListAdapter(), this)
        super.onPrepareDialogBuilder(builder)
    }

    private fun createListAdapter(): IconAdapter {
        val selectedIndex = findIndexOfValue(value)
        return IconAdapter(context, R.layout.item_list_pref_icon, entries, selectedIndex, drawableList)
    }

    class IconAdapter(context: Context,
                      resourceId: Int,
                      stringList: Array<CharSequence>,
                      private val selectedIndex: Int,
                      private val drawableList: ArrayList<Drawable>)
        : ArrayAdapter<CharSequence>(context, resourceId, stringList) {

        @SuppressLint("ViewHolder")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = LayoutInflater.from(context)
                    .inflate(R.layout.item_list_pref_icon, parent, false)

            val tvEntry = view.findViewById<CheckedTextView>(R.id.tv_entry)
            tvEntry.text = getItem(position)
            tvEntry.isChecked = selectedIndex == position

            val ivIcon = view.findViewById<ImageView>(R.id.iv_icon)
            ivIcon.setImageDrawable(drawableList[position])
            return view
        }
    }
}