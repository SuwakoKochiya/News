package cn.chhd.news.ui.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup
import cn.chhd.news.R
import cn.chhd.news.util.ImageLoader
import com.github.chrisbanes.photoview.PhotoView
import kotlinx.android.synthetic.main.activity_image.*

class ImageActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context, url: String) {
            val imgUrlList = ArrayList<String>()
            imgUrlList.add(url)
            val intent = Intent(context, ImageActivity::class.java)
            intent.putExtra("imgUrlList", imgUrlList)
            context.startActivity(intent)
        }

        fun start(context: Context, imgUrlList: ArrayList<String>) {
            val intent = Intent(context, ImageActivity::class.java)
            intent.putExtra("imgUrlList", imgUrlList)
            context.startActivity(intent)
        }
    }

    private val mImgUrlList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        iv_back.setOnClickListener {
            finish()
        }

        val imgUrlList = intent.getSerializableExtra("imgUrlList") as ArrayList<String>
        mImgUrlList.addAll(imgUrlList)

        val v = if (mImgUrlList.size == 1) View.GONE else View.VISIBLE
        tv_page.visibility = v
        tv_page.text = String.format("%d / %d", 1, mImgUrlList.size)

        view_pager.adapter = ImagePagerAdapter(mImgUrlList)
        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                tv_page.text = String.format("%d / %d", position + 1, mImgUrlList.size)
            }
        })
    }

    class ImagePagerAdapter(private val imgUrlList: ArrayList<String> = ArrayList())
        : PagerAdapter() {

        override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
            return view == `object`
        }

        override fun getCount(): Int {
            return imgUrlList.size
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val photoView = PhotoView(container.context)
            ImageLoader.instance.load(imgUrlList[position]).into(photoView)
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT)
            return photoView
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any?) {
            container.removeView(`object` as View)
        }
    }
}
