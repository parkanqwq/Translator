package com.kalabukhov.app.translator.impl

import com.kalabukhov.app.translator.data.WordDao
import com.kalabukhov.app.translator.domain.entity.WordEntity
import com.kalabukhov.app.translator.domain.repo.WordRepo

class RepositoryWordsDbImpl(
    private val wordDao: WordDao
    ) : WordRepo {

    override fun words(): List<WordEntity> = wordDao.getWords()

    override fun put(word: WordEntity) {
        wordDao.put(word)
    }

    override fun delete(word: WordEntity) {
        wordDao.delete(word)
    }

    override fun clear() {
        wordDao.clear()
    }

}