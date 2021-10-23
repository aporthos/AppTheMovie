package net.portes.movies.data.mappers

import net.portes.shared.data.BaseDefaultMapper
import net.portes.shared.data.BaseMapper
import net.portes.movies.data.models.MovieCommon
import net.portes.movies.domain.models.MovieDto
import javax.inject.Inject

/**
 * @author amadeus.portes
 */
class MovieListMapper @Inject constructor(private val movieMapper: MovieMapper) :
    BaseMapper<MovieCommon, List<MovieDto>>, BaseDefaultMapper<MovieCommon> {
    override fun mapFrom(from: MovieCommon): List<MovieDto> =
        mutableListOf<MovieDto>().apply {
            from.results.map {
                add(movieMapper.mapFrom(it))
            }
        }

    override fun toDefault(): MovieCommon = MovieCommon(0, 0, 0, emptyList())
}