package com.erif.quickstates.helper

import android.app.Application
import com.erif.quickstates.QuickStateBuilder
import com.erif.quickstates.R

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        val builder = QuickStateBuilder(this)
        builder.addState(Constant.NETWORK, R.layout.state_network)
        builder.addState(Constant.EMPTY, R.layout.state_empty)
        builder.addState(Constant.INTERNAL, R.layout.state_internal_server)
    }

}