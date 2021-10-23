package net.portes.movies.data.mappers

import net.portes.shared.data.BaseMapper
import net.portes.movies.data.models.MovieResponse
import net.portes.movies.domain.models.MovieDto
import javax.inject.Inject

/**
 * @author amadeus.portes
 */
class MovieMapper @Inject constructor() : BaseMapper<MovieResponse, MovieDto> {
    companion object {
        const val TAG = "MovieMapper"
        private const val URL_IMAGE = "https://image.tmdb.org"
    }

    override fun mapFrom(from: MovieResponse): MovieDto = MovieDto(
        adult = from.adult,
        backdropPath = from.backdropPath,
        genreIds = from.genreIds,
        id = from.id,
        originalLanguage = from.originalLanguage,
        originalTitle = from.originalTitle,
        overview = from.overview,
        popularity = from.popularity,
        imageUrl = createImageUrl(from.posterPath),
        releaseDate = from.releaseDate,
        title = from.title,
        video = from.video,
        voteAverage = from.voteAverage,
        voteCount = from.voteCount
    )

    private fun createImageUrl(posterPath: String?): String? {
        return posterPath?.let {
            "$URL_IMAGE/t/p/w500$it"
        }
    }
}