package net.portes.appthemovie.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import net.portes.movies.data.datasources.MoviesDataSourceImpl
import net.portes.movies.data.repositories.MoviesRepositoryImpl
import net.portes.movies.data.services.MoviesService
import net.portes.movies.domain.datasources.MoviesDataSource
import net.portes.movies.domain.repositories.MoviesRepository
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * @author amadeus.portes
 */
@Module
@InstallIn(ApplicationComponent::class)
object MoviesModule {

    @Singleton
    @Provides
    fun providesIpcService(retrofit: Retrofit): MoviesService =
        retrofit.create(MoviesService::class.java)

    @Singleton
    @Provides
    fun providesMoviesDataSource(moviesDataSourceImpl: MoviesDataSourceImpl): MoviesDataSource =
        moviesDataSourceImpl

    @Singleton
    @Provides
    fun providesMoviesRepository(moviesRepositoryImpl: MoviesRepositoryImpl): MoviesRepository =
        moviesRepositoryImpl
}