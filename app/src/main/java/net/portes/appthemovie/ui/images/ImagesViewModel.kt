package net.portes.appthemovie.ui.images

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.portes.images.domain.usecases.ImageLoadUseCase
import net.portes.shared.models.Failure
import net.portes.shared.ui.base.ViewState

/**
 * @author amadeus.portes
 */
class ImagesViewModel @ViewModelInject constructor(private val imageLoadUseCase: ImageLoadUseCase) :
    ViewModel() {

    private val _getImageLoad = MutableLiveData<ViewState<Boolean>>()
    val getImageLoad: LiveData<ViewState<Boolean>>
        get() = _getImageLoad

    fun loadImage(url: String) {
        _getImageLoad.value = ViewState.Loading()
        viewModelScope.launch {
            imageLoadUseCase(url).fold(::getImageLoadFailure, ::getImageLoadSuccess)
        }
    }

    private fun getImageLoadSuccess(imageLoad: Boolean) {
        _getImageLoad.value = ViewState.Success(imageLoad)
    }

    private fun getImageLoadFailure(failure: Failure) {
        _getImageLoad.value = ViewState.Error(-1)
    }
}