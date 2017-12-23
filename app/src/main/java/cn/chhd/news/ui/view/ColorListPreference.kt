package cn.chhd.news.ui.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.preference.ListPreference
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckedTextView
import android.widget.ImageView
import cn.chhd.news.R

/**
 * Created by 葱花滑蛋 on 2017/12/19.
 */
class ColorListPreference(context: Context, attrs: AttributeSet) : ListPreference(context, attrs) {

    private val colorStringList = ArrayList<String>()

    init {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.ColorListPreference)
        val colorStrings = typeArray.getTextArray(R.styleable.ColorListPreference_theme_colors)
        for (colorString in colorStrings) {
            colorStringList.add(colorString.toString())
        }
        typeArray.recycle()

        widgetLayoutResource = R.layout.preference_color
    }

    override fun onBindView(view: View?) {
        super.onBindView(view)
        val selectedIndex = findIndexOfValue(value)
        val colorString = colorStringList[selectedIndex]
        val background = view?.findViewById<View>(R.id.view_color)?.background
        (background as GradientDrawable).setColor(Color.parseColor(colorString))
    }

    override fun onPrepareDialogBuilder(builder: AlertDialog.Builder?) {
        builder?.setAdapter(createListAdapter(), this)
        super.onPrepareDialogBuilder(builder)
    }

    private fun createListAdapter(): ColorAdapter {
        val selectedIndex = findIndexOfValue(value)
        return ColorAdapter(context, R.layout.item_list_pref_icon, entries, selectedIndex, colorStringList)
    }

    class ColorAdapter(context: Context,
                       resourceId: Int,
                       stringList: Array<CharSequence>,
                       private val selectedIndex: Int,
                       private val colorStringList: ArrayList<String>)
        : ArrayAdapter<CharSequence>(context, resourceId, stringList) {

        @SuppressLint("ViewHolder")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = LayoutInflater.from(context)
                    .inflate(R.layout.item_list_pref_color, parent, false)

            val tvEntry = view.findViewById<CheckedTextView>(R.id.tv_entry)
            tvEntry.text = getItem(position)
            tvEntry.isChecked = selectedIndex == position

            val viewColor = view.findViewById<View>(R.id.view_color)
            val background = viewColor.background
            (background as GradientDrawable).setColor(Color.parseColor(colorStringList[position]))
            return view
        }
    }
}