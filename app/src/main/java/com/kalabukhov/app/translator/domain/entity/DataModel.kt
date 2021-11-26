package com.kalabukhov.app.translator.domain.entity

import com.squareup.moshi.Json

data class DataModel (
    @field:Json(name = "text") val text: String?,
    @field:Json(name = "meanings") val meanings: List<Meanings>?
    )