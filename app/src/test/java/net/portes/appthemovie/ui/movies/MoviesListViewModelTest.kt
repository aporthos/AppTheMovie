package net.portes.appthemovie.ui.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import net.portes.movies.domain.usecases.GetMovieListUseCase
import net.portes.shared.extensions.getThisValue
import net.portes.shared.models.Either
import net.portes.shared.models.Failure
import net.portes.shared.ui.base.ViewState
import org.junit.Assert.*
import org.junit.Rule
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
class MoviesListViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var useCase: GetMovieListUseCase

    private val viewModel by lazy {
        MoviesListViewModel(useCase)
    }

    @Test
    fun `validate get list ipc success`() = mainCoroutineRule.runBlockingTest {
        // Given
        mainCoroutineRule.testDispatcher.pauseDispatcher()
        `when`(useCase("")).thenReturn(Either.Right(emptyList()))

        // When
        viewModel.getMoviesList("")

        // Then
        assertTrue(viewModel.getMoviesList.getThisValue() is ViewState.Loading)
        mainCoroutineRule.testDispatcher.resumeDispatcher()
        assertTrue(viewModel.getMoviesList.getThisValue() is ViewState.Success)
    }

    @Test
    fun `validate get list ipc failed`() = mainCoroutineRule.runBlockingTest {
        // Given
        mainCoroutineRule.testDispatcher.pauseDispatcher()
        `when`(useCase("")).thenReturn(Either.Left(Failure.ServerError))


        // When
        viewModel.getMoviesList("")

        // Then
        assertTrue(viewModel.getMoviesList.getThisValue() is ViewState.Loading)
        mainCoroutineRule.testDispatcher.resumeDispatcher()
        assertTrue(viewModel.getMoviesList.getThisValue() is ViewState.Error)
    }

}