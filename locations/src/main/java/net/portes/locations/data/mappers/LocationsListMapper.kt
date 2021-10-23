package net.portes.locations.data.mappers

import net.portes.locations.data.models.LocationResponse
import net.portes.locations.domain.models.LocationDto
import net.portes.shared.data.BaseMapper
import javax.inject.Inject

/**
 * @author amadeus.portes
 */
class LocationsListMapper @Inject constructor(private val locationMapper: LocationMapper): BaseMapper<List<LocationResponse>, List<LocationDto>> {
    override fun mapFrom(from: List<LocationResponse>): List<LocationDto> =
        mutableListOf<LocationDto>().apply {
            from.map {
                add(locationMapper.mapFrom(it))
            }
        }
}