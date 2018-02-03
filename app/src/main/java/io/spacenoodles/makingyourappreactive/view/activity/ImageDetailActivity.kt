package io.spacenoodles.makingyourappreactive.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import io.spacenoodles.makingyourappreactive.R
import kotlinx.android.synthetic.main.activity_image_detail.*
import android.view.WindowManager


class ImageDetailActivity : AppCompatActivity() {

    private var imageUrl = ""
    private var title = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_detail)

        val w = window
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        val extras = intent.extras
        extras?.let {
            imageUrl = it.getString(MainActivity.EXTRA_IMAGE_URL, "")
            title = it.getString(MainActivity.EXTRA_IMAGE_TITLE, "")
        }

        init()
        postponeEnterTransition()
        //TODO: Handle situations where picasso fails better
    }

    private fun init() {
        loadImage()
        setTitle()
    }

    private fun loadImage() {
               Picasso.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.ic_image_24dp)
                .into(image_view, object: Callback {
                    override fun onSuccess() {
                        scheduleTransition(image_view)
                    }

                    override fun onError() {
                        Log.i("HI", "HI")
                    }
                })
    }

    private fun setTitle() {
        title_text.text = title
    }

    private fun scheduleTransition(sharedElement: View) {
        sharedElement.viewTreeObserver.addOnPreDrawListener(
                object : ViewTreeObserver.OnPreDrawListener {
                    override fun onPreDraw(): Boolean {
                        sharedElement.viewTreeObserver.removeOnPreDrawListener(this)
                        startPostponedEnterTransition()
                        return true
                    }
                })
    }
}
