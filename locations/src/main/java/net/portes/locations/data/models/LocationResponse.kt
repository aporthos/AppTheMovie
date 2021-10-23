package net.portes.locations.data.models

import com.google.firebase.firestore.GeoPoint

/**
 * @author amadeus.portes
 */
data class LocationResponse(val time: String = "", val latitude: GeoPoint = GeoPoint(0.0, 0.0))