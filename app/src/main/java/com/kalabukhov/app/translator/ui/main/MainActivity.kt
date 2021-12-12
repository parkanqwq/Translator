package com.kalabukhov.app.translator.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.kalabukhov.app.translator.R
import com.kalabukhov.app.translator.databinding.ActivityMainBinding
import com.kalabukhov.app.translator.domain.entity.DataModel
import com.kalabukhov.app.translator.hideKeyboard
import com.kalabukhov.app.translator.model.AppState
import com.kalabukhov.app.translator.showSnackBar
import com.kalabukhov.app.translator.ui.history.HistoryWords
import com.kalabukhov.app.translator.ui.repository.RepositoryWords
import com.kalabukhov.app.translator.ui.stopwatch.StopWatch
import com.kalabukhov.app.translator.ui.word.OpenWord
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val apiWords: RepositoryWords by inject()
    private val viewModel: MainViewModel by viewModel()
    //private lateinit var viewModel: MainViewModel

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
       // app.appComponent.inject(this)
       // viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        initViewToolBar()
        initView()
        binding.stopwatchButtonView.setOnClickListener {
            val intent = Intent(this, StopWatch::class.java)
            startActivity(intent)
        }
    }

    private fun initView() = with(binding){
        wordsRecyclerView.adapter = adapterWords
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

        viewModel.getLiveData().observe(this@MainActivity, { renderData(it) })
        findButtonView.setOnClickListener {
            viewModel.getWords(apiWords, findWordEditView.text.toString())
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
            val intent = Intent(this@MainActivity, OpenWord::class.java)
            intent.putExtra(tittle, dataModel.text)
            intent.putExtra(translation, dataModel.meanings?.get(0)
                ?.translation?.translation)
            intent.putExtra(imageUrl, dataModel.meanings?.get(0)?.imageUrl)
            startActivity(intent)
        }
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(dataModel: DataModel)
    }

    private val adapterWords = AdapterWords(onObjectListener)

    private fun initViewToolBar() {
        val toolbar: Toolbar = initToolbar()
    }

    private fun initToolbar(): Toolbar {
        val toolbar = findViewById<Toolbar>(R.id.main_tool_bar)
        setSupportActionBar(toolbar)
        return toolbar
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return if (navigateFragment(id)) {
            true
        } else super.onOptionsItemSelected(item)
    }

    private fun navigateFragment(id: Int): Boolean {
        when (id) {
            R.id.action_history_menu -> {
                val intent = Intent(this, HistoryWords::class.java)
                startActivity(intent)
                return true
            }
        }
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    companion object {
        const val tittle = "tittle"
        const val translation = "translation"
        const val imageUrl = "imageUrl"
    }
}