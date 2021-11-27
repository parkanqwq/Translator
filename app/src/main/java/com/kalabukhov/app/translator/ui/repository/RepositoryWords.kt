package com.kalabukhov.app.translator.ui.repository

import com.kalabukhov.app.translator.App
import com.kalabukhov.app.translator.domain.entity.DataModel

interface RepositoryWords {
    fun getWords(app: App, word: String): List<DataModel>
}