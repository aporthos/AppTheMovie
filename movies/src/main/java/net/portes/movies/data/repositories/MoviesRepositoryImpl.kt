package net.portes.movies.data.repositories

import net.portes.shared.models.Either
import net.portes.shared.models.Failure
import net.portes.movies.domain.datasources.MoviesDataSource
import net.portes.movies.domain.models.MovieDto
import net.portes.movies.domain.repositories.MoviesRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author amadeus.portes
 */
@Singleton
class MoviesRepositoryImpl @Inject constructor(private val dataSource: MoviesDataSource) :
    MoviesRepository {
    override fun getMovies(query: String): Either<Failure, List<MovieDto>> =
        dataSource.getMovies(query)

}