package com.kalabukhov.app.translator.ui.word

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kalabukhov.app.translator.domain.entity.WordEntity
import com.kalabukhov.app.translator.domain.repo.WordRepo
import com.kalabukhov.app.translator.model.AppState
import com.kalabukhov.app.translator.model.AppStateStopWatch
import com.kalabukhov.app.translator.model.AppStateWordsDb
import com.kalabukhov.app.translator.ui.repository.RepositoryWords
import kotlinx.coroutines.*

class OpenWordViewModel : ViewModel(), LifecycleObserver, CoroutineScope by MainScope() {

    private val liveDataToObserve: MutableLiveData<AppStateWordsDb> = MutableLiveData()
    fun getLiveData() = liveDataToObserve

    fun getWords(wordRepo: WordRepo, wordEntity: WordEntity)
    = getDataFromWordsSource(wordRepo, wordEntity)

    private fun getDataFromWordsSource(wordRepo: WordRepo, wordEntity: WordEntity) {
        liveDataToObserve.value = AppStateWordsDb.Loading

        launch {
            val job = async(Dispatchers.IO) {
                wordRepo.put(wordEntity)
                wordEntity
            }
            liveDataToObserve.value = AppStateWordsDb.Success(job.await())
        }
    }
}