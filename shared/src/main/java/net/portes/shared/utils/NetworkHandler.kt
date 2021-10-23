package net.portes.shared.utils

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject

/**
 * @author amadeus.portes
 */
class NetworkHandler @Inject constructor(private val context: Context) {
    fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}