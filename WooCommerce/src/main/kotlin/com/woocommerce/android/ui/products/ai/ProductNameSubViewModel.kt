package com.woocommerce.android.ui.products.ai

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asLiveData
import com.woocommerce.android.analytics.AnalyticsEvent
import com.woocommerce.android.analytics.AnalyticsTracker
import com.woocommerce.android.extensions.orNullIfEmpty
import com.woocommerce.android.viewmodel.MultiLiveEvent.Event
import com.woocommerce.android.viewmodel.getStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import org.wordpress.android.mediapicker.api.MediaPickerSetup.DataSource

class ProductNameSubViewModel(
    savedStateHandle: SavedStateHandle,
    override val onDone: (String) -> Unit
) : AddProductWithAISubViewModel<String> {
    companion object {
        private const val KEY_SUBSCREEN_NAME = "product_name"
    }

    private val _events = MutableSharedFlow<Event>()
    override val events: Flow<Event> get() = _events

    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val uiState = savedStateHandle.getStateFlow(
        scope = viewModelScope,
        initialValue = UiState(
            name = "",
            isMediaPickerDialogVisible = false
        ),
        key = KEY_SUBSCREEN_NAME
    )

    val state = uiState.asLiveData()

    fun onProductNameChanged(enteredName: String) {
        uiState.update { uiState.value.copy(name = enteredName) }
    }

    fun onDoneClick() {
        AnalyticsTracker.track(AnalyticsEvent.PRODUCT_CREATION_AI_PRODUCT_NAME_CONTINUE_BUTTON_TAPPED)
        onDone(uiState.value.name)
    }

    fun onSuggestNameClicked() {
        AnalyticsTracker.track(
            AnalyticsEvent.PRODUCT_NAME_AI_ENTRY_POINT_TAPPED,
            mapOf(
                AnalyticsTracker.KEY_HAS_INPUT_NAME to (uiState.value.name.isNotEmpty()).toString(),
                AnalyticsTracker.KEY_SOURCE to AnalyticsTracker.VALUE_PRODUCT_CREATION_AI
            )
        )
        viewModelScope.launch {
            _events.emit(NavigateToAIProductNameBottomSheet(uiState.value.name.orNullIfEmpty()))
        }
    }

    fun onMediaPickerDialogDismissed() {
        uiState.update { uiState.value.copy(isMediaPickerDialogVisible = false) }
    }

    @Suppress("UNUSED_PARAMETER")
    fun onMediaLibraryRequested(source: DataSource) {
        onMediaPickerDialogDismissed()
    }

    fun onPackageImageClicked() {
        uiState.update { uiState.value.copy(isMediaPickerDialogVisible = true) }
    }

    override fun close() {
        viewModelScope.cancel()
    }

    @Parcelize
    data class UiState(
        val name: String,
        val isMediaPickerDialogVisible: Boolean
    ) : Parcelable

    data class NavigateToAIProductNameBottomSheet(val initialName: String?) : Event()
}
