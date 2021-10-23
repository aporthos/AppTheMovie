package net.portes.locations.domain.datasource

import net.portes.locations.domain.models.LocationDto
import net.portes.shared.models.Either
import net.portes.shared.models.Failure

/**
 * @author amadeus.portes
 */
interface LocationsDataSource {
    suspend fun getLocations(): Either<Failure, List<LocationDto>>
    suspend fun saveLocation(locationDto: LocationDto): Either<Failure, Boolean>
}