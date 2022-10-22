package com.mvi.insecureauthentication.domain.usecase

import com.mvi.insecureauthentication.repository.SharedPrefs
import com.mvi.insecureauthentication.repository.utils.Result
import dagger.Reusable
import javax.inject.Inject

@Reusable
class LogoutUseCase @Inject constructor(private val sharedPrefs: SharedPrefs) {
    operator fun invoke(): Result<Boolean> = when (val result = sharedPrefs.logout()) {
        is Result.Success -> result
        is Result.Error -> Result.Error(result.exception)
    }
}