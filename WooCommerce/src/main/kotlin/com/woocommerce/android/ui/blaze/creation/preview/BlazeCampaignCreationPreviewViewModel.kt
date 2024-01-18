package ui.blaze.creation.preview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.woocommerce.android.R
import com.woocommerce.android.extensions.formatToMMMdd
import com.woocommerce.android.ui.blaze.BlazeRepository
import com.woocommerce.android.ui.blaze.BlazeRepository.CampaignPreview
import com.woocommerce.android.ui.blaze.creation.preview.BlazeCampaignCreationPreviewFragmentArgs
import com.woocommerce.android.util.CurrencyFormatter
import com.woocommerce.android.viewmodel.ResourceProvider
import com.woocommerce.android.viewmodel.ScopedViewModel
import com.woocommerce.android.viewmodel.navArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlazeCampaignCreationPreviewViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    blazeRepository: BlazeRepository,
    private val resourceProvider: ResourceProvider,
    private val currencyFormatter: CurrencyFormatter
) : ScopedViewModel(savedStateHandle) {
    private val navArgs: BlazeCampaignCreationPreviewFragmentArgs by savedStateHandle.navArgs()

    private val _viewState = MutableLiveData(
        blazeRepository
            .getCampaignPreviewDetails(navArgs.productId)
            .toCampaignPreviewUiState(isLoading = true)
    )
    val viewState = _viewState

    init {
        launch {
            delay(3000)
            _viewState.value = _viewState.value?.copy(isLoading = false)
        }
    }

    private fun CampaignPreview.toCampaignPreviewUiState(isLoading: Boolean = false) =
        CampaignPreviewUiState(
            isLoading = isLoading,
            adDetails = AdDetailsUi(
                productId = productId,
                title = aiSuggestions.firstOrNull()?.title ?: "",
                tagLine = aiSuggestions.firstOrNull()?.tagLine ?: "",
                campaignImageUrl = campaignImageUrl ?: "",
            ),
            campaignDetails = toCampaignDetailsUi()
        )

    private fun CampaignPreview.toCampaignDetailsUi() =
        CampaignDetailsUi(
            budget = CampaignDetailItemUi(
                displayTitle = resourceProvider.getString(R.string.blaze_campaign_preview_details_budget),
                displayValue = budget.toDisplayValue(),
            ),
            targetDetails = listOf(
                CampaignDetailItemUi(
                    displayTitle = resourceProvider.getString(R.string.blaze_campaign_preview_details_language),
                    displayValue = languages.joinToString { it.name }
                        .ifEmpty { resourceProvider.getString(R.string.blaze_campaign_preview_target_default_value) },
                ),
                CampaignDetailItemUi(
                    displayTitle = resourceProvider.getString(R.string.blaze_campaign_preview_details_devices),
                    displayValue = locations.joinToString { it.name }
                        .ifEmpty { resourceProvider.getString(R.string.blaze_campaign_preview_target_default_value) },
                ),
                CampaignDetailItemUi(
                    displayTitle = resourceProvider.getString(R.string.blaze_campaign_preview_details_location),
                    displayValue = devices.joinToString { it.name }
                        .ifEmpty { resourceProvider.getString(R.string.blaze_campaign_preview_target_default_value) },
                ),
                CampaignDetailItemUi(
                    displayTitle = resourceProvider.getString(R.string.blaze_campaign_preview_details_interests),
                    displayValue = interests.joinToString { it.description }
                        .ifEmpty { resourceProvider.getString(R.string.blaze_campaign_preview_target_default_value) },
                ),
            ),
            destinationUrl = CampaignDetailItemUi(
                displayTitle = resourceProvider.getString(R.string.blaze_campaign_preview_details_destination_url),
                displayValue = targetUrl,
                maxLinesValue = 1,
            )
        )

    private fun BlazeRepository.Budget.toDisplayValue(): String {
        val totalBudgetWithCurrency = currencyFormatter.formatCurrency(
            totalBudget.toBigDecimal(),
            currencyCode
        )
        val duration = resourceProvider.getString(
            R.string.blaze_campaign_preview_days_duration,
            durationInDays,
            startDate.formatToMMMdd()
        )
        return "$totalBudgetWithCurrency,  $duration"
    }

    data class CampaignPreviewUiState(
        val isLoading: Boolean = false,
        val adDetails: AdDetailsUi,
        val campaignDetails: CampaignDetailsUi,
    )

    data class AdDetailsUi(
        val productId: Long,
        val title: String,
        val tagLine: String,
        val campaignImageUrl: String?,
    )

    data class CampaignDetailsUi(
        val budget: CampaignDetailItemUi,
        val targetDetails: List<CampaignDetailItemUi>,
        val destinationUrl: CampaignDetailItemUi,
    )

    data class CampaignDetailItemUi(
        val displayTitle: String,
        val displayValue: String,
        val maxLinesValue: Int? = null,
    )
}
