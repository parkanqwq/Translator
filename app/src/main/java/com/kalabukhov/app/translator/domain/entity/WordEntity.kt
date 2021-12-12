package com.kalabukhov.app.translator.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class WordEntity(
    @PrimaryKey
    @ColumnInfo(name = "tittle") val tittle: String,
    @ColumnInfo(name = "translation") val translation: String,
    @ColumnInfo(name = "imageUrl") val imageUrl: String
)
