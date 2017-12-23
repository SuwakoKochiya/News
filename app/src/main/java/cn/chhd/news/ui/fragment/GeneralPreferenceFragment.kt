package cn.chhd.news.ui.fragment

import android.os.Bundle
import android.preference.ListPreference
import android.preference.Preference
import android.preference.PreferenceFragment
import android.preference.PreferenceManager
import cn.chhd.news.R
import cn.chhd.news.util.SettingsUtils

/**
 * Created by 葱花滑蛋 on 2017/12/18.
 */
class GeneralPreferenceFragment : PreferenceFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.pref_general)

        findPreference("pref_no_photo").setOnPreferenceChangeListener { preference, newValue ->
            SettingsUtils.setNoPhoto(newValue as Boolean)
            true
        }

        bindPreferenceSummaryToValue(findPreference("pref_slide_return"))
    }

    /**
     * A preference value change listener that updates the preference's summary to reflect its new value.
     */
    private val sBindPreferenceSummaryToValueListener = Preference.OnPreferenceChangeListener { preference, value ->
        val stringValue = value.toString()
        if (preference is ListPreference) {
            // For list preferences, look up the correct display value in the preference's 'entries' list.
            val index = preference.findIndexOfValue(stringValue)
            // Set the summary to reflect the new value.
            preference.setSummary(if (index >= 0) preference.entries[index] else null)

        }
        true
    }

    /**
     * Binds a preference's summary to its value. More specifically, when the preference's value is changed,
     * its summary (line of text below the preference title) is updated to reflect the value. The summary is also
     * immediately updated upon calling this method. The exact display format is dependent on the type of preference.
     */
    private fun bindPreferenceSummaryToValue(preference: Preference) {
        // Set the listener to watch for value changes.
        preference.onPreferenceChangeListener = sBindPreferenceSummaryToValueListener

        // Trigger the listener immediately with the preference's current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager.getDefaultSharedPreferences(preference.context).getString(preference.key, ""))
    }

    companion object {

        fun newInstance(): GeneralPreferenceFragment {
            return GeneralPreferenceFragment()
        }
    }
}