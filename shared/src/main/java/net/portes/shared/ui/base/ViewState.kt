package net.portes.shared.ui.base

/**
 * @author amadeus.portes
 */
sealed class ViewState<out T : Any> {
    class Success<out T : Any>(val data: T) : ViewState<T>()
    class Error<out T : Any>(val error: Int) : ViewState<T>()
    class Loading<out T : Any> : ViewState<T>()
}