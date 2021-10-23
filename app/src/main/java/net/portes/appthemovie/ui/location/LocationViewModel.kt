package net.portes.appthemovie.ui.location

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.portes.locations.data.models.LocationResponse
import net.portes.locations.domain.models.LocationDto
import net.portes.locations.domain.usecases.LocationSaveUseCase
import net.portes.locations.domain.usecases.LocationsGetUseCase
import net.portes.movies.domain.models.MovieDto
import net.portes.shared.models.Failure
import net.portes.shared.ui.base.BaseViewModel
import net.portes.shared.ui.base.ViewState

class LocationViewModel @ViewModelInject constructor(
    private val locationsGetUseCase: LocationsGetUseCase,
    private val locationSaveUseCase: LocationSaveUseCase
) : BaseViewModel() {

    private val _getLocationsList = MutableLiveData<ViewState<List<LocationDto>>>()
    val getLocationsList: LiveData<ViewState<List<LocationDto>>>
        get() = _getLocationsList

    private val _toSaveLocation = MutableLiveData<ViewState<Boolean>>()
    val toSaveLocation: LiveData<ViewState<Boolean>>
        get() = _toSaveLocation

    fun getLocations() {
        _getLocationsList.value = ViewState.Loading()
        viewModelScope.launch {
            locationsGetUseCase(Unit).fold(::getLocationsListFailure, ::getLocationsListSuccess)
        }
    }

    fun saveLocation(location: LocationDto) {
        _getLocationsList.value = ViewState.Loading()
        viewModelScope.launch {
            locationSaveUseCase(location).fold(::toSaveLocationsFailure, ::toSaveLocationsSuccess)
        }
    }

    private fun getLocationsListSuccess(ipcList: List<LocationDto>) {
        _getLocationsList.value = ViewState.Success(ipcList)
    }

    private fun getLocationsListFailure(failure: Failure) {
        _getLocationsList.value = ViewState.Error(proccessError(failure))
    }

    private fun toSaveLocationsSuccess(result: Boolean) {
        _toSaveLocation.value = ViewState.Success(result)
    }

    private fun toSaveLocationsFailure(failure: Failure) {
        _toSaveLocation.value = ViewState.Error(proccessError(failure))
    }

}