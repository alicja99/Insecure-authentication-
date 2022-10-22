package com.mvi.insecureauthentication.ui.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvi.insecureauthentication.domain.usecase.LoginUseCase
import com.mvi.insecureauthentication.domain.usecase.RegisterUseCase
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
class RegisterViewModel @Inject constructor(private val registerUseCase: RegisterUseCase) :
    ContainerHost<RegisterState, RegisterEffect>, ViewModel() {
    override val container: Container<RegisterState, RegisterEffect> = container(RegisterState())

    fun register() {
        viewModelScope.launch {
            intent {
                registerUseCase(state.email, state.password, state.name)
                    .whenSuccess {
                        postSideEffect(RegisterEffect.RegisterSuccess)
                    }.whenError {
                        postSideEffect(RegisterEffect.RegisterError(exception))
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

    fun onNameChange(text: String) = intent {
        reduce { state.copy(name = text) }
    }
}


data class RegisterState(
    val email: String = "",
    val password: String = "",
    val name: String = ""
)

sealed class RegisterEffect {
    object RegisterSuccess : RegisterEffect()
    data class RegisterError(val exception: Exception) : RegisterEffect()
}