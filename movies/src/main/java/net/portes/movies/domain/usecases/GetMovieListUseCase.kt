package net.portes.movies.domain.usecases

import kotlinx.coroutines.CoroutineDispatcher
import net.portes.shared.di.IoDispatcher
import net.portes.shared.domain.UseCase
import net.portes.shared.models.Either
import net.portes.shared.models.Failure
import net.portes.movies.domain.models.MovieDto
import net.portes.movies.domain.repositories.MoviesRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author amadeus.portes
 */
@Singleton
class GetMovieListUseCase @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val repository: MoviesRepository
) : UseCase<String, List<MovieDto>>(dispatcher) {
    override suspend fun execute(params: String): Either<Failure, List<MovieDto>> =
        repository.getMovies(params)
}