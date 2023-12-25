package com.woocommerce.android.ui.orders.creation.totals

import com.woocommerce.android.R
import com.woocommerce.android.extensions.isNotEqualTo
import com.woocommerce.android.extensions.isNotNullOrEmpty
import com.woocommerce.android.extensions.sumByBigDecimal
import com.woocommerce.android.model.Order
import com.woocommerce.android.ui.orders.TabletOrdersFeatureFlagWrapper
import com.woocommerce.android.ui.orders.creation.OrderCreateEditViewModel
import com.woocommerce.android.util.CurrencyFormatter
import com.woocommerce.android.viewmodel.ResourceProvider
import java.math.BigDecimal
import javax.inject.Inject

class OrderCreateEditTotalsHelper @Inject constructor(
    private val isTabletOrdersM1Enabled: TabletOrdersFeatureFlagWrapper,
    private val resourceProvider: ResourceProvider,
    private val currencyFormatter: CurrencyFormatter
) {
    @Suppress("LongParameterList")
    fun mapToPaymentTotalsState(
        order: Order,
        mode: OrderCreateEditViewModel.Mode,
        onShippingClicked: () -> Unit,
        onCouponsClicked: () -> Unit,
        onGiftClicked: () -> Unit,
        onTaxesLearnMore: () -> Unit,
        onMainButtonClicked: () -> Unit
    ): TotalsSectionsState {
        return if (isTabletOrdersM1Enabled()) {
            val bigDecimalFormatter = currencyFormatter.buildBigDecimalFormatter(
                currencyCode = order.currency
            )

            if (order.items.isEmpty() && order.feesLines.isEmpty()) {
                TotalsSectionsState.Minimised(
                    orderTotal = order.toOrderTotals(bigDecimalFormatter)
                )
            } else {
                TotalsSectionsState.Full(
                    lines = listOfNotNull(
                        order.toProductsSection(bigDecimalFormatter),
                        order.toCustomAmountSection(bigDecimalFormatter),
                        order.toShippingSection(bigDecimalFormatter, onClick = onShippingClicked),
                        order.toCouponsSection(bigDecimalFormatter, onClick = onCouponsClicked),
                        order.toGiftSection(bigDecimalFormatter, onClick = onGiftClicked),
                        order.toTaxesSection(bigDecimalFormatter, onClick = onTaxesLearnMore),
                        order.toDiscountSection(bigDecimalFormatter),
                    ),
                    orderTotal = order.toOrderTotals(bigDecimalFormatter),
                    mainButton = TotalsSectionsState.Button(
                        text = mode.toButtonText(),
                        enabled = true,
                        onClick = onMainButtonClicked,
                    )
                )
            }
        } else {
            TotalsSectionsState.Disabled
        }
    }

    private fun Order.toOrderTotals(bigDecimalFormatter: (BigDecimal) -> String) =
        TotalsSectionsState.OrderTotal(
            label = resourceProvider.getString(R.string.order_creation_payment_order_total),
            value = bigDecimalFormatter(total)
        )

    private fun Order.toProductsSection(
        bigDecimalFormatter: (BigDecimal) -> String
    ): TotalsSectionsState.Line? =
        if (items.isNotEmpty()) {
            TotalsSectionsState.Line.Simple(
                label = resourceProvider.getString(R.string.order_creation_payment_products),
                value = bigDecimalFormatter(productsTotal)
            )
        } else {
            null
        }

    private fun Order.toCustomAmountSection(
        bigDecimalFormatter: (BigDecimal) -> String
    ): TotalsSectionsState.Line? {
        val hasCustomAmount = feesTotal.isNotEqualTo(BigDecimal.ZERO)

        return if (hasCustomAmount) {
            TotalsSectionsState.Line.Simple(
                label = resourceProvider.getString(R.string.custom_amounts),
                value = bigDecimalFormatter(feesTotal)
            )
        } else {
            null
        }
    }

    private fun Order.toShippingSection(
        bigDecimalFormatter: (BigDecimal) -> String,
        onClick: () -> Unit
    ): TotalsSectionsState.Line? =
        if (shippingLines.firstOrNull { it.methodId != null } != null) {
            TotalsSectionsState.Line.Button(
                text = resourceProvider.getString(R.string.shipping),
                value = shippingLines.sumByBigDecimal { it.total }.let(bigDecimalFormatter),
                enabled = ,
                onClick = onClick,
            )
        } else {
            null
        }

    private fun Order.toCouponsSection(
        bigDecimalFormatter: (BigDecimal) -> String,
        onClick: () -> Unit
    ): TotalsSectionsState.Line? =
        if (discountCodes.isNotNullOrEmpty()) {
            TotalsSectionsState.Line.Button(
                text = resourceProvider.getString(R.string.order_creation_coupon_button),
                value = resourceProvider.getString(
                    R.string.order_creation_coupon_discount_value,
                    bigDecimalFormatter(discountTotal)
                ),
                extraValue = discountCodes,
                enabled = ,
                onClick = onClick,
            )
        } else {
            null
        }

    private fun Order.toGiftSection(
        bigDecimalFormatter: (BigDecimal) -> String,
        onClick: () -> Unit
    ): TotalsSectionsState.Line? =
        if (!selectedGiftCard.isNullOrEmpty()) {
            TotalsSectionsState.Line.Button(
                text = resourceProvider.getString(R.string.order_creation_coupon_button),
                value = bigDecimalFormatter(giftCardDiscountedAmount ?: BigDecimal.ZERO),
                extraValue = selectedGiftCard,
                onClick = onClick,
            )
        } else {
            null
        }

    private fun Order.toTaxesSection(
        bigDecimalFormatter: (BigDecimal) -> String,
        onClick: () -> Unit
    ): TotalsSectionsState.Line =
        TotalsSectionsState.Line.Block(
            lines = listOf(
                TotalsSectionsState.Line.Simple(
                    label = resourceProvider.getString(R.string.order_creation_payment_tax_label),
                    value = bigDecimalFormatter(totalTax)
                )
            ) + taxLines.map {
                TotalsSectionsState.Line.SimpleSmall(
                    label = "${it.label} · ${it.ratePercent}",
                    value = bigDecimalFormatter(BigDecimal(it.taxTotal))
                )
            } + TotalsSectionsState.Line.LearnMore(
                text = resourceProvider.getString(
                    R.string.order_creation_tax_based_on_billing_address
                ),
                buttonText = resourceProvider.getString(R.string.learn_more),
                onClick = onClick
            )
        )

    private fun Order.toDiscountSection(
        bigDecimalFormatter: (BigDecimal) -> String,
    ): TotalsSectionsState.Line? =
        if (discountTotal.isNotEqualTo(BigDecimal.ZERO)) {
            TotalsSectionsState.Line.Simple(
                label = resourceProvider.getString(R.string.order_creation_discounts_total),
                value = resourceProvider.getString(
                    R.string.order_creation_discounts_total_value,
                    bigDecimalFormatter(discountTotal)
                )
            )
        } else {
            null
        }

    private fun OrderCreateEditViewModel.Mode.toButtonText() =
        when (this) {
            OrderCreateEditViewModel.Mode.Creation -> resourceProvider.getString(
                R.string.order_creation_collect_payment_button
            )

            is OrderCreateEditViewModel.Mode.Edit -> resourceProvider.getString(R.string.save)
        }
}

sealed class TotalsSectionsState {
    data class Full(
        val lines: List<Line>,
        val orderTotal: OrderTotal,
        val mainButton: Button,
    ) : TotalsSectionsState()

    data class Minimised(
        val orderTotal: OrderTotal,
    ) : TotalsSectionsState()

    object Disabled : TotalsSectionsState()

    data class Button(
        val text: String,
        val enabled: Boolean,
        val onClick: () -> Unit
    )

    data class OrderTotal(
        val label: String,
        val value: String,
    )

    sealed class Line {
        data class Simple(
            val label: String,
            val value: String,
        ) : Line()

        data class SimpleSmall(val label: String, val value: String) : Line()

        data class Button(
            val text: String,
            val value: String,
            val extraValue: String? = null,
            val enabled: Boolean,
            val onClick: () -> Unit
        ) : Line()

        data class Block(val lines: List<Line>) : Line()

        data class LearnMore(val text: String, val buttonText: String, val onClick: () -> Unit) : Line()
    }
}
