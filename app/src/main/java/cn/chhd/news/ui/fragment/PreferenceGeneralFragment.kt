package cn.chhd.news.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.preference.ListPreference
import android.preference.Preference
import android.preference.PreferenceFragment
import android.preference.SwitchPreference
import android.text.format.Formatter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.chhd.mylibrary.util.CacheUtils
import cn.chhd.mylibrary.util.LauncherUtils
import cn.chhd.news.R
import cn.chhd.news.global.Constant
import cn.chhd.news.ui.activity.SettingsActivity
import cn.chhd.news.ui.activity.base.BaseActivity
import cn.chhd.news.util.SettingsUtils
import com.afollestad.materialdialogs.MaterialDialog
import com.blankj.utilcode.util.AppUtils
import com.tencent.bugly.beta.Beta
import de.psdev.licensesdialog.LicensesDialog
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.ResourceSubscriber
import java.util.concurrent.TimeUnit

class PreferenceGeneralFragment : PreferenceFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.pref_general)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater?.inflate(R.layout.preference_list, container, false)!!
    }

    @SuppressLint("ApplySharedPref")
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findPreference(Constant.PREF_NO_PHOTO).setOnPreferenceChangeListener { preference, newValue ->
            preference as SwitchPreference
            newValue as Boolean
            if (preference.isChecked != newValue) {
                SettingsUtils.setNoPhoto(newValue)
            }
            true
        }

        bindPreferenceSummaryToValue(findPreference(Constant.PREF_SLIDE_RETURN))

        findPreference(Constant.PREF_TEXT_SIZE).setOnPreferenceClickListener { preference ->
            SettingsActivity.start(activity, PreferenceTextSizeFragment::class.java.name,
                    preference.title.toString())
            true
        }

        findPreference(Constant.PREF_AUTO_NIGHT_MODE).setOnPreferenceClickListener { preference ->
            SettingsActivity.start(activity, PreferenceAutoNightModeFragment::class.java.name,
                    preference.title.toString())
            true
        }

        findPreference(Constant.PREF_APP_ICON).setOnPreferenceChangeListener { preference, newValue ->
            preference as ListPreference
            newValue as String
            if (preference != newValue) {
                var activeName = ""
                when (newValue) {
                    "0" -> {
                        activeName = "cn.chhd.news.ui.activity.MainActivity"

                    }
                    "1" -> {
                        activeName = "cn.chhd.news.ui.activity.MainActivity-round"
                    }
                }
                if (activeName != SettingsUtils.getAppComponentClassName()) {
                    LauncherUtils.changeLauncherInfo(activeName,
                            SettingsUtils.getAppComponentClassName())
                    SettingsUtils.setAppComponentClassName(activeName)
                    showChangeAppIconDialog()
                }
            }
            true
        }

        findPreference(Constant.PREF_THEME_COLOR).setOnPreferenceChangeListener { preference, newValue ->
            preference as ListPreference
            newValue as String
            if (preference != newValue) {
                when (newValue) {
                    "0" -> {
                        SettingsUtils.setThemeColor(R.style.AppTheme_Red)
                    }
                    "1" -> {
                        SettingsUtils.setThemeColor(R.style.AppTheme_Green)
                    }
                    "2" -> {
                        SettingsUtils.setThemeColor(R.style.AppTheme_Blue)
                    }
                }
                (activity as BaseActivity).reCreateToAll()
            }
            true
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            (findPreference(Constant.PREF_NAVIGATION_BAR) as SwitchPreference).isChecked = false
        }
        findPreference(Constant.PREF_NAVIGATION_BAR).setOnPreferenceChangeListener { preference, newValue ->
            preference as SwitchPreference
            newValue as Boolean
            if (preference.isChecked != newValue) {
                Handler().post {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        (activity as BaseActivity).setNavigationBarColor()
                    } else {
                        if (newValue) {
                            MaterialDialog.Builder(activity)
                                    .content("仅在安卓5.0或以上的版本生效")
                                    .positiveText("确定")
                                    .dismissListener {
                                        (findPreference(Constant.PREF_NAVIGATION_BAR) as SwitchPreference)
                                                .isChecked = false
                                    }
                                    .show()
                        }
                    }
                }
            }
            true
        }

        findPreference(Constant.PREF_CACHE_SIZE).setOnPreferenceClickListener { preference ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val intent = Intent()
                intent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
                intent.data = Uri.fromParts("package", context.packageName, null)
                startActivity(intent)
            } else {
                CacheUtils.freeStorageAndNotify()
                preference.summary = String.format("包括图片、音频和视频缓存 (共%s)", "0KB")
            }
            true
        }

        findPreference(Constant.PREF_LICENSE).setOnPreferenceClickListener {
            LicensesDialog.Builder(activity)
                    .setNotices(R.raw.notices)
                    .setIncludeOwnLicense(true)
                    .build()
                    .show()
            true
        }

        findPreference(Constant.PREF_UPGRADE).summary = String.format("当前版本 %s",
                AppUtils.getAppVersionName())
        findPreference(Constant.PREF_UPGRADE).setOnPreferenceClickListener {
            Beta.checkUpgrade()
            true
        }
    }

    override fun onResume() {
        super.onResume()
        CacheUtils.getPackageSizeInfo { pStats, _ ->
            val formatFileSize = Formatter.formatFileSize(activity, pStats.cacheSize)
            findPreference(Constant.PREF_CACHE_SIZE).summary =
                    String.format("包括图片、音频和视频缓存 (共%s)", formatFileSize)
        }
    }

    @SuppressLint("ApplySharedPref")
    private val onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, newValue ->
        if (preference is ListPreference) {
            newValue as String
            if (preference.value != newValue) {
                val index = preference.findIndexOfValue(newValue)
                preference.setSummary(if (index >= 0) preference.entries[index] else null)

                when (preference.key) {
                    Constant.PREF_SLIDE_RETURN -> {
                        (activity as BaseActivity).reStart()
                    }
                }
            }
        }
        true
    }

    private fun bindPreferenceSummaryToValue(preference: Preference) {
        if (preference is ListPreference) {
            val index = preference.findIndexOfValue(preference.value)
            preference.setSummary(if (index >= 0) preference.entries[index] else null)
        }
        preference.onPreferenceChangeListener = onPreferenceChangeListener
    }

    private fun showChangeAppIconDialog() {
        Flowable.interval(1, TimeUnit.SECONDS)
                .take(10)
                .map { i ->
                    10 - (i + 1)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ResourceSubscriber<Long>() {
                    private lateinit var dialog: MaterialDialog
                    override fun onStart() {
                        super.onStart()
                        dialog = MaterialDialog.Builder(activity)
                                .content("最多等待10秒刷新启动程序...(10s)")
                                .canceledOnTouchOutside(false)
                                .cancelable(false)
                                .dismissListener {
                                    dispose()
                                }
                                .show()
                    }

                    override fun onNext(t: Long?) {
                        if (dialog.isShowing)
                            dialog.setContent(String.format("最多等待10秒刷新启动程序...(%ds)", t?.toInt()))
                    }

                    override fun onComplete() {
                        try {
                            if (dialog.isShowing) {
                                dialog.dismiss()
                            }
                        } catch (e: Exception) {

                        }
                    }

                    override fun onError(t: Throwable?) {
                    }
                })
    }
}