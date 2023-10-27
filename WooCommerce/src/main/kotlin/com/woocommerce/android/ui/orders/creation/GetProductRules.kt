package com.woocommerce.android.ui.orders.creation

import com.woocommerce.android.model.Order
import com.woocommerce.android.model.Product
import com.woocommerce.android.ui.orders.creation.configuration.ProductRules
import com.woocommerce.android.ui.products.GetBundledProducts
import com.woocommerce.android.ui.products.ProductDetailRepository
import com.woocommerce.android.ui.products.ProductType
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetProductRules @Inject constructor(
    private val productDetailRepository: ProductDetailRepository,
    private val getBundledProducts: GetBundledProducts
) {
    suspend fun getRules(item: Order.Item): ProductRules? {
        if (item.isVariation) return null
        return productDetailRepository.getProduct(item.productId)?.let { getRules(it) }
    }

    suspend fun getRules(productId: Long): ProductRules? {
        return productDetailRepository.getProduct(productId)?.let { getRules(it) }
    }

    private suspend fun getRules(product: Product): ProductRules? {
        val isBundle = product.productType == ProductType.BUNDLE
        return if (isBundle) {
            val builder = ProductRules.Builder().apply {
                productType = ProductType.BUNDLE
            }
            getBundledProducts(product.remoteId).first().forEach { bundledProduct ->
                builder.setChildQuantityRules(
                    itemId = bundledProduct.id,
                    quantityMin = bundledProduct.rules.quantityMin,
                    quantityMax = bundledProduct.rules.quantityMax,
                    quantityDefault = bundledProduct.rules.quantityDefault
                )
                if (bundledProduct.rules.isOptional) {
                    builder.setChildOptional(bundledProduct.id)
                }
            }
            builder.build()
        } else {
            null
        }
    }
}
