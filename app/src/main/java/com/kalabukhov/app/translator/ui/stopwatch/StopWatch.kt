package com.kalabukhov.app.translator.ui.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kalabukhov.app.translator.R
import com.kalabukhov.app.translator.databinding.ActivityStopWatchBinding
import com.kalabukhov.app.translator.model.AppStateStopWatch
import com.kalabukhov.app.translator.ui.repository.RepositoryStopWatch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class StopWatch : AppCompatActivity() {

    private val repositoryStopWatch: RepositoryStopWatch by inject()
    private val viewModel: StopWatchViewModel by viewModel()

    private var startOrStop = true

    private lateinit var binding: ActivityStopWatchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStopWatchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
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