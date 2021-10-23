package net.portes.shared.models

/**
 * @author amadeus.portes
 */
sealed class Failure {
    object NetworkConnection : Failure()
    object ServerError : Failure()
    object TimeoutError : Failure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure: Failure()
}
