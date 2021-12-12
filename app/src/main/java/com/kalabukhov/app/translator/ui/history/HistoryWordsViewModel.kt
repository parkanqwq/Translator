package com.kalabukhov.app.translator.ui.history

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kalabukhov.app.translator.domain.entity.WordEntity
import com.kalabukhov.app.translator.domain.repo.WordRepo
import com.kalabukhov.app.translator.model.AppState
import com.kalabukhov.app.translator.model.AppStateEntity
import com.kalabukhov.app.translator.model.AppStateWordsDb
import kotlinx.coroutines.*

class HistoryWordsViewModel  : ViewModel(), LifecycleObserver, CoroutineScope by MainScope() {

    private val liveDataToObserve: MutableLiveData<AppStateEntity> = MutableLiveData()
    fun getLiveData() = liveDataToObserve
    private lateinit var listWords: List<WordEntity>

    fun getWords(wordRepo: WordRepo)
            = getDataFromWordsSource(wordRepo)

    fun getWordSearch(wordRepo: WordRepo, wordSearch: String)
            = getDataFromWordsSearch(wordRepo, wordSearch)

    private fun getDataFromWordsSource(wordRepo: WordRepo) {
        liveDataToObserve.value = AppStateEntity.Loading
        launch {
            val job = async(Dispatchers.IO) {
                wordRepo.words()
            }
            liveDataToObserve.value = AppStateEntity.Success(job.await())
        }
    }

    private fun getDataFromWordsSearch(wordRepo: WordRepo, wordSearch: String) {
        liveDataToObserve.value = AppStateEntity.Loading
        launch {
            val job = async(Dispatchers.IO) {
                for (search in wordRepo.words()) {
                    if (wordSearch == search.translation) {
                        listWords = listOf(
                            WordEntity(
                                search.tittle,
                                search.translation,
                                search.imageUrl
                            )
                        )
                    }
                }
                if (listWords.isEmpty()) wordRepo.words()
                else listWords
            }
            liveDataToObserve.value = AppStateEntity.Success(job.await())
        }
    }
}