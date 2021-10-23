package net.portes.locations.domain.usecases

import kotlinx.coroutines.CoroutineDispatcher
import net.portes.locations.data.models.LocationResponse
import net.portes.locations.domain.models.LocationDto
import net.portes.locations.domain.repositories.LocationsRepository
import net.portes.shared.di.IoDispatcher
import net.portes.shared.domain.UseCase
import net.portes.shared.models.Either
import net.portes.shared.models.Failure
import javax.inject.Inject

/**
 * @author amadeus.portes
 */
class LocationsGetUseCase @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val repository: LocationsRepository
) : UseCase<Unit, List<LocationDto>>(dispatcher) {
    override suspend fun execute(params: Unit): Either<Failure, List<LocationDto>> =
        repository.getLocations()
}