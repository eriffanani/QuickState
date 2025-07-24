package com.erif.quickstates.examples

import android.os.Bundle
import android.view.MenuItem
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.erif.quickstates.QuickState
import com.erif.quickstates.QuickStateView
import com.erif.quickstates.R
import com.erif.quickstates.helper.Constant
import com.erif.quickstates.helper.Delay
import com.erif.quickstates.helper.adapter.AdapterList

class ActFullState : AppCompatActivity(), QuickState.OnClickListener {

    private var state: QuickState? = null
    private var step = 1

    private val adapter = AdapterList()
    private var recyclerView: RecyclerView? = null
    private var loader: NestedScrollView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_full_state)

        supportActionBar?.apply {
            title = "Full State"
            setDisplayHomeAsUpEnabled(true)
        }

        val parentView: RelativeLayout = findViewById(R.id.act_full_parentView)
        loader = findViewById(R.id.act_full_loader)
        recyclerView = findViewById(R.id.act_full_recyclerView)
        recyclerView?.isVisible = false
        val manager = LinearLayoutManager(this)
        recyclerView?.layoutManager = manager
        state = QuickState(parentView, true)
        state?.onClickListener(this)
        state?.contentLoader(loader)

        val list: MutableList<Int> = ArrayList()
        for (i in 0 until 20) {
            list.add(i)
        }
        adapter.setList(list)
        recyclerView?.adapter = adapter
        loadingData()
    }

    private fun loadingData() {
        blinkLoading()
        Delay(2) {
            if (step > 3) {
                loader?.isVisible = false
                loader?.animate()
                    ?.alpha(0f)
                    ?.setDuration(200L)
                    ?.start()
                recyclerView?.isVisible = true
                recyclerView?.scheduleLayoutAnimation()
            } else {
                val stateName = when (step) {
                    2 -> { Constant.INTERNAL }
                    3 -> { Constant.EMPTY }
                    else -> { Constant.NETWORK }
                }
                state?.show(stateName)
            }
        }
    }

    private fun blinkLoading() {
        val anim: Animation = AlphaAnimation(0.4f, 1.0f)
        anim.duration = 500 //You can manage the blinking time with this parameter
        anim.repeatMode = Animation.REVERSE
        anim.repeatCount = Animation.INFINITE
        loader?.startAnimation(anim)
    }

    override fun onClickStateButton(stateView: QuickStateView?, buttonState: Int, tag: String?) {
        if (buttonState == QuickState.BUTTON_PRIMARY) {
            step++
            state?.hideAndShowLoader()
            Delay(0.4) {
                loadingData()
            }
        } else if (buttonState == QuickState.BUTTON_SECONDARY) {
            finish()
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