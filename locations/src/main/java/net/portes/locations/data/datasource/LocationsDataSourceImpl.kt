package net.portes.locations.data.datasource

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import net.portes.locations.data.mappers.LocationsListMapper
import net.portes.locations.data.models.LocationResponse
import net.portes.locations.domain.datasource.LocationsDataSource
import net.portes.locations.domain.models.LocationDto
import net.portes.shared.models.Either
import net.portes.shared.models.Failure
import net.portes.shared.utils.NetworkHandler
import javax.inject.Inject

/**
 * @author amadeus.portes
 */
class LocationsDataSourceImpl @Inject constructor(
    private val networkHandler: NetworkHandler,
    private val firestore: FirebaseFirestore,
    private val LocationsListMapper: LocationsListMapper
) : LocationsDataSource {
    companion object {
        private const val COLLECTION_LOCATIONS = "locations"
    }

    override suspend fun getLocations(): Either<Failure, List<LocationDto>> =
        when (networkHandler.isNetworkAvailable()) {
            true -> toLoadImage()
            false -> Either.Left(Failure.NetworkConnection)
        }

    override suspend fun saveLocation(locationDto: LocationDto): Either<Failure, Boolean> {
        return when (networkHandler.isNetworkAvailable()) {
            true -> toSaveLocation(locationDto)
            false -> Either.Left(Failure.NetworkConnection)
        }
    }

    private suspend fun toSaveLocation(location: LocationDto): Either<Failure, Boolean> {
        val document = firestore.collection(COLLECTION_LOCATIONS).document().set(location).await()
        return document?.let {
            Either.Right(true)
        } ?: run {
            Either.Left(Failure.ServerError)
        }
    }

    private suspend fun toLoadImage(): Either<Failure, List<LocationDto>> {
        return try {
            val list = firestore.collection(COLLECTION_LOCATIONS).get().await()
                .toObjects(LocationResponse::class.java)
            return Either.Right(LocationsListMapper.mapFrom(list))
        } catch (e: Exception) {
            Either.Left(Failure.ServerError)
        }
    }
}