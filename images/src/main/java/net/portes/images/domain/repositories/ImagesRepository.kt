package net.portes.images.domain.repositories

import net.portes.shared.models.Either
import net.portes.shared.models.Failure

/**
 * @author amadeus.portes
 */
interface ImagesRepository {
    suspend fun loadImage(url: String): Either<Failure, Boolean>
}