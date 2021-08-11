package com.woocommerce.android.ui.products.addons

import com.woocommerce.android.annotations.OpenClassOnDebug
import com.woocommerce.android.model.Order
import com.woocommerce.android.model.Order.Item.Attribute
import com.woocommerce.android.model.toAppModel
import com.woocommerce.android.tools.SelectedSite
import org.wordpress.android.fluxc.model.order.OrderIdentifier
import org.wordpress.android.fluxc.store.WCOrderStore
import org.wordpress.android.fluxc.store.WCProductStore
import javax.inject.Inject

@OpenClassOnDebug
class AddonRepository @Inject constructor(
    private val orderStore: WCOrderStore,
    private val productStore: WCProductStore,
    private val selectedSite: SelectedSite
) {
    fun fetchOrderAddonsData(
        orderID: OrderIdentifier,
        productID: Long
    ) = fetchOrder(orderID)
        ?.findOrderAttributesWith(productID)
        ?.joinWithAddonsFrom(productID)

    private fun fetchOrder(orderID: OrderIdentifier) =
        orderStore.getOrderByIdentifier(orderID)?.toAppModel()

    private fun Order.findOrderAttributesWith(productID: Long) =
        items.find { it.productId == productID }
            ?.attributesList

    private fun List<Attribute>.joinWithAddonsFrom(productID: Long) =
        productStore
            .getProductByRemoteId(selectedSite.get(), productID)
            ?.toAppModel()
            ?.addons
            ?.let { addons -> Pair(addons, this) }
}
