package com.kalabukhov.app.stopwatchmodule.repository

import kotlinx.coroutines.flow.Flow

interface RepositoryStopWatch {
    suspend fun setStartStopWatch(): Flow<Int>
    suspend fun setStopStopWatch()
}