package net.portes.movies.data.mappers

import net.portes.movies.data.MockFactory.response
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

/**
 * @author amadeus.portes
 */
@RunWith(MockitoJUnitRunner.StrictStubs::class)
class MovieListMapperTest {

    private val mapper by lazy {
        MovieListMapper(MovieMapper())
    }

    @Test
    fun `verify transform`() {
        with(mapper.mapFrom(response())) {
            assertEquals(first().adult, response().results.first().adult)
            assertEquals(first().backdropPath, response().results.first().backdropPath)
            assertEquals(first().genreIds, response().results.first().genreIds)
            assertEquals(first().id, response().results.first().id)
            assertEquals(first().originalLanguage, response().results.first().originalLanguage)
            assertEquals(first().originalTitle, response().results.first().originalTitle)
            assertEquals(first().overview, response().results.first().overview)
            assertEquals(first().popularity, response().results.first().popularity, 0.0)
            assertEquals(first().imageUrl, "https://image.tmdb.org/t/p/w500")
            assertEquals(first().releaseDate, response().results.first().releaseDate)
            assertEquals(first().title, response().results.first().title)
            assertEquals(first().voteAverage, response().results.first().voteAverage, 0.0)
            assertEquals(first().voteCount, response().results.first().voteCount)
        }
    }
}