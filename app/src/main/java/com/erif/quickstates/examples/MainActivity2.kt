package com.erif.quickstates.examples

import android.os.Bundle
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
import java.util.Random

class MainActivity2 : AppCompatActivity(), QuickState.OnClickListener {

    private var contentLoader: NestedScrollView? = null
    private var state: QuickState? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val parentLayout: CoordinatorLayout = findViewById(R.id.parentView)
        contentLoader = findViewById(R.id.loadingLayout2)
        state = QuickState(parentLayout, true)
        state?.contentLoader(contentLoader)
        state?.onClickListener(this)
        requestIllustration()
    }

    private fun requestIllustration() {
        blinkLoading()
        Delay(1.5) {
            state?.show(getRandomState())
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
        when (buttonState) {
            QuickState.BUTTON_LEFT -> finish()
            QuickState.BUTTON_RIGHT -> {
                state?.hideAndShowLoader()
                // Delay
                Delay(0.5) {
                    requestIllustration()
                }
            }
        }
    }

    private fun getRandomState(): String {
        val arr = arrayOf(
            Constant.NETWORK, Constant.INTERNAL
        )
        return arr[random()]
    }

    private fun random(): Int {
        val rand = Random()
        return rand.nextInt(2)
    }

}