package com.kalabukhov.app.translator.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kalabukhov.app.translator.domain.entity.WordEntity

@Database(
    entities = [WordEntity::class],
    version = 1
)

abstract class WordsDb : RoomDatabase() {
    abstract fun wordDao() : WordDao
}