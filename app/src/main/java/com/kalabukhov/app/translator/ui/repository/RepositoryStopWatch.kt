package com.kalabukhov.app.translator.ui.repository

import kotlinx.coroutines.flow.Flow

interface RepositoryStopWatch {
    suspend fun setStartStopWatch(): Flow<Int>
    suspend fun setStopStopWatch()
}