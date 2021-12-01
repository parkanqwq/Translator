package com.kalabukhov.app.translator

import android.app.Application
import android.content.Context
import com.kalabukhov.app.translator.di.AppComponent
import com.kalabukhov.app.translator.di.DaggerAppComponent

class App : Application() {

    val appComponent : AppComponent by lazy {
        DaggerAppComponent
            .builder()
            .build()
    }
}

val Context.app : App
    get() = applicationContext as App