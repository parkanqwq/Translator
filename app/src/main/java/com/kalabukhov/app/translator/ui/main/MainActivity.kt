package com.kalabukhov.app.translator.ui.main

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.kalabukhov.app.translator.app
import com.kalabukhov.app.translator.databinding.ActivityMainBinding
import com.kalabukhov.app.translator.domain.entity.DataModel
import com.kalabukhov.app.translator.hideKeyboard
import com.kalabukhov.app.translator.showSnackBar
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

class MainActivity : MvpAppCompatActivity(), MainContract.View {

    private val presenter by moxyPresenter { MainPresenter() }

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() = with(binding){
        wordsRecyclerView.adapter = adapterWords
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

        findButtonView.setOnClickListener {
            presenter.onLoadListWords(app, findWordEditView.text.toString())
            main.hideKeyboard(inputMethodManager)
        }
    }

    override fun setState(state: MainContract.ViewState) = with(binding) {
        when (state) {
            MainContract.ViewState.SUCCESSFUL -> {
                progressBar.visibility = View.GONE
                wordsRecyclerView.visibility = View.VISIBLE
                main.showSnackBar("SUCCESSFUL")
            }
            MainContract.ViewState.LOADING -> {
                progressBar.visibility = View.VISIBLE
                wordsRecyclerView.visibility = View.GONE
            }
            MainContract.ViewState.ERROR -> {
                main.showSnackBar("ERROR")
            }
        }
    }

    override fun showWordsWed(dataModel: List<DataModel>) {
        adapterWords.setDataModel(dataModel)
    }

    private val onObjectListener = object : OnItemViewClickListener {
        override fun onItemViewClick(dataModel: DataModel) {
            binding.main.showSnackBar(dataModel.text.toString())
        }
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(dataModel: DataModel)
    }

    private val adapterWords = AdapterWords(onObjectListener)
}