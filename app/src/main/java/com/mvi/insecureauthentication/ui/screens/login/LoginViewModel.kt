package com.mvi.insecureauthentication.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvi.insecureauthentication.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) :
    ContainerHost<LoginState, LoginEffect>, ViewModel() {
    override val container: Container<LoginState, LoginEffect> = container(LoginState())

    fun login() {
        viewModelScope.launch {
            intent {
                loginUseCase(state.email, state.password)
                    .whenSuccess {
                        postSideEffect(LoginEffect.LoginSuccess)
                    }.whenError {
                        postSideEffect(LoginEffect.InvalidData(exception))
                    }
            }
        }
    }

    fun onEmailChange(text: String) = intent {
        reduce { state.copy(email = text) }
    }

    fun onPasswordChange(text: String) = intent {
        reduce { state.copy(password = text) }
    }

    fun onNavigateToRegistration() = intent {
        postSideEffect(LoginEffect.NavigateToRegistration)
    }
}


data class LoginState(
    val email: String = "",
    val password: String = "",
)

sealed class LoginEffect {
    object LoginSuccess : LoginEffect()
    object NavigateToRegistration : LoginEffect()
    data class InvalidData(val exception: Exception) : LoginEffect()
}