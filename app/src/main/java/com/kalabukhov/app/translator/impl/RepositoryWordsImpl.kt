package com.kalabukhov.app.translator.impl

import com.kalabukhov.app.translator.App
import com.kalabukhov.app.translator.domain.entity.DataModel
import com.kalabukhov.app.translator.ui.repository.RepositoryWords
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RepositoryWordsImpl : RepositoryWords {
    override fun getWords(app: App, word: String): List<DataModel> {
        val compositeDisposable = CompositeDisposable()
        var listWords: List<DataModel> = listOf()

        compositeDisposable.add(app.apiWorlds.search(word)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { repos ->
                listWords = repos
            })
        return listWords
    }
}