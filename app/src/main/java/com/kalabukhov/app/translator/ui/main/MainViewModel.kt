package com.kalabukhov.app.translator.ui.main

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kalabukhov.app.translator.model.AppState
import com.kalabukhov.app.translator.ui.repository.RepositoryWords
import kotlinx.coroutines.*

class MainViewModel : ViewModel(), LifecycleObserver, CoroutineScope by MainScope() {

    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
    fun getLiveData() = liveDataToObserve

    fun getWords(apiWorlds: RepositoryWords, word: String) =
        getDataFromWordsSource(apiWorlds, word)

    private fun getDataFromWordsSource(apiWorlds: RepositoryWords, word: String) {
        liveDataToObserve.value = AppState.Loading

        launch {
            val job = async(Dispatchers.IO) {
                apiWorlds.getWords(word).blockingFirst()
            }
            liveDataToObserve.value = AppState.Success(job.await())
        }

//        apiWorlds.getWords(word).subscribe {
//            liveDataToObserve.value = AppState.Success(it)
//        }
    }

    fun handlerError(error: Throwable){}
}