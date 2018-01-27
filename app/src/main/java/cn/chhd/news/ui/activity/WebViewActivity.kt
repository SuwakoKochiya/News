package cn.chhd.news.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import com.chhd.android.common.util.ToastUtils
import cn.chhd.news.R
import cn.chhd.news.global.Constant
import cn.chhd.news.ui.activity.base.ToolbarActivity
import cn.chhd.news.util.SettingsUtils
import cn.sharesdk.framework.Platform
import cn.sharesdk.framework.PlatformActionListener
import cn.sharesdk.onekeyshare.OnekeyShare
import com.afollestad.materialdialogs.MaterialDialog
import com.blankj.utilcode.util.LogUtils
import kotlinx.android.synthetic.main.activity_web_view.*
import java.util.*

class WebViewActivity : ToolbarActivity() {

    companion object {
        fun start(context: Context, url: String) {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra("url", url)
            context.startActivity(intent)
        }
    }

    private lateinit var mUrl: String
    private lateinit var mToolbatTitle: TextView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mUrl = intent.getStringExtra("url") ?: "http://m.baidu.com"

        mToolbatTitle = View.inflate(this, R.layout.toolbar_title, null) as TextView
        setToolbarContainer(mToolbatTitle)

        swipe_refresh_layout.setProgressBackgroundColorSchemeResource(R.color.color_background_light)
        swipe_refresh_layout.setColorSchemeResources(*Constant.SWIPE_REFRESH_LAYOUT_COLORS)
        swipe_refresh_layout.setOnRefreshListener {
            swipe_refresh_layout.isRefreshing = false
            web_view.loadUrl(mUrl)
        }

        val settings = web_view.settings
        settings.javaScriptEnabled = true
        settings.textZoom = SettingsUtils.getWebViewTextSize()
        settings.blockNetworkImage = SettingsUtils.isNoPhoto()
        web_view.webViewClient = webViewClient
        web_view.webChromeClient = webChromeClient
        web_view.setBackgroundColor(ContextCompat.getColor(this, R.color.color_background))
        web_view.loadUrl(mUrl)
    }

    override fun getContainerResId(): Int {
        return R.layout.activity_web_view
    }

    private val webViewClient = object : WebViewClient() {

    }

    private var mTitle = ""

    private val webChromeClient = object : WebChromeClient() {

        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
            mTitle = title ?: ""
            mToolbatTitle.text = mTitle
        }

        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            progress.setProgress(newProgress)
        }
    }

    override fun onBackPressed() {
        if (web_view.canGoBack()) {
            web_view.goBack()
        } else {
            super.onBackPressed()
        }
    }

    private val mMenuItemIdShare = 10
    private val mMenuItemIdSize = 11
    private val mMenuItemIdBrowser = 12

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuItemSize = menu?.add(0, mMenuItemIdSize, 0, "文字大小")!!
        menuItemSize.setIcon(R.drawable.ic_format_size_white_24dp)
        menuItemSize.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
        val menuItemShare = menu?.add(0, mMenuItemIdShare, 0, "分享")!!
        menuItemShare.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
        menuItemShare.setIcon(R.drawable.ic_share_white_24dp)
        val menuItemBrowser = menu?.add(0, mMenuItemIdBrowser, 0, "在浏览器打开")!!
        menuItemBrowser.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
        menuItemBrowser.setIcon(R.drawable.ic_public_white_24dp)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            mMenuItemIdShare -> {
                share()
            }
            mMenuItemIdSize -> {
                val size = SettingsUtils.getWebViewTextSize()
                val index: Int = when (size) {
                    80 -> {
                        0
                    }
                    100 -> {
                        1
                    }
                    else -> {
                        2
                    }
                }
                MaterialDialog.Builder(this)
                        .title("字体大小")
                        .items("小", "默认", "大")
                        .itemsCallbackSingleChoice(index) { _, _, which, _ ->
                            val settings = web_view.settings
                            when (which) {
                                0 -> {
                                    settings.textZoom = 80
                                }
                                1 -> {
                                    settings.textZoom = 100

                                }
                                2 -> {
                                    settings.textZoom = 120
                                }
                            }
                            SettingsUtils.setWebViewTextSize(settings.textZoom)
                            true
                        }
                        .show()
            }
            mMenuItemIdBrowser -> {
                val intent = Intent()
                intent.action = "android.intent.action.VIEW"
                val url = Uri.parse(web_view.url)
                intent.data = url
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun share() {
        val oks = OnekeyShare()
        //关闭sso授权
        oks.disableSSOWhenAuthorize()
        oks.text = "我在使用 头条新闻 阅读：$mTitle，链接：${web_view.url}"
        // 启动分享GUI
        oks.callback = object : PlatformActionListener {

            override fun onComplete(p0: Platform?, p1: Int, p2: HashMap<String, Any>?) {
            }

            override fun onCancel(p0: Platform?, p1: Int) {
                ToastUtils.showLong("分享取消")
            }

            override fun onError(p0: Platform?, p1: Int, p2: Throwable?) {
                ToastUtils.showLong("分享失败")
                LogUtils.e(p0, p1, p2)
            }
        }
        oks.show(this)
    }
}
