package net.portes.shared.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import net.portes.shared.models.Either
import net.portes.shared.models.Failure

/**
 * @author amadeus.portes
 */
abstract class UseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {
    protected abstract suspend fun execute(params: P): Either<Failure, R>

    suspend operator fun invoke(parameters: P): Either<Failure, R> {
        return withContext(coroutineDispatcher) {
            execute(parameters)
        }
    }
}