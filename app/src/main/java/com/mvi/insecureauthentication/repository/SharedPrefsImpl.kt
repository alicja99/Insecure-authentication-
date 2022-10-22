package com.mvi.insecureauthentication.repository

import android.content.Context
import android.content.SharedPreferences
import com.mvi.insecureauthentication.repository.exceptions.InvalidData
import com.mvi.insecureauthentication.repository.exceptions.UserAlreadyExists
import com.mvi.insecureauthentication.repository.exceptions.UserNotFoundException
import com.mvi.insecureauthentication.repository.model.User
import com.mvi.insecureauthentication.repository.utils.Result
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.reflect.Type
import java.util.*
import javax.inject.Inject

const val SHARED_PREFS = "SharedPrefs"
const val DEF_VALUE = "Default"

class SharedPrefsImpl @Inject constructor(
    @ApplicationContext context: Context,
    private val moshi: Moshi
) : SharedPrefs {
    private val userAdapter = moshi.adapter(User::class.java)
    private val type: Type = Types.newParameterizedType(List::class.java, User::class.java)
    private val usersAdapter: JsonAdapter<List<User>> = moshi.adapter(type)

    override val data: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)

    override fun getStringValue(key: String): String = data.getString(key, DEF_VALUE) ?: ""

    override fun setStringValue(key: String, value: String) {
        data.edit().putString(key, value).apply()
    }

    override fun login(email: String, password: String): Result<User> {
        val usersJson = getStringValue(ALL_USERS)
        val users = usersAdapter.fromJson(usersJson) ?: emptyList()

        val match = users.find { it.email == email && it.password == password }

        match?.let {
            return Result.Success(it)
        } ?: return Result.Error(UserNotFoundException)
    }

    override fun register(email: String, password: String, name: String): Result<User> {
        val usersJson = getStringValue(ALL_USERS)
        val users = usersAdapter.fromJson(usersJson)?.toMutableList() ?: mutableListOf()

        val match = users.find { it.email == email && it.password == password }
        if (match !== null) {
            return Result.Error(UserAlreadyExists)
        }

        val newUser = User(id = generateRandomId(), email = email, password = password, name = name)
        users.add(newUser)
        val updatedUsersJson = usersAdapter.toJson(users)

        setStringValue(ALL_USERS, updatedUsersJson)

        return Result.Success(newUser)
    }

    override fun setCurrentUser(user: User) {
        val userJson = userAdapter.toJson(user)
        setStringValue(CURRENT_USER, userJson)
    }

    override fun getCurrentUser(): Result<User> {
        val userJson = getStringValue(CURRENT_USER)
        val user = userAdapter.fromJson(userJson)
        user?.let {
            return Result.Success(it)
        } ?: return Result.Error(InvalidData)
    }

    override fun logout(): Result<Boolean> {
        setStringValue(CURRENT_USER, "")
        return Result.Success(true)
    }

    private fun generateRandomId(): String = UUID.randomUUID().toString().substring(0, 15)

    companion object Keys {
        const val CURRENT_USER = "current_user"
        const val ALL_USERS = "all_users"
    }
}