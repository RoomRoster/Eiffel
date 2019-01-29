package com.etiennelenhart.sample

import android.app.Application

open class App : Application() {

    override fun onCreate() {
        super.onCreate()

        setupEiffel()
    }

    open fun setupEiffel() {}
}