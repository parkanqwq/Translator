package com.kalabukhov.app.translator.domain.entity

import com.squareup.moshi.Json

data class Translation(
    @field:Json(name = "text") val translation: String?
)
