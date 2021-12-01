package com.kalabukhov.app.translator.ui.repository

import com.kalabukhov.app.translator.domain.entity.DataModel
import io.reactivex.Observable

interface RepositoryWords {
    fun getWords(word: String): Observable<List<DataModel>>
}