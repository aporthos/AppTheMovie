package net.portes.locations.domain.models

import com.google.firebase.firestore.GeoPoint

/**
 * @author amadeus.portes
 */
data class LocationDto(val time: String = "", val latitude: GeoPoint = GeoPoint(0.0, 0.0))