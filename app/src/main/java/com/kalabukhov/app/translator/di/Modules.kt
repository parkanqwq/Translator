package com.kalabukhov.app.translator.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.kalabukhov.app.translator.data.WordDao
import com.kalabukhov.app.translator.data.WordsDb
import com.kalabukhov.app.translator.data.rest.ApiWorlds
import com.kalabukhov.app.translator.domain.repo.WordRepo
import com.kalabukhov.app.translator.impl.RepositoryStopWatchImpl
import com.kalabukhov.app.translator.impl.RepositoryWordsDbImpl
import com.kalabukhov.app.translator.impl.RepositoryWordsImpl
import com.kalabukhov.app.translator.ui.history.HistoryWordsViewModel
import com.kalabukhov.app.translator.ui.main.MainViewModel
import com.kalabukhov.app.translator.ui.repository.RepositoryStopWatch
import com.kalabukhov.app.translator.ui.repository.RepositoryWords
import com.kalabukhov.app.translator.ui.stopwatch.StopWatchViewModel
import com.kalabukhov.app.translator.ui.word.OpenWordViewModel
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

val retrofitModule = module {

    single<RepositoryWords> { RepositoryWordsImpl(get()) }
    single<ApiWorlds> { get<Retrofit>().create(ApiWorlds::class.java) }
    single<Retrofit> { Retrofit.Builder()
        .baseUrl("https://dictionary.skyeng.ru/api/public/v1/")
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .build() }
}

val stopWatchModule = module {
    single<RepositoryStopWatch> { RepositoryStopWatchImpl() }
}

val wordsDbModule = module {
    val dbPatch = "words.db"
    single { Room.databaseBuilder(get(), WordsDb::class.java, dbPatch).build() }
    single { get<WordsDb>().wordDao() }
    single<WordRepo> { RepositoryWordsDbImpl(get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel() }
    viewModel { StopWatchViewModel() }
    viewModel { OpenWordViewModel() }
    viewModel { HistoryWordsViewModel() }
}
//@Module
//class ModuleRetrofit {
//
//    @Provides
//    fun provideApiWorlds(retrofit: Retrofit) : ApiWorlds =
//        retrofit.create(ApiWorlds::class.java)
//
//    @Provides
//    fun provideRepositoryWords(apiWorlds: ApiWorlds) : RepositoryWords =
//        RepositoryWordsImpl(apiWorlds)
//
//    @Provides
//    @Singleton
//    fun provideRetrofit(): Retrofit = Retrofit.Builder()
//    .baseUrl("https://dictionary.skyeng.ru/api/public/v1/")
//    .addConverterFactory(MoshiConverterFactory.create())
//    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
//    .build()
//}
//
//@Singleton
//@Component(modules = [ModuleRetrofit::class])
//interface AppComponent {
//
//    fun inject(activity: MainActivity)
//}