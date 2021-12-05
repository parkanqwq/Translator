package com.kalabukhov.app.translator.model

sealed class AppStateStopWatch {
    data class Start(val data: Int) : AppStateStopWatch()
    object Stop : AppStateStopWatch()
}