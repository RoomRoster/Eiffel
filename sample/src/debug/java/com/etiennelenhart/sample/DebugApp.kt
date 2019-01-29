package com.etiennelenhart.sample

import com.etiennelenhart.eiffel.Eiffel
import com.etiennelenhart.eiffel.plugin.event.Event
import com.etiennelenhart.eiffel.plugin.event.transformEvent
import com.etiennelenhart.eiffel.plugin.logger.DefaultLoggerPlugin
import com.etiennelenhart.eiffel.plugin.logger.log
import timber.log.Timber

class DebugApp : App() {

    private val logger = DefaultLoggerPlugin(
        logger = log { _, tag, message ->
            // Changed to error for better visibility in the logcat
            Timber.tag(tag).e(message)
        },
        transform = transformEvent { event ->
            if (event is Event.Message) {
                // Lets make these messages YELL
                return@transformEvent event.message.toUpperCase()
            }
            defaultStringTransformer(event)
        }
    )

    override fun setupEiffel() {
        Timber.plant(Timber.DebugTree())

        Eiffel.debugMode(
            enabled = true,
            plugins = listOf(logger)
        )
    }
}