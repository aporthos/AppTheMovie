package net.portes.images.domain.datasource

import net.portes.shared.models.Either
import net.portes.shared.models.Failure

/**
 * @author amadeus.portes
 */
interface ImagesDataSource {
    suspend fun loadImage(url: String): Either<Failure, Boolean>
}