package com.woocommerce.android.ui.payments.receipt.preview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.woocommerce.android.R.string
import com.woocommerce.android.ui.payments.receipt.PaymentReceiptShare
import com.woocommerce.android.ui.payments.receipt.preview.ReceiptPreviewViewModel.ReceiptPreviewViewState.Content
import com.woocommerce.android.ui.payments.receipt.preview.ReceiptPreviewViewModel.ReceiptPreviewViewState.Loading
import com.woocommerce.android.ui.payments.tracking.PaymentsFlowTracker
import com.woocommerce.android.util.PrintHtmlHelper.PrintJobResult
import com.woocommerce.android.util.PrintHtmlHelper.PrintJobResult.CANCELLED
import com.woocommerce.android.util.PrintHtmlHelper.PrintJobResult.FAILED
import com.woocommerce.android.util.PrintHtmlHelper.PrintJobResult.STARTED
import com.woocommerce.android.viewmodel.MultiLiveEvent.Event.ShowSnackbar
import com.woocommerce.android.viewmodel.ScopedViewModel
import com.woocommerce.android.viewmodel.navArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReceiptPreviewViewModel
@Inject constructor(
    savedState: SavedStateHandle,
    private val paymentsFlowTracker: PaymentsFlowTracker,
    private val paymentReceiptShare: PaymentReceiptShare,
) : ScopedViewModel(savedState) {
    private val args: ReceiptPreviewFragmentArgs by savedState.navArgs()

    private val viewState = MutableLiveData<ReceiptPreviewViewState>(Loading)
    val viewStateData: LiveData<ReceiptPreviewViewState> = viewState

    init {
        _event.value = LoadUrl(args.receiptUrl)
    }

    fun onReceiptLoaded() {
        viewState.value = Content
    }

    fun onPrintClicked() {
        launch {
            paymentsFlowTracker.trackPrintReceiptTapped()
            triggerEvent(PrintReceipt(args.receiptUrl, "receipt-order-${args.orderId}"))
        }
    }

    fun onShareClicked() {
        launch {
            viewState.value = Loading

            paymentsFlowTracker.trackEmailReceiptTapped()
            when (val sharingResult = paymentReceiptShare(args.receiptUrl, args.orderId)) {
                is PaymentReceiptShare.ReceiptShareResult.Error.FileCreation -> {
                    paymentsFlowTracker.trackPaymentsReceiptSharingFailed(sharingResult)
                    triggerEvent(ShowSnackbar(string.card_reader_payment_receipt_can_not_be_stored))
                }
                is PaymentReceiptShare.ReceiptShareResult.Error.FileDownload -> {
                    paymentsFlowTracker.trackPaymentsReceiptSharingFailed(sharingResult)
                    triggerEvent(ShowSnackbar(string.card_reader_payment_receipt_can_not_be_downloaded))
                }
                is PaymentReceiptShare.ReceiptShareResult.Error.Sharing -> {
                    paymentsFlowTracker.trackPaymentsReceiptSharingFailed(sharingResult)
                    triggerEvent(ShowSnackbar(string.card_reader_payment_email_client_not_found))
                }
                PaymentReceiptShare.ReceiptShareResult.Success -> {
                    // no-op
                }
            }

            viewState.value = Content
        }
    }

    fun onPrintResult(result: PrintJobResult) {
        launch {
            when (result) {
                CANCELLED -> paymentsFlowTracker.trackPrintReceiptCancelled()
                FAILED -> paymentsFlowTracker.trackPrintReceiptFailed()
                STARTED -> paymentsFlowTracker.trackPrintReceiptSucceeded()
            }
        }
    }

    sealed class ReceiptPreviewViewState(
        val isProgressVisible: Boolean = false,
        val isContentVisible: Boolean = false
    ) {
        object Loading : ReceiptPreviewViewState(isProgressVisible = true)
        object Content : ReceiptPreviewViewState(isContentVisible = true)
    }
}
