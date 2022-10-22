package com.mvi.insecureauthentication.domain.usecase

import com.mvi.insecureauthentication.repository.SharedPrefs
import com.mvi.insecureauthentication.repository.model.User
import com.mvi.insecureauthentication.repository.utils.Result
import dagger.Reusable
import javax.inject.Inject

@Reusable
class RegisterUseCase @Inject constructor(private val sharedPrefs: SharedPrefs) {
    operator fun invoke(
        email: String,
        password: String,
        name: String
    ): Result<User> = when (val result = sharedPrefs.register(
        email,
        password,
        name
    )) {
        is Result.Success -> result
        is Result.Error -> Result.Error(result.exception)
    }
}