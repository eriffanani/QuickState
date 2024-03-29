package com.erif.quickstates.examples

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.erif.quickstates.QuickState
import com.erif.quickstates.QuickStateView
import com.erif.quickstates.R
import com.erif.quickstates.helper.Constant
import com.erif.quickstates.helper.Delay

class ActImage : AppCompatActivity(), QuickState.OnClickListener {

    private var state: QuickState? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_illustration)

        supportActionBar?.apply {
            title = "Image Illustration State"
            setDisplayHomeAsUpEnabled(true)
        }

        val parentView: CoordinatorLayout = findViewById(R.id.act_illustration_parentView)
        state = QuickState(parentView, true)
        state?.onClickListener(this)
        val contentLoader = findViewById<LinearLayout>(R.id.act_illustration_loader)
        state?.contentLoader(contentLoader)
        loadingData()

    }

    private fun loadingData() {
        Delay(2) {
            state?.show(Constant.ILLUSTRATION)
        }
    }

    override fun onClickStateButton(stateView: QuickStateView?, buttonState: Int, tag: String?) {
        state?.hideAndShowLoader()
        loadingData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}