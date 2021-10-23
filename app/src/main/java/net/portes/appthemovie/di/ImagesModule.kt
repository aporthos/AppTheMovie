package net.portes.appthemovie.di

import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import net.portes.images.data.datasource.ImagesDataSourceImpl
import net.portes.images.data.repositories.ImagesRepositoryImpl
import net.portes.images.domain.datasource.ImagesDataSource
import net.portes.images.domain.repositories.ImagesRepository

/**
 * @author amadeus.portes
 */
@Module
@InstallIn(ApplicationComponent::class)
object ImagesModule {

    @Provides
    fun providesFirebaseStorage(): FirebaseStorage = FirebaseStorage.getInstance()

    @Provides
    fun providesImagesDataSource(imagesDataSourceImpl: ImagesDataSourceImpl): ImagesDataSource =
        imagesDataSourceImpl

    @Provides
    fun providesImagesRepository(imagesRepositoryImpl: ImagesRepositoryImpl): ImagesRepository =
        imagesRepositoryImpl
}