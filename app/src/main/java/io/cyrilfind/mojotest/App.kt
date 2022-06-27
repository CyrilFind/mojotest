package io.cyrilfind.mojotest

import android.app.Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        ServiceLocator.appContext = this
    }
}