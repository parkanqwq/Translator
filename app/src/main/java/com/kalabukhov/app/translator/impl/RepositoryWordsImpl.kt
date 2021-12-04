package com.kalabukhov.app.translator.impl

import com.kalabukhov.app.translator.data.rest.ApiWorlds
import com.kalabukhov.app.translator.domain.entity.DataModel
import com.kalabukhov.app.translator.ui.repository.RepositoryWords
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RepositoryWordsImpl(private val apiWorlds: ApiWorlds) : RepositoryWords {

    override fun getWords(word: String): Observable<List<DataModel>> {
        return apiWorlds.search(word)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
    }
}