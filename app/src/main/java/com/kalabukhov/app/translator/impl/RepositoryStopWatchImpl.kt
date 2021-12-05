package com.kalabukhov.app.translator.impl

import com.kalabukhov.app.translator.ui.repository.RepositoryStopWatch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class RepositoryStopWatchImpl : RepositoryStopWatch {
    private var dataBase: Int = -1
    private val refreshIntervalMs: Long = 1000
    private var stopFlow = false

    override suspend fun setStartStopWatch(): Flow<Int> {
        val data: Flow<Int> = flow {
            while (true) {
                dataBase++
                emit(dataBase)
                delay(refreshIntervalMs)
                if (stopFlow) {
                    stopFlow = false
                    break
                }
            }
        }
            .flowOn(Dispatchers.Default)
            .catch { e ->
                println(e.message)
            }
        return data
    }

    override suspend fun setStopStopWatch() {
        if (!stopFlow) stopFlow = true
    }
}