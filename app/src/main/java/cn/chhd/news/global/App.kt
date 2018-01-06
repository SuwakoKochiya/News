package cn.chhd.news.global

import android.support.v7.app.AppCompatDelegate
import cn.chhd.mylibrary.global.BaseApplication
import cn.chhd.news.BuildConfig
import cn.chhd.news.R
import cn.chhd.news.di.component.AppComponent
import cn.chhd.news.di.component.DaggerAppComponent
import cn.chhd.news.di.module.AppModule
import cn.chhd.news.di.module.HttpModule
import cn.chhd.news.util.ImageLoader
import cn.chhd.news.util.SettingsUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import com.facebook.stetho.Stetho
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import com.tencent.bugly.crashreport.CrashReport
import java.util.*

/**
 * Created by congh on 2017/11/26.
 */
class App : BaseApplication() {

    val mComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .httpModule(HttpModule())
                .build()
    }

    override fun onCreate() {
        super.onCreate()

        mInstance = this

        Utils.init(this)
        initLog()

        Stetho.initializeWithDefaults(this)

        ImageLoader.instance.mConfiguration.setNoPhoto(SettingsUtils.isNoPhoto())

        initNightMode()

        if (!BuildConfig.DEBUG) {
            initBugly()
        }
    }

    private fun initBugly() {

        CrashReport.setIsDevelopmentDevice(this, BuildConfig.DEBUG)

        // 设置状态栏小图标，smallIconId为项目中的图片资源id;
        Beta.smallIconId = R.drawable.ic_cloud_download_white_18dp
        // 设置通知栏大图标，largeIconId为项目中的图片资源；
        Beta.largeIconId = R.mipmap.ic_launcher

        /*
         * true表示初始化时自动检查升级
         * false表示不会自动检查升级，需要手动调用Beta.checkUpgrade()方法
         */
        Beta.autoCheckUpgrade = false

        /*参数1：上下文对象

        参数2：注册时申请的APPID

        参数3：是否开启debug模式，true表示打开debug模式，false表示关闭调试模式

        提示：已经接入Bugly用户改用上面的初始化方法,不影响原有的crash上报功能; init方法会自动检测更新，不需要再手动调用Beta.checkUpgrade(), 如需增加自动检查时机可以使用Beta.checkUpgrade(false,false);

        参数1：isManual 用户手动点击检查，非用户点击操作请传false

        参数2：isSilence 是否显示弹窗等交互，[true:没有弹窗和toast] [false:有弹窗或toast]*/
        Bugly.init(getApplication(), "762d5981d0", BuildConfig.DEBUG)
    }

    public fun initNightMode() {
        if (SettingsUtils.isAutoNightMode()) {
            val currentTimeMillis = System.currentTimeMillis()
            if (currentTimeMillis in getDayMillis()..(getNightMillis() - 1)) { // 日间模式
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else { // 夜间模式
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        } else {
            val mode = if (SettingsUtils.isNightMode()) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
            AppCompatDelegate.setDefaultNightMode(mode)
        }
    }

    private fun getNightMillis(): Long {
        val nightTimes = SettingsUtils.getAutoNightTime()
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, nightTimes[0])
        cal.set(Calendar.MINUTE, nightTimes[1])
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        return cal.timeInMillis
    }

    private fun getDayMillis(): Long {
        val dayTimes = SettingsUtils.getAutoDayTime()
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, dayTimes[0])
        cal.set(Calendar.MINUTE, dayTimes[1])
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        return cal.timeInMillis
    }

    private fun initLog() {
        LogUtils.getConfig()
                .setLogSwitch(true)// 设置 log 总开关，包括输出到控制台和文件，默认开
                .setConsoleSwitch(true)// 设置是否输出到控制台开关，默认开
                .setGlobalTag("debug-app")// 设置 log 全局标签，默认为空
                // 当全局标签不为空时，我们输出的 log 全部为该 tag，
                // 为空时，如果传入的 tag 为空那就显示类名，否则显示 tag
                .setLogHeadSwitch(true)// 设置 log 头信息开关，默认为开
                .setLog2FileSwitch(false)// 打印 log 时是否存到文件的开关，默认关
                .setDir("")// 当自定义路径为空时，写入应用的/cache/log/目录中
                .setFilePrefix("")// 当文件前缀为空时，默认为"util"，即写入文件为"util-MM-dd.txt"
                .setBorderSwitch(true)// 输出日志是否带边框开关，默认开
                .setConsoleFilter(LogUtils.V)// log 的控制台过滤器，和 logcat 过滤器同理，默认 Verbose
                .setFileFilter(LogUtils.V)// log 文件过滤器，和 logcat 过滤器同理，默认 Verbose
                .setStackDeep(1)// log 栈深度，默认为 1
    }

    companion object {
        lateinit var mInstance: App
    }
}