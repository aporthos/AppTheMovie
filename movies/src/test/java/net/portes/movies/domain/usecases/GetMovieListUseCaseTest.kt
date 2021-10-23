package net.portes.movies.domain.usecases

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import net.portes.movies.domain.repositories.MoviesRepository
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
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner.StrictStubs::class)
class GetMovieListUseCaseTest {
    @Mock
    private lateinit var repository: MoviesRepository

    private val useCase by lazy {
        GetMovieListUseCase(TestCoroutineDispatcher(), repository)
    }

    @Test
    fun `validation use case success`() = runBlockingTest {
        `when`(repository.getMovies("")).thenReturn(Either.Right(emptyList()))

        val ipcUseCase = useCase("")
        assertEquals(true, ipcUseCase is Either.Right)
    }

    @Test
    fun `validation use case failed`() = runBlockingTest {
        `when`(repository.getMovies("")).thenReturn(Either.Left(Failure.ServerError))

        val ipcUseCase = useCase("")
        assertEquals(true, ipcUseCase is Either.Left)
    }
}