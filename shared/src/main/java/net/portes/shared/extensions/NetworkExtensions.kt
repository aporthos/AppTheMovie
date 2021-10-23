package net.portes.shared.extensions

import net.portes.shared.models.Either
import net.portes.shared.models.Failure
import retrofit2.Call
import timber.log.Timber
import java.net.SocketTimeoutException

/**
 * @author amadeus.portes
 */
fun Throwable.parseError(): Failure = when (this) {
    is SocketTimeoutException -> Failure.TimeoutError
    else -> Failure.ServerError
}

fun <T, R> Call<T>.call(transform: (T) -> R, default: T): Either<Failure, R> {
    return try {
        val response = execute()
        when (response.isSuccessful) {
            true -> Either.Right(transform(response.body() ?: default))
            false -> Either.Left(Failure.ServerError)
        }
    } catch (e: Throwable) {
        Timber.e("request: -> $e")
        Either.Left(e.parseError())
    }
}
