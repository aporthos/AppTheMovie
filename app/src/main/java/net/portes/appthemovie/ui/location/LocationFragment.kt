package net.portes.appthemovie.ui.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.location.LocationManager
import android.os.Looper
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import androidx.fragment.app.viewModels
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.GeoPoint
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog
import dagger.hilt.android.AndroidEntryPoint
import net.portes.appthemovie.R
import net.portes.appthemovie.databinding.FragmentLocationBinding
import net.portes.appthemovie.ui.base.BaseFragment
import net.portes.locations.domain.models.LocationDto
import net.portes.shared.extensions.observe
import net.portes.shared.extensions.toast
import net.portes.shared.ui.base.ViewState
import java.util.concurrent.TimeUnit


/**
 * @author amadeus.portes
 */
@AndroidEntryPoint
class LocationFragment : BaseFragment<FragmentLocationBinding>(), OnMapReadyCallback,
    EasyPermissions.PermissionCallbacks {

    companion object {
        private const val RC_ACCESS_LOCATION = 1
    }

    private val viewModel: LocationViewModel by viewModels()

    private var googleMap: GoogleMap? = null

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private var locationManager: LocationManager? = null

    override fun getLayoutRes(): Int = R.layout.fragment_location

    override fun initializeView() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)

        requestPermissions()
        val map = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        map.getMapAsync(this)
        map.getMapAsync {
            it.uiSettings.isZoomControlsEnabled = true
            it.uiSettings.isMyLocationButtonEnabled = true
        }
    }

    override fun initObservers() {
        observe(viewModel.getLocationsList, ::resultLocationsList)
        observe(viewModel.toSaveLocation, ::resultLocationsSave)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        viewModel.getLocations()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            SettingsDialog.Builder(requireActivity()).build().show()
        } else {
            requestPermissions()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        if (isLocationEnabled()) {
            getLocation()
        } else {
            val intent = Intent(ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }
    }

    private fun resultLocationsList(result: ViewState<List<LocationDto>>) {
        when (result) {
            is ViewState.Loading -> showLoader()
            is ViewState.Success -> {
                hideLoader()
                addMarkers(result.data)
            }
            is ViewState.Error -> {
                hideLoader()
            }
        }
    }

    private fun resultLocationsSave(result: ViewState<Boolean>) {
        when (result) {
            is ViewState.Loading -> showLoader()
            is ViewState.Success -> {
                hideLoader()
                toast(R.string.message_save_location_success)
            }
            is ViewState.Error -> {
                hideLoader()
            }
        }
    }

    private fun addMarkers(list: List<LocationDto>) {
        list.forEach {
            val marker = MarkerOptions().position(
                LatLng(
                    it.latitude.latitude,
                    it.latitude.longitude
                )
            ).title(it.time)
            googleMap?.addMarker(marker)
        }

        list.firstOrNull()?.let {
            val firstMarker = LatLng(it.latitude.latitude, it.latitude.longitude)
            googleMap?.moveCamera(CameraUpdateFactory.newLatLng(firstMarker))
            googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(firstMarker, 12.0f))
        }
    }

    private fun requestPermissions() {
        if (hasAccessLocation()) {
            if (isLocationEnabled()) {
                getLocation()
            } else {
                val intent = Intent(ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.message_permissions_read_write),
                RC_ACCESS_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = TimeUnit.MINUTES.toMillis(5)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)
        fusedLocationProviderClient.requestLocationUpdates(
            mLocationRequest,
            mLocationCallback,
            Looper.getMainLooper()
        )
    }

    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val lastLocation = locationResult.lastLocation
            val locationDto = LocationDto(
                time = "", GeoPoint(
                    lastLocation.latitude,
                    lastLocation.longitude
                )
            )
            viewModel.saveLocation(locationDto)
        }
    }

    private fun isLocationEnabled(): Boolean {
        locationManager = activity?.getSystemService(LOCATION_SERVICE) as LocationManager
        return locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true || locationManager?.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        ) == true
    }

    private fun hasAccessLocation(): Boolean = EasyPermissions.hasPermissions(
        requireContext(),
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
}