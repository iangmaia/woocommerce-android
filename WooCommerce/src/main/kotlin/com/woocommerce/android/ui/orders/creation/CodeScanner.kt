package com.woocommerce.android.ui.orders.creation

import android.os.Parcelable
import androidx.camera.core.ImageProxy
import com.woocommerce.android.ui.orders.creation.GoogleBarcodeFormatMapper.BarcodeFormat
import kotlinx.parcelize.Parcelize

interface CodeScanner {
    suspend fun recogniseCode(imageProxy: ImageProxy): CodeScannerStatus
}

sealed class CodeScannerStatus : Parcelable {
    @Parcelize
    data class Success(val code: String, val format: BarcodeFormat) : CodeScannerStatus()
    @Parcelize
    data class Failure(
        val error: String?,
        val type: CodeScanningErrorType
    ) : CodeScannerStatus()

    @Parcelize
    object NotFound : CodeScannerStatus()
}

sealed class CodeScanningErrorType : Parcelable {
    @Parcelize
    object Aborted : CodeScanningErrorType()
    @Parcelize
    object AlreadyExists : CodeScanningErrorType()
    @Parcelize
    object Cancelled : CodeScanningErrorType()
    @Parcelize
    object CodeScannerAppNameUnavailable : CodeScanningErrorType()
    @Parcelize
    object CodeScannerCameraPermissionNotGranted : CodeScanningErrorType()
    @Parcelize
    object CodeScannerCancelled : CodeScanningErrorType()
    @Parcelize
    object CodeScannerGooglePlayServicesVersionTooOld : CodeScanningErrorType()
    @Parcelize
    object CodeScannerPipelineInferenceError : CodeScanningErrorType()
    @Parcelize
    object CodeScannerPipelineInitializationError : CodeScanningErrorType()
    @Parcelize
    object CodeScannerTaskInProgress : CodeScanningErrorType()
    @Parcelize
    object CodeScannerUnavailable : CodeScanningErrorType()
    @Parcelize
    object DataLoss : CodeScanningErrorType()
    @Parcelize
    object DeadlineExceeded : CodeScanningErrorType()
    @Parcelize
    object FailedPrecondition : CodeScanningErrorType()
    @Parcelize
    object Internal : CodeScanningErrorType()
    @Parcelize
    object InvalidArgument : CodeScanningErrorType()
    @Parcelize
    object ModelHashMismatch : CodeScanningErrorType()
    @Parcelize
    object ModelIncompatibleWithTFLite : CodeScanningErrorType()
    @Parcelize
    object NetworkIssue : CodeScanningErrorType()
    @Parcelize
    object NotEnoughSpace : CodeScanningErrorType()
    @Parcelize
    object NotFound : CodeScanningErrorType()
    @Parcelize
    object OutOfRange : CodeScanningErrorType()
    @Parcelize
    object PermissionDenied : CodeScanningErrorType()
    @Parcelize
    object ResourceExhausted : CodeScanningErrorType()
    @Parcelize
    object UnAuthenticated : CodeScanningErrorType()
    @Parcelize
    object UnAvailable : CodeScanningErrorType()
    @Parcelize
    object UnImplemented : CodeScanningErrorType()
    @Parcelize
    object Unknown : CodeScanningErrorType()
    @Parcelize
    data class Other(val throwable: Throwable?) : CodeScanningErrorType()

    override fun toString(): String = when (this) {
        is Other -> this.throwable?.message ?: "Other"
        else -> this.javaClass.run {
            name.removePrefix("${`package`?.name ?: ""}.")
        }
    }
}
