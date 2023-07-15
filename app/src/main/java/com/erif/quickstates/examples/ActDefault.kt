package com.erif.quickstates.examples

import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.erif.quickstates.QuickState
import com.erif.quickstates.QuickStateView
import com.erif.quickstates.R
import com.erif.quickstates.helper.Constant
import com.erif.quickstates.helper.Delay

class ActDefault : AppCompatActivity(), QuickState.OnClickListener {

    private var contentView: LinearLayout? = null
    private var state: QuickState? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_default)

        supportActionBar?.apply {
            title = "Default State"
            setDisplayHomeAsUpEnabled(true)
        }

        val parentView: ConstraintLayout = findViewById(R.id.act_default_parentView)
        state = QuickState(parentView, true)
        state?.onClickListener(this)

        contentView = findViewById(R.id.act_default_contentView)
        state?.contentLoader(contentView)

        loadData()

    }

    private fun loadData() {
        Delay(2.5) {
            state?.show(Constant.DEFAULT)
        }
    }

    override fun onClickStateButton(stateView: QuickStateView?, buttonState: Int, tag: String?) {
        stateView?.hideAndShowLoader()
        loadData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}