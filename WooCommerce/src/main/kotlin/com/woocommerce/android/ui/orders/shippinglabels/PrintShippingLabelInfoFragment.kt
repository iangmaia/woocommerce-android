package com.woocommerce.android.ui.orders.shippinglabels

import android.os.Bundle
import android.text.Html
import android.view.View
import com.woocommerce.android.R
import com.woocommerce.android.analytics.AnalyticsTracker
import com.woocommerce.android.databinding.FragmentPrintShippingLabelInfoBinding
import com.woocommerce.android.ui.base.BaseFragment
import com.woocommerce.android.ui.main.AppBarStatus

class PrintShippingLabelInfoFragment : BaseFragment(R.layout.fragment_print_shipping_label_info) {
    override val activityAppBarStatus: AppBarStatus
        get() = AppBarStatus.Visible(
            navigationIcon = R.drawable.ic_gridicons_cross_24dp
        )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentPrintShippingLabelInfoBinding.bind(view)
        binding.printShippingLabelInfoStep1.text =
            Html.fromHtml(getString(R.string.print_shipping_label_info_step_1), Html.FROM_HTML_MODE_LEGACY)
        binding.printShippingLabelInfoStep2.text =
            Html.fromHtml(getString(R.string.print_shipping_label_info_step_2), Html.FROM_HTML_MODE_LEGACY)
        binding.printShippingLabelInfoStep3.text =
            Html.fromHtml(getString(R.string.print_shipping_label_info_step_3), Html.FROM_HTML_MODE_LEGACY)
        binding.printShippingLabelInfoStep4.text =
            Html.fromHtml(getString(R.string.print_shipping_label_info_step_4), Html.FROM_HTML_MODE_LEGACY)
        binding.printShippingLabelInfoStep5.text =
            Html.fromHtml(getString(R.string.print_shipping_label_info_step_5), Html.FROM_HTML_MODE_LEGACY)
    }

    override fun onResume() {
        super.onResume()
        AnalyticsTracker.trackViewShown(this)
    }

    override fun getFragmentTitle(): String = getString(R.string.print_shipping_label_info_title)
}
