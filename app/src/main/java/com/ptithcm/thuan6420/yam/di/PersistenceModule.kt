package com.ptithcm.thuan6420.yam.di

import android.content.Context
import com.ptithcm.thuan6420.yam.data.local.AppSharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {
    @Provides
    @Singleton
    fun provideAppSharedPreferences(@ApplicationContext context: Context): AppSharedPreferences {
        return AppSharedPreferences(context)
    }
}