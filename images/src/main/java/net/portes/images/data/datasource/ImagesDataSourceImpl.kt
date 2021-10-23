package net.portes.images.data.datasource

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import net.portes.images.domain.datasource.ImagesDataSource
import net.portes.shared.models.Either
import net.portes.shared.models.Failure
import net.portes.shared.utils.NetworkHandler
import java.io.File
import javax.inject.Inject

/**
 * @author amadeus.portes
 */
class ImagesDataSourceImpl @Inject constructor(
    private val storage: FirebaseStorage,
    private val networkHandler: NetworkHandler
) : ImagesDataSource {
    override suspend fun loadImage(url: String): Either<Failure, Boolean> {
        return when (networkHandler.isNetworkAvailable()) {
            true -> toLoadImage(url)
            false -> Either.Left(Failure.NetworkConnection)
        }
    }

    private suspend fun toLoadImage(url: String): Either<Failure, Boolean> {
        val storageRef = storage.reference
        val file = Uri.fromFile(File(url))
        val riversRef = storageRef.child("images/${file.lastPathSegment}")
        val task = riversRef.putFile(file).await()
        return task.error?.let {
            Either.Left(Failure.ServerError)
        } ?: run {
            Either.Right(true)
        }
    }
}