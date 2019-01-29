package com.etiennelenhart.sample.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.etiennelenhart.eiffel.plugin.dispatchEiffelCustom
import com.etiennelenhart.eiffel.plugin.dispatchEiffelMessage
import com.etiennelenhart.eiffel.state.extension.observe
import com.etiennelenhart.sample.R
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainFragment : Fragment(), CoroutineScope {

    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext = job + Dispatchers.Main

    private val customObject = SampleData(
        name = "Top Gun",
        rating = 10.0
    )

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(viewModel) {
            dispatch(MainAction.Init("MainFragment: ${Math.random()}"))

            dispatchDelayedCustomMessage()

            state.observe(this@MainFragment) { renderState(it) }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        job.cancel()
    }

    // Don't ever do this... just a quick example.
    private fun renderState(state: MainState) {
        message?.text = state.name
        loading?.text =
            if (state.isLoading) "LOADING" else if (state.hasLoadError) "THERE WAS AN ERROR" else "not loading"
        movieList?.text = state.sampleData.map { it.toString() }.joinToString { "\n" }
    }

    private fun dispatchDelayedCustomMessage(delay: Long = 5000) {
        launch {
            delay(delay)
            viewModel.dispatchEiffelMessage("I'm a delayed message from ${delay}ms in the past!")
            dispatchedDelayedCustomObject(delay)
        }
    }

    private fun dispatchedDelayedCustomObject(delay: Long = 5000) {
        launch {
            delay(delay)
            viewModel.dispatchEiffelCustom(customObject)
        }
    }
}
