package net.portes.movies.data

import net.portes.movies.data.models.MovieCommon
import net.portes.movies.data.models.MovieResponse

/**
 * @author amadeus.portes
 */
object MockFactory {
    fun response() = MovieCommon(page = 0, total_pages = 0, total_results = 0, results = listOf(movie()))

    fun movie() = MovieResponse(
        adult = false,
        backdropPath = "",
        genreIds = listOf(),
        id = 0,
        originalLanguage = "",
        originalTitle = "",
        overview = "",
        popularity = 0.0,
        posterPath = "",
        releaseDate = "",
        title = "",
        video = false,
        voteAverage = 0.0,
        voteCount = 0
    )
}