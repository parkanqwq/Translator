package com.kalabukhov.app.translator.ui.word

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.kalabukhov.app.translator.R
import com.kalabukhov.app.translator.databinding.ActivityOpenWordBinding
import com.kalabukhov.app.translator.domain.entity.WordEntity
import com.kalabukhov.app.translator.domain.repo.WordRepo
import com.kalabukhov.app.translator.impl.RepositoryWordsDbImpl
import com.kalabukhov.app.translator.model.AppState
import com.kalabukhov.app.translator.model.AppStateWordsDb
import com.kalabukhov.app.translator.showSnackBar
import com.squareup.picasso.Picasso
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class OpenWord : AppCompatActivity() {

    private val viewModel: OpenWordViewModel by viewModel()
    private val wordRepo: WordRepo by inject()
    private lateinit var wordEntity: WordEntity

    private lateinit var binding: ActivityOpenWordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOpenWordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createWord(intent.getStringExtra(tittle).toString(),
            intent.getStringExtra(translation).toString(),
            intent.getStringExtra(imageUrl).toString())

        initView()
        viewModel.getLiveData().observe(this@OpenWord, { renderData(it) })
        viewModel.getWords(wordRepo, wordEntity)
    }

    private fun renderData(appState: AppStateWordsDb) {
        with(binding){
            when (appState) {
                is AppStateWordsDb.Success -> {

                }
                is AppStateWordsDb.Loading -> {

                }
                is AppStateWordsDb.Error -> {
                    open.showSnackBar("ERROR")
                }
            }
        }
    }

    private fun createWord(tittle: String, translation: String, imageUrl: String) {
        wordEntity = WordEntity(tittle, translation, imageUrl)
    }

    private fun initView() = with(binding){
        tittleTextView.text = wordEntity.tittle
        translationTextView.text = wordEntity.translation
        Picasso.get()
            .load(https + intent.getStringExtra(imageUrl).toString())
            .into(imageWordImageView)
    }


    companion object {
        const val https = "https:"
        const val tittle = "tittle"
        const val translation = "translation"
        const val imageUrl = "imageUrl"
    }
}