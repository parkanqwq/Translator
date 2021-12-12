package com.kalabukhov.app.translator.domain.repo

import com.kalabukhov.app.translator.domain.entity.WordEntity

interface WordRepo {
    fun words(): List<WordEntity>
    fun put(word: WordEntity)
    fun delete(word: WordEntity)
    fun clear()
}