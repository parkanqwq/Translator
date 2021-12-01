package com.kalabukhov.app.translator.data.rest

import com.kalabukhov.app.translator.domain.entity.DataModel
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiWorlds {
    @GET("words/search")
    fun search(@Query("search") wordToSearch: String
    ): Observable<List<DataModel>>
}