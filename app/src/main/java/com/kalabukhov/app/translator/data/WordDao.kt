package com.kalabukhov.app.translator.data

import androidx.room.*
import com.kalabukhov.app.translator.domain.entity.WordEntity
import io.reactivex.Completable
import org.jetbrains.annotations.NotNull

@Dao
interface WordDao {

    @Query("SELECT * FROM words")
    fun getWords() : List<WordEntity>

    @Query("DELETE FROM words")
    fun clear()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun put(word: WordEntity)

    @Delete
    fun delete(word: WordEntity)
}