package com.kalabukhov.app.translator.domain.entity

import com.squareup.moshi.Json

data class Meanings (
    @field:Json(name = "translation") val translation: Translation?,
    @field:Json(name = "imageUrl") val imageUrl: String?
    )