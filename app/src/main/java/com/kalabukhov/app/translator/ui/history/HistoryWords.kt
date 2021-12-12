package com.kalabukhov.app.translator.ui.history

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import com.kalabukhov.app.translator.R
import com.kalabukhov.app.translator.databinding.ActivityHistoryWordsBinding
import com.kalabukhov.app.translator.domain.entity.DataModel
import com.kalabukhov.app.translator.domain.entity.WordEntity
import com.kalabukhov.app.translator.domain.repo.WordRepo
import com.kalabukhov.app.translator.model.AppState
import com.kalabukhov.app.translator.model.AppStateEntity
import com.kalabukhov.app.translator.showSnackBar
import com.kalabukhov.app.translator.ui.main.AdapterWords
import com.kalabukhov.app.translator.ui.main.MainActivity
import com.kalabukhov.app.translator.ui.word.OpenWord
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryWords : AppCompatActivity() {

    private val wordsRepo: WordRepo by inject()
    private val viewModel: HistoryWordsViewModel by viewModel()

    private lateinit var binding: ActivityHistoryWordsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryWordsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewToolBar()
        binding.wordsRecyclerView.adapter = adapterWords
        viewModel.getLiveData().observe(this@HistoryWords, { renderData(it) })
        viewModel.getWords(wordsRepo)
    }

    private val onObjectListener = object : OnItemViewClickListener {
        override fun onItemViewClick(dataModel: WordEntity) {
            val intent = Intent(this@HistoryWords, OpenWord::class.java)
            intent.putExtra(tittle, dataModel.tittle)
            intent.putExtra(translation, dataModel.translation)
            intent.putExtra(imageUrl, dataModel.imageUrl)
            startActivity(intent)
        }
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(dataModel: WordEntity)
    }

    private val adapterWords = AdapterHistoryWords(onObjectListener)

    private fun renderData(appState: AppStateEntity) {
        with(binding){
            when (appState) {
                is AppStateEntity.Success -> {
                    adapterWords.setDataModel(appState.data.asReversed())
                }
                is AppStateEntity.Loading -> {
                    history.showSnackBar("Loading")
                }
                is AppStateEntity.Error -> {
                    history.showSnackBar("ERROR")
                }
            }
        }
    }

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

                return true
            }
        }
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_history, menu)
        val search = menu.findItem(R.id.action_history_menu)
        val searchText = search.actionView as SearchView
        searchText.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query == "") viewModel.getWords(wordsRepo)
                else viewModel.getWordSearch(wordsRepo, query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {

                return true
            }
        })
        return true
    }

    companion object {
        const val tittle = "tittle"
        const val translation = "translation"
        const val imageUrl = "imageUrl"
    }
}