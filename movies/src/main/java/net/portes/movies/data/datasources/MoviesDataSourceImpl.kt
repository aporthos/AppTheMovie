package net.portes.movies.data.datasources

import net.portes.shared.extensions.call
import net.portes.shared.models.Either
import net.portes.shared.models.Failure
import net.portes.shared.utils.NetworkHandler
import net.portes.movies.data.mappers.MovieListMapper
import net.portes.movies.data.services.MoviesService
import net.portes.movies.domain.datasources.MoviesDataSource
import net.portes.movies.domain.models.MovieDto
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author amadeus.portes
 */
@Singleton
class MoviesDataSourceImpl @Inject constructor(
    private val service: MoviesService,
    private val networkHandler: NetworkHandler,
    private val movieListMapper: MovieListMapper
) : MoviesDataSource {
    override fun getMovies(query: String): Either<Failure, List<MovieDto>> {
        return when (networkHandler.isNetworkAvailable()) {
            true -> service.movie(query = query, page = 1)
                .call({ movieListMapper.mapFrom(it) }, movieListMapper.toDefault())
            false -> Either.Left(Failure.NetworkConnection)
        }
    }
}