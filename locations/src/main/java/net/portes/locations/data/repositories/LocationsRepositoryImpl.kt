package net.portes.locations.data.repositories

import net.portes.locations.domain.datasource.LocationsDataSource
import net.portes.locations.domain.models.LocationDto
import net.portes.locations.domain.repositories.LocationsRepository
import net.portes.shared.models.Either
import net.portes.shared.models.Failure
import javax.inject.Inject

/**
 * @author amadeus.portes
 */
class LocationsRepositoryImpl @Inject constructor(private val dataSource: LocationsDataSource): LocationsRepository {
    override suspend fun getLocations(): Either<Failure, List<LocationDto>> = dataSource.getLocations()
    override suspend fun saveLocation(locationDto: LocationDto): Either<Failure, Boolean> = dataSource.saveLocation(locationDto)
}