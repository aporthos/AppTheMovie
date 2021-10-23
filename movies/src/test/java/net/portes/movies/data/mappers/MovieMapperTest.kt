package net.portes.movies.data.mappers

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import net.portes.movies.data.MockFactory.movie

/**
 * @author amadeus.portes
 */
@RunWith(MockitoJUnitRunner.StrictStubs::class)
class MovieMapperTest {

    private val mapper by lazy {
        MovieMapper()
    }

    @Test
    fun `verify transform`() {
        with(mapper.mapFrom(movie())) {
            assertEquals(adult, movie().adult)
            assertEquals(backdropPath, movie().backdropPath)
            assertEquals(genreIds, movie().genreIds)
            assertEquals(id, movie().id)
            assertEquals(originalLanguage, movie().originalLanguage)
            assertEquals(originalTitle, movie().originalTitle)
            assertEquals(overview, movie().overview)
            assertEquals(popularity, movie().popularity, 0.0)
            assertEquals(imageUrl, "https://image.tmdb.org/t/p/w500")
            assertEquals(releaseDate, movie().releaseDate)
            assertEquals(title, movie().title)
            assertEquals(voteAverage, movie().voteAverage, 0.0)
            assertEquals(voteCount, movie().voteCount)
        }
    }
}