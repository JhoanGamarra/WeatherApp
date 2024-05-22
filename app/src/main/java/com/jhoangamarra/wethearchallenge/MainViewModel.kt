package com.jhoangamarra.wethearchallenge

import androidx.compose.runtime.mutableStateListOf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    coroutineContextProvider: CoroutineContextProvider
) : AbstractViewModel<MainViewModel.State, MainViewModel.Event>(
    initialState = State(),
    coroutineContextProvider = coroutineContextProvider
) {

    val visiblePermissionDialogQueue = mutableStateListOf<String>()
    private val _uiState: MutableStateFlow<State> = MutableStateFlow(State())
    val uiState = _uiState.asStateFlow()


    fun dismissDialog() {
        visiblePermissionDialogQueue.removeFirst()
    }

    fun validateStartDestination() {
        if (_uiState.value.isGrantedLocationPermission) {
            _uiState.update { it.copy(startDestination = "LandingRoute") }
        } else {
            _uiState.update { it.copy(startDestination = "WelcomeRoute") }
        }
    }

    fun onPermissionResult(
        permission: String,
        isGranted: Boolean
    ) {
        if (!isGranted && !visiblePermissionDialogQueue.contains(permission)) {
            visiblePermissionDialogQueue.add(permission)
            _uiState.update { it.copy(isGrantedLocationPermission = false) }

        } else {
            _uiState.update { it.copy(isGrantedLocationPermission = true) }
        }
    }

    data class State(
        val loading: Boolean = true,
        val startDestination: String = "WelcomeRoute",
        val isGrantedLocationPermission: Boolean = false
    )

    sealed interface Event {
        data object EnableLocation : Event
    }
}





