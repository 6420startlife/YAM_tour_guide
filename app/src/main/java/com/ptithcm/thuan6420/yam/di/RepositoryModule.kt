package com.ptithcm.thuan6420.yam.di

import com.ptithcm.thuan6420.yam.data.local.AppSharedPreferences
import com.ptithcm.thuan6420.yam.data.remote.ApiHelper
import com.ptithcm.thuan6420.yam.data.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    @ViewModelScoped
    fun provideAuthRepository(
        apiHelper: ApiHelper,
        appSharedPreferences: AppSharedPreferences
    ): AuthRepository {
        return AuthRepository(apiHelper, appSharedPreferences)
    }

    @Provides
    @ViewModelScoped
    fun provideRoomRepository(
        apiHelper: ApiHelper, appSharedPreferences: AppSharedPreferences): RoomRepository {
        return RoomRepository(apiHelper, appSharedPreferences)
    }

    @Provides
    @ViewModelScoped
    fun provideAddressRepository(apiHelper: ApiHelper, appSharedPreferences: AppSharedPreferences): MapsRepository {
        return MapsRepository(apiHelper, appSharedPreferences)
    }

    @Provides
    @ViewModelScoped
    fun provideStationRepository(apiHelper: ApiHelper, appSharedPreferences: AppSharedPreferences): StationRepository {
        return StationRepository(apiHelper, appSharedPreferences)
    }

}