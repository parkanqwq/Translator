package com.kalabukhov.app.translator

import android.app.Application
import android.content.Context
import com.kalabukhov.app.translator.data.rest.ApiWorlds
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class App : Application() {

        private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_LOCATIONS)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .build()
    val apiWorlds: ApiWorlds = retrofit.create(ApiWorlds::class.java)

    companion object {
        private const val BASE_URL_LOCATIONS = "https://dictionary.skyeng.ru/api/public/v1/"
    }
}

val Context.app : App
    get() = applicationContext as App