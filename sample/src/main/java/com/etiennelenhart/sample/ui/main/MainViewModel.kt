package com.etiennelenhart.sample.ui.main

import com.etiennelenhart.eiffel.state.Action
import com.etiennelenhart.eiffel.state.State
import com.etiennelenhart.eiffel.state.update
import com.etiennelenhart.eiffel.viewmodel.EiffelViewModel

data class SampleData(
    val name: String,
    val rating: Double
) {

    /**
     * Showcase overriding the toString for the LoggerPlugin
     */
    override fun toString(): String {
        return "The movie $name has a IMDB rating of $rating"
    }
}

data class MainState(
    val name: String = "",
    val isLoading: Boolean = false,
    val hasLoadError: Boolean = false,
    val sampleData: List<SampleData> = listOf()
) : State

sealed class MainAction : Action {
    data class Init(val name: String) : MainAction()
    object LoadData : MainAction()
    object LoadFailed : MainAction()
    data class LoadSuccess(val data: List<SampleData>) : MainAction()
}

class MainViewModel : EiffelViewModel<MainState, MainAction>(
    initialState = MainState(),
    update = update { state, action ->
        when (action) {
            is MainAction.Init -> state.copy(
                name = action.name,
                isLoading = false,
                hasLoadError = false
            )
            MainAction.LoadData -> state.copy(isLoading = true, hasLoadError = false)
            MainAction.LoadFailed -> state.copy(isLoading = false, hasLoadError = true)
            is MainAction.LoadSuccess -> state.copy(
                isLoading = false,
                hasLoadError = false,
                sampleData = action.data
            )
        }
    }
) {

    fun onButtonClick() {
        // TODO - Simulate loading data from the server
    }
}
