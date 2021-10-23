package net.portes.movies.data.datasources

import net.portes.movies.data.mappers.MovieListMapper
import net.portes.movies.data.mappers.MovieMapper
import net.portes.movies.data.models.MovieCommon
import net.portes.movies.data.services.MoviesService
import net.portes.shared.models.Either
import net.portes.shared.utils.NetworkHandler
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Call
import retrofit2.Response
import retrofit2.Response.error

/**
 * @author amadeus.portes
 */
@RunWith(MockitoJUnitRunner.StrictStubs::class)
class MoviesDataSourceImplTest {
    @Mock
    private lateinit var networkHandler: NetworkHandler

    @Mock
    private lateinit var service: MoviesService

    @Mock
    private lateinit var call: Call<MovieCommon>

    private val mapper by lazy {
        MovieListMapper(MovieMapper())
    }

    private val dataSource by lazy {
        MoviesDataSourceImpl(service, networkHandler, mapper)
    }

    @Test
    fun `validate data source get movies success`() {
        // Given
        `when`(call.execute()).thenReturn(Response.success(MovieCommon(0, 0, 0, emptyList())))
        `when`(service.movie(query = "", page = 1)).thenReturn(call)
        `when`(networkHandler.isNetworkAvailable()).thenReturn(true)

        // When
        val data = dataSource.getMovies("")

        // Then
        assertEquals(true, data is Either.Right)
    }

    @Test
    fun `validate data source get movies failed`() {
        // Given
        `when`(call.execute()).thenReturn(error(401, anyError()))
        `when`(service.movie(query = "", page = 1)).thenReturn(call)
        `when`(networkHandler.isNetworkAvailable()).thenReturn(true)

        // When
        val data = dataSource.getMovies("")

        //Then
        assertEquals(true, data is Either.Left)
    }

    @Test
    fun `validate data source get movies is network not available`() {
        // Given
        `when`(networkHandler.isNetworkAvailable()).thenReturn(false)

        // When
        val data = dataSource.getMovies("")

        //Then
        assertEquals(true, data is Either.Left)
    }

    fun anyError(): ResponseBody = ResponseBody.create(
        MediaType.parse("application/json"), """
                {
                    "code": "InvalidCredentials",
                    "message": "That hash, timestamp and key combination is invalid."
                }
            """.trimIndent()

    )

}