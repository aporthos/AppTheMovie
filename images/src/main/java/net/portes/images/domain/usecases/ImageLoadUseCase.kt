package net.portes.images.domain.usecases

import kotlinx.coroutines.CoroutineDispatcher
import net.portes.images.domain.repositories.ImagesRepository
import net.portes.shared.di.IoDispatcher
import net.portes.shared.domain.UseCase
import net.portes.shared.models.Either
import net.portes.shared.models.Failure
import javax.inject.Inject

/**
 * @author amadeus.portes
 */
class ImageLoadUseCase @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val repository: ImagesRepository
) : UseCase<String, Boolean>(dispatcher) {
    override suspend fun execute(params: String): Either<Failure, Boolean> = repository.loadImage(params)
}