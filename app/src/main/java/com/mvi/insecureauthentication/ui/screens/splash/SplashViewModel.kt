package com.mvi.insecureauthentication.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvi.insecureauthentication.domain.usecase.GetCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.viewmodel.container

@HiltViewModel
class SplashViewModel @Inject constructor(private val getCurrentUser: GetCurrentUserUseCase) :
    ContainerHost<SplashState, SplashEffect>, ViewModel() {
    override val container: Container<SplashState, SplashEffect> = container(SplashState())

    init {
        viewModelScope.launch {
            intent {
                getCurrentUser()
                    .whenSuccess {
                        postSideEffect(SplashEffect.NavigateToHome)
                    }.whenError {
                        postSideEffect(SplashEffect.NavigateToAuthentication)
                    }
            }
        }
    }
}

data class SplashState(
    val isLoading: Boolean = false,
)

sealed class SplashEffect {
    object NavigateToAuthentication : SplashEffect()
    object NavigateToHome : SplashEffect()
}