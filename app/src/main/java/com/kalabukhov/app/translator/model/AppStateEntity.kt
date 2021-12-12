package com.kalabukhov.app.translator.model

import com.kalabukhov.app.translator.domain.entity.DataModel
import com.kalabukhov.app.translator.domain.entity.WordEntity

sealed class AppStateEntity {
    data class Success(val data: List<WordEntity>) : AppStateEntity()
    data class Error(val error: Throwable) : AppStateEntity()
    object Loading : AppStateEntity()
}