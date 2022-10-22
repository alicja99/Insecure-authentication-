package com.mvi.insecureauthentication.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvi.insecureauthentication.domain.usecase.GetCurrentUserUseCase
import com.mvi.insecureauthentication.domain.usecase.LogoutUseCase
import com.mvi.insecureauthentication.repository.model.User
import com.mvi.insecureauthentication.repository.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.Exception
import javax.inject.Inject
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val getCurrentUser: GetCurrentUserUseCase
) :
    ContainerHost<HomeState, HomeEffect>, ViewModel() {
    override val container: Container<HomeState, HomeEffect> = container(HomeState())

    fun logout() = intent {
        logoutUseCase().whenSuccess {
            postSideEffect(HomeEffect.NavigateToAuthentication)
        }.whenError {
            postSideEffect(HomeEffect.ShowError(this.exception))
        }
    }

    init {
        intent {
            when (val result = getCurrentUser()) {
                is Result.Success -> reduce { state.copy(user = result.data) }
                else -> Unit
            }
        }

    }
}

data class HomeState(
    val user: User? = null
)

sealed class HomeEffect {
    object NavigateToAuthentication : HomeEffect()
    data class ShowError(val exception: Exception) : HomeEffect()
}