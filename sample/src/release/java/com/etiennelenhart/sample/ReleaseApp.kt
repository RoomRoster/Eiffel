package com.etiennelenhart.sample

import com.etiennelenhart.eiffel.Eiffel
import com.etiennelenhart.eiffel.plugin.logger.ReleaseLoggerPlugin

class ReleaseApp : App() {

    /**
     * This will disable all logging to the logcat.
     *
     * Even though we globally disabled Eiffel.debugMode.  If a EiffelViewModel overrides the
     * debug variable, and the developer forgets to reset it, then the app still won't log in
     * production thanks to the ReleaseLoggerPlugin.
     */
    override fun setupEiffel() {
        Eiffel.debugMode(false, listOf(ReleaseLoggerPlugin))
    }
}