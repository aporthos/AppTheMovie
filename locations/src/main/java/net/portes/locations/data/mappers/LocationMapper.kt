package net.portes.locations.data.mappers

import net.portes.locations.data.models.LocationResponse
import net.portes.locations.domain.models.LocationDto
import net.portes.shared.data.BaseMapper
import javax.inject.Inject

/**
 * @author amadeus.portes
 */
class LocationMapper @Inject constructor(): BaseMapper<LocationResponse, LocationDto> {
    override fun mapFrom(from: LocationResponse): LocationDto = LocationDto(
        time = from.time,
        latitude = from.latitude
    )
}