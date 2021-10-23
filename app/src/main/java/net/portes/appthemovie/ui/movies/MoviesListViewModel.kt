package net.portes.appthemovie.ui.movies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.portes.shared.models.Failure
import net.portes.shared.ui.base.BaseViewModel
import net.portes.shared.ui.base.ViewState
import net.portes.movies.domain.models.MovieDto
import net.portes.movies.domain.usecases.GetMovieListUseCase

class MoviesListViewModel @ViewModelInject constructor(private val getMovieListUseCase: GetMovieListUseCase) :
    BaseViewModel() {

    private val _getMoviesList = MutableLiveData<ViewState<List<MovieDto>>>()
    val getMoviesList: LiveData<ViewState<List<MovieDto>>>
        get() = _getMoviesList

    fun getMoviesList(query: String) {
        _getMoviesList.value = ViewState.Loading()
        viewModelScope.launch {
            getMovieListUseCase(query).fold(::getMoviesListFailure, ::getMoviesListSuccess)
        }
    }

    private fun getMoviesListSuccess(ipcList: List<MovieDto>) {
        _getMoviesList.value = ViewState.Success(ipcList)
    }

    private fun getMoviesListFailure(failure: Failure) {
        _getMoviesList.value = ViewState.Error(proccessError(failure))
    }
}