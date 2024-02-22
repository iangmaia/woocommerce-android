package com.woocommerce.android.ui.blaze.creation.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.woocommerce.android.extensions.handleResult
import com.woocommerce.android.extensions.navigateSafely
import com.woocommerce.android.extensions.navigateToHelpScreen
import com.woocommerce.android.ui.base.BaseFragment
import com.woocommerce.android.ui.blaze.creation.payment.BlazeCampaignPaymentSummaryViewModel.NavigateToStartingScreenWithSuccessBottomSheet
import com.woocommerce.android.ui.compose.composeView
import com.woocommerce.android.ui.compose.theme.WooThemeWithBackground
import com.woocommerce.android.ui.main.AppBarStatus
import com.woocommerce.android.viewmodel.MultiLiveEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BlazeCampaignPaymentSummaryFragment : BaseFragment() {
    override val activityAppBarStatus: AppBarStatus
        get() = AppBarStatus.Hidden

    private val viewModel: BlazeCampaignPaymentSummaryViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return composeView {
            WooThemeWithBackground {
                BlazeCampaignPaymentSummaryScreen(viewModel)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        handleEvents()
        handleResults()
    }

    private fun handleEvents() {
        viewModel.event.observe(viewLifecycleOwner) { event ->
            when (event) {
                MultiLiveEvent.Event.Exit -> findNavController().navigateUp()
                is MultiLiveEvent.Event.NavigateToHelpScreen -> navigateToHelpScreen(event.origin)
                is BlazeCampaignPaymentSummaryViewModel.NavigateToPaymentsListScreen -> {
                    findNavController().navigateSafely(
                        BlazeCampaignPaymentSummaryFragmentDirections
                            .actionBlazeCampaignPaymentSummaryFragmentToBlazeCampaignPaymentMethodsListFragment(
                                paymentMethodsData = event.paymentMethodsData,
                                selectedPaymentMethodId = event.selectedPaymentMethodId
                            )
                    )
                }

                is NavigateToStartingScreenWithSuccessBottomSheet -> navigateBackToStartingScreen()
            }
        }
    }

    private fun handleResults() {
        handleResult<String>(BlazeCampaignPaymentMethodsListFragment.SELECTED_PAYMENT_METHOD_KEY) {
            viewModel.onPaymentMethodSelected(it)
        }
    }

    private fun navigateBackToStartingScreen() {
        findNavController().navigateSafely(
            BlazeCampaignPaymentSummaryFragmentDirections
                .actionBlazeCampaignPaymentSummaryFragmentToBlazeCampaignSuccessBottomSheetFragment()
        )
    }
}
