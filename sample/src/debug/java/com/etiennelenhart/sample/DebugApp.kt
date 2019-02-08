package com.etiennelenhart.sample

import com.etiennelenhart.eiffel.Eiffel
import com.etiennelenhart.eiffel.logger.logger
import timber.log.Timber

class DebugApp : App() {

    override fun setupEiffel() {
        Timber.plant(Timber.DebugTree())

        Eiffel.debugMode(
            enabled = true,
            logger = logger { priority, tag, message ->
                Timber.tag("Eiffel:$tag").log(priority, message)
            }
        )
    }
}