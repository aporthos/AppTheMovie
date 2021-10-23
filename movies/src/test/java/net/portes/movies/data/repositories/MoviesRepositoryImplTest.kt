package net.portes.movies.data.repositories

import net.portes.movies.domain.datasources.MoviesDataSource
import net.portes.shared.models.Either
import net.portes.shared.models.Failure
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

/**
 * @author amadeus.portes
 */
@RunWith(MockitoJUnitRunner.StrictStubs::class)
class MoviesRepositoryImplTest {
    @Mock
    private lateinit var dataSource: MoviesDataSource

    private val repository by lazy {
        MoviesRepositoryImpl(dataSource)
    }

    @Test
    fun `validate repository get movies success`() {
        // Given
        `when`(dataSource.getMovies("")).thenReturn(Either.Right(emptyList()))

        // When
        val data = repository.getMovies("")

        // Then
        assertEquals(true, data is Either.Right)
    }

    @Test
    fun `validate repository get ipc failed`() {
        // Given
        `when`(dataSource.getMovies("")).thenReturn(Either.Left(Failure.ServerError))

        // When
        val data = repository.getMovies("")

        // Then
        assertEquals(true, data is Either.Left)
    }

    @Test
    fun `validate repository get ipc network not available`() {
        // Given
        `when`(dataSource.getMovies("")).thenReturn(Either.Left(Failure.ServerError))

        // When
        val data = repository.getMovies("")

        // Then
        assertEquals(true, data is Either.Left)
    }
}