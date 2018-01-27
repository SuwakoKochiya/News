package cn.chhd.news.ui.activity

import android.content.Intent
import android.os.Bundle
import com.chhd.android.common.ui.activity.FullScreenActivity
import cn.chhd.news.R
import cn.chhd.news.global.App
import cn.chhd.news.http.SimpleSubscriber
import cn.chhd.news.util.SettingsUtils
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.concurrent.TimeUnit

class SplashActivity : FullScreenActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        SettingsUtils.setAppComponentClassName(componentName.className)

        if (App.mInstance.mIsHotStart) {
            goToMainActivity()
        } else {
            App.mInstance.mIsHotStart = true
            countdown()
        }
    }

    private fun countdown() {
        Flowable
                .interval(1, TimeUnit.SECONDS)
                .take(5)
                .map { t: Long ->
                    5 - (t + 1)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SimpleSubscriber<Long>() {

                    override fun before() {
                        super.before()
                        tv_time.text = String.format("广告 %ds", 5)
                    }

                    override fun success(t: Long) {
                        tv_time.text = String.format("广告 %ds", t)
                    }

                    override fun after() {
                        super.after()
                        goToMainActivity()
                    }
                })
    }

    private fun goToMainActivity() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
    }
}
