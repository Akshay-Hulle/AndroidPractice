package com.supremology.travelguide2.di

import android.content.Context
import com.supremology.travelguide2.automotive.location.LocationTracker
import com.supremology.travelguide2.domain.repository.PlaceRepository
import com.supremology.travelguide2.data.repository.PlaceRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindPlaceRepository(
        impl: PlaceRepositoryImpl
    ): PlaceRepository
}

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {
    @Provides
    fun provideLocationTracker(@ApplicationContext context: Context): LocationTracker {
        return LocationTracker(context)
    }
}
