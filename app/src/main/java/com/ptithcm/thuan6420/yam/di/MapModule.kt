package com.ptithcm.thuan6420.yam.di

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object MapModule {
    @Provides
    @ActivityScoped
    fun provideFusedLocationClient(@ActivityContext context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    @Provides
    @ActivityScoped
    fun provideLocationRequest(): LocationRequest {
        return LocationRequest.create().apply {
            interval = 300000
            fastestInterval = 300000
            priority = Priority.PRIORITY_HIGH_ACCURACY
            maxWaitTime = 300000
        }
    }

}