package com.woocommerce.android.ui.prefs.domain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.woocommerce.android.extensions.navigateSafely
import com.woocommerce.android.ui.base.BaseFragment
import com.woocommerce.android.ui.common.wpcomwebview.WPComWebViewAuthenticator
import com.woocommerce.android.ui.compose.theme.WooThemeWithBackground
import com.woocommerce.android.ui.main.AppBarStatus
import com.woocommerce.android.ui.prefs.domain.DomainPurchaseViewModel.NavigateToSuccessScreen
import com.woocommerce.android.viewmodel.MultiLiveEvent
import dagger.hilt.android.AndroidEntryPoint
import org.wordpress.android.fluxc.network.UserAgent
import javax.inject.Inject

@AndroidEntryPoint
class DomainPurchaseFragment : BaseFragment() {
    private val viewModel: DomainPurchaseViewModel by viewModels()

    @Inject internal lateinit var authenticator: WPComWebViewAuthenticator
    @Inject internal lateinit var userAgent: UserAgent

    override val activityAppBarStatus: AppBarStatus
        get() = AppBarStatus.Hidden

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                WooThemeWithBackground {
                    DomainRegistrationCheckoutScreen(viewModel, authenticator, userAgent)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.event.observe(viewLifecycleOwner) { event ->
            when (event) {
                is MultiLiveEvent.Event.Exit -> findNavController().popBackStack()
                is NavigateToSuccessScreen -> navigateToPurchaseSuccessScreen(event.domain)
            }
        }
    }

    private fun navigateToPurchaseSuccessScreen(domain: String) {
        findNavController().navigateSafely(
            DomainPurchaseFragmentDirections
                .actionDomainRegistrationCheckoutFragmentToPurchaseSuccessfulFragment(domain)
        )
    }
}
