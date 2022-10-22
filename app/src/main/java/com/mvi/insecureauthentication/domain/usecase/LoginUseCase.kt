package com.mvi.insecureauthentication.domain.usecase

import com.mvi.insecureauthentication.repository.SharedPrefs
import com.mvi.insecureauthentication.repository.model.User
import dagger.Reusable
import javax.inject.Inject
import com.mvi.insecureauthentication.repository.utils.Result

@Reusable
class LoginUseCase @Inject constructor(private val sharedPrefs: SharedPrefs) {
    operator fun invoke(
        email: String,
        password: String
    ): Result<User> = when (val result = sharedPrefs.login(
        email,
        password
    )) {
        is Result.Success -> {
            sharedPrefs.setCurrentUser(result.data)
            result
        }
        is Result.Error -> Result.Error(result.exception)
    }
}