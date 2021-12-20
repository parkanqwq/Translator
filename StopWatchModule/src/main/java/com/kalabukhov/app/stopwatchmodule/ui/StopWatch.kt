package com.kalabukhov.app.stopwatchmodule.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.kalabukhov.app.stopwatchmodule.R
import com.kalabukhov.app.stopwatchmodule.databinding.ActivityStopSwatchBinding
import com.kalabukhov.app.stopwatchmodule.impl.RepositoryStopWatchImpl
import com.kalabukhov.app.stopwatchmodule.model.AppStateStopWatch
import com.kalabukhov.app.stopwatchmodule.repository.RepositoryStopWatch

class StopWatch : AppCompatActivity() {

    private var startOrStop = true

    private lateinit var binding: ActivityStopSwatchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStopSwatchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repositoryStopWatch: RepositoryStopWatch = RepositoryStopWatchImpl()
        val viewModel = ViewModelProvider(this).get(StopWatchViewModel::class.java)

        initView(viewModel, repositoryStopWatch)
    }

    private fun initView(
        viewModel: StopWatchViewModel, repositoryStopWatch: RepositoryStopWatch) {

        viewModel.getLiveData().observe(this@StopWatch, { renderData(it) })
        binding.startViewButton.setOnClickListener {
            startOrStop = if (startOrStop) {
                viewModel.setTimeStopWatch(repositoryStopWatch)
                false
            } else {
                viewModel.setStopStopWatch(repositoryStopWatch)
                true
            }
        }
    }

    private fun renderData(appStateStopWatch: AppStateStopWatch) {
        with(binding){
            when (appStateStopWatch) {
                is AppStateStopWatch.Start -> {
                    startViewButton.text = resources.getString(R.string.stop)
                    secTextView.text = appStateStopWatch.data.toString()
                }
                is AppStateStopWatch.Stop -> {
                    startViewButton.text = resources.getString(R.string.start)
                }
            }
        }
    }
}