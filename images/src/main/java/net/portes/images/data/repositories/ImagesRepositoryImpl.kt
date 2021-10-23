package net.portes.images.data.repositories

import net.portes.images.domain.datasource.ImagesDataSource
import net.portes.images.domain.repositories.ImagesRepository
import net.portes.shared.models.Either
import net.portes.shared.models.Failure
import javax.inject.Inject

/**
 * @author amadeus.portes
 */
class ImagesRepositoryImpl @Inject constructor(private val imagesDataSource: ImagesDataSource) :
    ImagesRepository {
    override suspend fun loadImage(url: String): Either<Failure, Boolean> =
        imagesDataSource.loadImage(url)
}