package com.kalabukhov.app.translator.ui.main

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.kalabukhov.app.translator.app
import com.kalabukhov.app.translator.databinding.ActivityMainBinding
import com.kalabukhov.app.translator.domain.entity.DataModel
import com.kalabukhov.app.translator.hideKeyboard
import com.kalabukhov.app.translator.model.AppState
import com.kalabukhov.app.translator.showSnackBar

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        initView()
    }

    private fun initView() = with(binding){
        wordsRecyclerView.adapter = adapterWords
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

        viewModel.getLiveData().observe(this@MainActivity, { renderData(it) })
        findButtonView.setOnClickListener {
            viewModel.getWords(app, findWordEditView.text.toString())
            main.hideKeyboard(inputMethodManager)
        }
    }

    private fun renderData(appState: AppState) {
        with(binding){
            when (appState) {
                is AppState.Success -> {
                    progressBar.visibility = View.GONE
                    wordsRecyclerView.visibility = View.VISIBLE
                    main.showSnackBar("SUCCESSFUL")
                    adapterWords.setDataModel(appState.data)
                }
                is AppState.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    wordsRecyclerView.visibility = View.GONE
                }
                is AppState.Error -> {
                    main.showSnackBar("ERROR")
                }
            }
        }
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