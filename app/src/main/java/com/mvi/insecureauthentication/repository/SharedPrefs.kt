package com.mvi.insecureauthentication.repository

import android.content.SharedPreferences
import com.mvi.insecureauthentication.repository.model.User
import com.mvi.insecureauthentication.repository.utils.Result

interface SharedPrefs {
    val data: SharedPreferences
    fun getStringValue(key: String): String
    fun setStringValue(key: String, value: String)

    fun login(email: String, password: String): Result<User>
    fun register(email: String, password: String, name: String): Result<User>
    fun setCurrentUser(user: User)
    fun getCurrentUser(): Result<User>
    fun logout(): Result<Boolean>
}