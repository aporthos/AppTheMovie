package net.portes.movies.domain.datasources

import net.portes.shared.models.Either
import net.portes.shared.models.Failure
import net.portes.movies.domain.models.MovieDto

/**
 * @author amadeus.portes
 */
interface MoviesDataSource {
    fun getMovies(query: String): Either<Failure, List<MovieDto>>
}