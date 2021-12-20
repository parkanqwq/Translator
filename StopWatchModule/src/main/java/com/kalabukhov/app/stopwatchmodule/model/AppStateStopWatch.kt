package com.kalabukhov.app.stopwatchmodule.model

sealed class AppStateStopWatch {
    data class Start(val data: Int) : AppStateStopWatch()
    object Stop : AppStateStopWatch()
}