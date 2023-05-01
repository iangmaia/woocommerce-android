package com.woocommerce.android.ui.orders.shippinglabels.creation.banner

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import com.google.android.material.card.MaterialCardView
import com.woocommerce.android.databinding.ShippingNoticeBannerBinding

class ShippingNoticeCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {
    private val binding = ShippingNoticeBannerBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    fun setLearnMoreClickListener(action: (View) -> Unit) {
        binding.learnMoreButton.setOnClickListener(action)
    }

    fun setDismissClickListener(action: (View) -> Unit) {
        binding.dismissButton.setOnClickListener(action)
    }
}
