package com.erif.quickstates.helper

import android.os.Handler
import android.os.Looper

class Delay {

    companion object {
        private const val INTERVAL: Int = 1000
    }

    /**
     * Delay Timer
     * @param seconds Duration to executed
     * @param execution Function to executed
     */
    constructor(seconds: Int, execution: () -> Unit) {
        val duration: Long = (seconds * INTERVAL).toLong()
        exec(duration, execution)
    }

    /**
     * Delay Timer
     * @param seconds Duration to executed
     * @param execution Function to executed
     */
    constructor(seconds: Double, execution: () -> Unit) {
        val duration: Long = (seconds * INTERVAL).toLong()
        exec(duration, execution)
    }

    /**
     * Delay Timer
     * @param milliseconds Duration to executed
     * @param execution Function to executed
     */
    constructor(milliseconds: Long, execution: () -> Unit) {
        exec(milliseconds, execution)
    }

    private fun exec(duration: Long, execution: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed({
            execution()
        }, duration)
    }


}