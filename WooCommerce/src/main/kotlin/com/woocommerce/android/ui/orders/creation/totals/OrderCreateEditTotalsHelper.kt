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
    fun mapToPaymentTotalsState(
        mode: OrderCreateEditViewModel.Mode,
        order: Order,
        onButtonClicked: () -> Unit
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
                        TotalsSectionsState.Line.Simple(
                            label = resourceProvider.getString(R.string.order_creation_payment_products),
                            value = bigDecimalFormatter(order.productsTotal)
                        ),
                        order.toCustomAmountSection(bigDecimalFormatter),
                        order.toShippingSection(bigDecimalFormatter, onClick = {}),
                        order.toCouponsSection(bigDecimalFormatter, onClick = {}),
                        order.toGiftSection(bigDecimalFormatter, onClick = {}),
                        order.toTaxesSection(bigDecimalFormatter, onClick = {}),
                    ),
                    orderTotal = order.toOrderTotals(bigDecimalFormatter),
                    mainButton = TotalsSectionsState.Button(
                        text = mode.toButtonText(),
                        enabled = true,
                        onClick = onButtonClicked,
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
            val enabled: Boolean = true,
            val onClick: () -> Unit
        ) : Line()

        data class Block(val lines: List<Line>) : Line()

        data class LearnMore(val text: String, val buttonText: String, val onClick: () -> Unit) : Line()
    }
}
