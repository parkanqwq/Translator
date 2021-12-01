package com.kalabukhov.app.translator.model

import com.kalabukhov.app.translator.domain.entity.DataModel

sealed class AppState {
    data class Success(val data: List<DataModel>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}