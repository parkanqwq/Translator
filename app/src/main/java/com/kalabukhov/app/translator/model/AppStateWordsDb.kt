package com.kalabukhov.app.translator.model

import com.kalabukhov.app.translator.domain.entity.DataModel
import com.kalabukhov.app.translator.domain.entity.WordEntity

sealed class AppStateWordsDb {
    data class Success(val data: WordEntity) : AppStateWordsDb()
    data class Error(val error: Throwable) : AppStateWordsDb()
    object Loading : AppStateWordsDb()
}