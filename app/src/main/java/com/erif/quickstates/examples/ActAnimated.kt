package com.erif.quickstates.examples

import android.os.Bundle
import android.view.MenuItem
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.NestedScrollView
import com.erif.quickstates.QuickState
import com.erif.quickstates.QuickStateView
import com.erif.quickstates.R
import com.erif.quickstates.helper.Constant
import com.erif.quickstates.helper.Delay

class ActAnimated : AppCompatActivity(), QuickState.OnClickListener {

    private var contentLoader: NestedScrollView? = null
    private var state: QuickState? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_animated)

        supportActionBar?.apply {
            title = "Animated Image State"
            setDisplayHomeAsUpEnabled(true)
        }

        val parentLayout: CoordinatorLayout = findViewById(R.id.act_animated_parentView)
        contentLoader = findViewById(R.id.act_animated_loadingView)
        state = QuickState(parentLayout, true)
        state?.contentLoader(contentLoader)
        state?.onClickListener(this)
        requestIllustration()
    }

    private fun requestIllustration() {
        blinkLoading()
        Delay(2) {
            state?.show(Constant.ANIMATED)
        }
    }

    private fun blinkLoading() {
        val anim: Animation = AlphaAnimation(0.4f, 1.0f)
        anim.duration = 500 //You can manage the blinking time with this parameter
        anim.repeatMode = Animation.REVERSE
        anim.repeatCount = Animation.INFINITE
        contentLoader?.startAnimation(anim)
    }

    override fun onClickStateButton(stateView: QuickStateView?, buttonState: Int, tag: String?) {
        if (buttonState == QuickState.BUTTON_PRIMARY) {
            state?.hideAndShowLoader()
            // Delay
            Delay(0.4) {
                requestIllustration()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}