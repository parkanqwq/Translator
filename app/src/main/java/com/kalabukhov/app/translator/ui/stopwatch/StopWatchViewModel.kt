package com.kalabukhov.app.translator.ui.stopwatch

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kalabukhov.app.translator.model.AppStateStopWatch
import com.kalabukhov.app.translator.ui.repository.RepositoryStopWatch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class StopWatchViewModel : ViewModel(), LifecycleObserver, CoroutineScope by MainScope() {

    private val liveDadaToObserver: MutableLiveData<AppStateStopWatch> = MutableLiveData()
    fun getLiveData() = liveDadaToObserver

    fun setTimeStopWatch(repositoryStopWatch: RepositoryStopWatch) = getTime(repositoryStopWatch)
    fun setStopStopWatch(repositoryStopWatch: RepositoryStopWatch) = stop(repositoryStopWatch)

    private fun getTime(repositoryStopWatch: RepositoryStopWatch) {
        launch {
            repositoryStopWatch.setStartStopWatch()
                .collect {
                    liveDadaToObserver.value = AppStateStopWatch.Start(it)
                }
        }
    }

    private fun stop(repositoryStopWatch: RepositoryStopWatch) {
        launch {
            repositoryStopWatch.setStopStopWatch()
            liveDadaToObserver.value = AppStateStopWatch.Stop
        }
    }
}