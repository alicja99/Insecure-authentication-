package com.mvi.insecureauthentication.repository.di

import android.content.Context
import com.mvi.insecureauthentication.repository.SharedPrefs
import com.mvi.insecureauthentication.repository.SharedPrefsImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Provides
    @Reusable
    fun provideSharedPrefs(@ApplicationContext context: Context, moshi: Moshi): SharedPrefs =
        SharedPrefsImpl(context, moshi)
}