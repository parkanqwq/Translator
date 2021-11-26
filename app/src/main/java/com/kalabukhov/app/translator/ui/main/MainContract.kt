package com.kalabukhov.app.translator.ui.main

import com.kalabukhov.app.translator.App
import com.kalabukhov.app.translator.domain.entity.DataModel
import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd

class MainContract {

    enum class ViewState {
        LOADING, SUCCESSFUL, ERROR
    }

    interface View: MvpView {
        @AddToEnd
        fun setState(state: ViewState)
        @AddToEnd
        fun showWordsWed(dataModel: List<DataModel>)
    }

    abstract class Presenter: MvpPresenter<View>() {
        abstract fun onLoadListWords(app: App, word: String)
    }
}