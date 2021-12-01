package com.kalabukhov.app.translator.ui.main

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kalabukhov.app.translator.model.AppState
import com.kalabukhov.app.translator.ui.repository.RepositoryWords

class MainViewModel : ViewModel(), LifecycleObserver {

    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
    fun getLiveData() = liveDataToObserve

    fun getWords(apiWorlds: RepositoryWords, word: String) =
        getDataFromWordsSource(apiWorlds, word)

    private fun getDataFromWordsSource(apiWorlds: RepositoryWords, word: String) {
        liveDataToObserve.value = AppState.Loading

        apiWorlds.getWords(word).subscribe {
            liveDataToObserve.value = AppState.Success(it)
        }
    }
}