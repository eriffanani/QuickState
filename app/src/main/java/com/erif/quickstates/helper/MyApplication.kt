package com.erif.quickstates.helper

import android.app.Application
import com.erif.quickstates.QuickStateBuilder
import com.erif.quickstates.R

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        val builder = QuickStateBuilder(this)
        builder.addState(Constant.DEFAULT, R.layout.state_default)
        builder.addState(Constant.FONTS, R.layout.state_fonts)
        builder.addState(Constant.ILLUSTRATION, R.layout.state_illustration)
        builder.addState(Constant.ANIMATED, R.layout.state_animated)

        builder.addState(Constant.NETWORK, R.layout.state_network)
        builder.addState(Constant.EMPTY, R.layout.state_empty)
        builder.addState(Constant.INTERNAL, R.layout.state_internal_server)
    }

}