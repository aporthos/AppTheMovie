package net.portes.movies.data.services

import net.portes.movies.BuildConfig
import net.portes.movies.data.models.MovieCommon
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author amadeus.portes
 */
interface MoviesService {
    @GET("3/search/movie")
    fun movie(
        @Query("query") query: String,
        @Query("api_key") api_key: String = BuildConfig.API_KEY,
        @Query("page") page: Int
    ): Call<MovieCommon>
}