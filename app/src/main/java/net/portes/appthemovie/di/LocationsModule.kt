package net.portes.appthemovie.di

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import net.portes.locations.data.datasource.LocationsDataSourceImpl
import net.portes.locations.data.repositories.LocationsRepositoryImpl
import net.portes.locations.domain.datasource.LocationsDataSource
import net.portes.locations.domain.repositories.LocationsRepository

/**
 * @author amadeus.portes
 */
@Module
@InstallIn(ApplicationComponent::class)
object LocationsModule {
    @Provides
    fun providesFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    fun providesLocationDataSource(locationDataSourceImpl: LocationsDataSourceImpl): LocationsDataSource =
        locationDataSourceImpl

    @Provides
    fun providesLocationsRepository(locationsRepositoryImpl: LocationsRepositoryImpl): LocationsRepository =
        locationsRepositoryImpl

}