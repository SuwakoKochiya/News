package cn.chhd.news.ui.fragment

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceFragment
import android.preference.SwitchPreference
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.chhd.news.R
import cn.chhd.news.global.App
import cn.chhd.news.global.Constant
import cn.chhd.news.ui.activity.base.BaseActivity
import cn.chhd.news.util.SettingsUtils
import java.text.DecimalFormat


class PreferenceAutoNightModeFragment : PreferenceFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.pref_auto_night_mode)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater?.inflate(R.layout.preference_list, container, false)!!
    }

    @SuppressLint("ApplySharedPref")
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findPreference(Constant.PREF_AUTO_NIGHT_MODE_SWITCH).setOnPreferenceChangeListener { preference, newValue ->
            preference as SwitchPreference
            newValue as Boolean
            if (preference.isChecked != newValue) {
                Handler().post {
                    preference.isEnabled = false
                    App.mInstance.initNightMode()
                    Handler().postDelayed({
                        preference.isEnabled = true
                        (activity as BaseActivity).reCreateToAll()
                    }, 500)
                }
            }
            true
        }
    }

    override fun onResume() {
        super.onResume()

        var nightTimes = SettingsUtils.getAutoNightTime()
        val format = DecimalFormat("00")
        findPreference(Constant.PREF_AUTO_NIGHT_MODE_NIGHT).summary = String.format("%1\$s:%2\$s",
                format.format(nightTimes[0]), format.format(nightTimes[1]))
        findPreference(Constant.PREF_AUTO_NIGHT_MODE_NIGHT).setOnPreferenceClickListener {
            TimePickerDialog(activity, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                SettingsUtils.setAutoNightTime(hourOfDay, minute)
                nightTimes = SettingsUtils.getAutoNightTime()
                findPreference(Constant.PREF_AUTO_NIGHT_MODE_NIGHT).summary = String.format("%1\$s:%2\$s",
                        format.format(nightTimes[0]), format.format(nightTimes[1]))
            }, nightTimes[0], nightTimes[1], is24HourMode()).show()
            true
        }

        var dayTimes = SettingsUtils.getAutoDayTime()
        findPreference(Constant.PREF_AUTO_NIGHT_MODE_DAY).summary = String.format("%1\$s:%2\$s",
                format.format(dayTimes[0]), format.format(dayTimes[1]))
        findPreference(Constant.PREF_AUTO_NIGHT_MODE_DAY).setOnPreferenceClickListener {
            TimePickerDialog(activity, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                SettingsUtils.setAutoDayTime(hourOfDay, minute)
                dayTimes = SettingsUtils.getAutoDayTime()
                findPreference(Constant.PREF_AUTO_NIGHT_MODE_DAY).summary = String.format("%1\$s:%2\$s",
                        format.format(dayTimes[0]), format.format(dayTimes[1]))
            }, dayTimes[0], dayTimes[1], is24HourMode()).show()
            true
        }
    }

    private fun is24HourMode(): Boolean {
        val mResolver = activity.contentResolver
        val timeFormat = android.provider.Settings.System.getString(mResolver,
                android.provider.Settings.System.TIME_12_24)
        return timeFormat == "24"
    }
}