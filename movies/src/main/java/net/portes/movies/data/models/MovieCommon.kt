package net.portes.movies.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @author amadeus.portes
 */
@JsonClass(generateAdapter = true)
data class MovieCommon(
    @Json(name = "page")
    val page: Int,
    @Json(name = "total_pages")
    val total_pages: Int,
    @Json(name = "total_results")
    val total_results: Int,
    @Json(name = "results")
    val results: List<MovieResponse>,
)