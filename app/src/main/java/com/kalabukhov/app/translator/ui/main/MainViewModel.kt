package com.kalabukhov.app.translator.ui.main

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kalabukhov.app.translator.App
import com.kalabukhov.app.translator.model.AppState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel(), LifecycleObserver {

    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()

    fun getLiveData() = liveDataToObserve

    fun getWords(app: App, word: String) = getDataFromWordsSource(app, word)

    private fun getDataFromWordsSource(app: App, word: String) {
        liveDataToObserve.value = AppState.Loading

            val compositeDisposable = CompositeDisposable()
            compositeDisposable.add(app.apiWorlds.search(word)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { repos ->
                    liveDataToObserve.value = AppState.Success(repos)
                })
    }
}