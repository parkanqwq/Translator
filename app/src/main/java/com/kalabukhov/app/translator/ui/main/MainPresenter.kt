package com.kalabukhov.app.translator.ui.main

import com.kalabukhov.app.translator.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class MainPresenter : MainContract.Presenter() {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onLoadListWords(app: App, word: String) {
        viewState.setState(MainContract.ViewState.LOADING)
        compositeDisposable.add(app.apiWorlds.search(word)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { repos ->
                    viewState.showWordsWed(repos)
                    viewState.setState(MainContract.ViewState.SUCCESSFUL)
                } ,
                { viewState.setState(MainContract.ViewState.ERROR) }))
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }
}