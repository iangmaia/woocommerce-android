package com.woocommerce.android.ui.payments.receipt

import com.woocommerce.android.AppPrefsWrapper
import com.woocommerce.android.BuildConfig
import com.woocommerce.android.extensions.semverCompareTo
import com.woocommerce.android.tools.SelectedSite
import com.woocommerce.android.ui.payments.cardreader.onboarding.PluginType
import org.wordpress.android.fluxc.model.SiteModel
import org.wordpress.android.fluxc.store.WCOrderStore
import org.wordpress.android.fluxc.store.WooCommerceStore
import javax.inject.Inject

class PaymentReceiptHelper @Inject constructor(
    private val selectedSite: SelectedSite,
    private val wooCommerceStore: WooCommerceStore,
    private val appPrefsWrapper: AppPrefsWrapper,
    private val orderStore: WCOrderStore,
    private val isDevSiteSupported: IsDevSiteSupported,
) {
    fun storeReceiptUrl(orderId: Long, receiptUrl: String) {
        selectedSite.get().let {
            appPrefsWrapper.setReceiptUrl(it.id, it.siteId, it.selfHostedSiteId, orderId, receiptUrl)
        }
    }

    suspend fun getReceiptUrl(orderId: Long): Result<String> =
        if (isWCCanGenerateReceipts()) {
            val fetchingResult = orderStore.fetchOrdersReceipt(
                site = selectedSite.get(),
                orderId = orderId,
                expirationDays = RECEIPT_EXPIRATION_DAYS,
            )

            val result = fetchingResult.result
            if (fetchingResult.isError || result == null) {
                Result.failure(Exception(fetchingResult.error.message))
            } else {
                Result.success(result.receiptUrl)
            }
        } else {
            val urlFromAppPrefs = getReceiptUrlFromAppPrefs(orderId)
            if (urlFromAppPrefs.isEmpty()) {
                Result.failure(Exception("Receipt url not found"))
            } else {
                Result.success(urlFromAppPrefs)
            }
        }

    suspend fun isReceiptAvailable(orderId: Long) =
        when {
            isWCCanGenerateReceipts() -> true
            getReceiptUrlFromAppPrefs(orderId).isNotEmpty() -> true
            else -> false
        }

    private fun getReceiptUrlFromAppPrefs(orderId: Long) = selectedSite.get().let {
        appPrefsWrapper.getReceiptUrl(it.id, it.siteId, it.selfHostedSiteId, orderId)
    }

    fun isPluginCanSendReceipt(site: SiteModel): Boolean {
        val preferredPlugin = appPrefsWrapper.getCardReaderPreferredPlugin(
            localSiteId = site.id,
            remoteSiteId = site.siteId,
            selfHostedSiteId = site.selfHostedSiteId
        )
        return if (preferredPlugin == null || preferredPlugin != PluginType.WOOCOMMERCE_PAYMENTS) {
            false
        } else {
            val pluginVersion = appPrefsWrapper.getCardReaderPreferredPluginVersion(
                localSiteId = site.id,
                remoteSiteId = site.siteId,
                selfHostedSiteId = site.selfHostedSiteId,
                preferredPlugin,
            ) ?: return false

            pluginVersion.semverCompareTo(WCPAY_RECEIPTS_SENDING_SUPPORT_VERSION) >= 0
        }
    }

    suspend fun isWCCanGenerateReceipts(): Boolean {
        val currentWooCoreVersion = getWoocommerceCorePluginVersion()

        return currentWooCoreVersion.semverCompareTo(WC_CAN_GENERATE_RECEIPTS_VERSION) >= 0
    }

    private suspend fun getWoocommerceCorePluginVersion(): String {
        val sitePlugin = wooCommerceStore.getSitePlugin(
            selectedSite.get(),
            WooCommerceStore.WooPlugin.WOO_CORE
        )
        return if (sitePlugin == null) {
            if (isDevSiteSupported()) {
                val devPlugin = wooCommerceStore.getSitePlugins(selectedSite.get())
                    .firstOrNull { it.name == "woocommerce-dev/woocommerce" }
                if (devPlugin != null) {
                    // return this version so we use the backend to generate receipts for testing
                    WC_CAN_GENERATE_RECEIPTS_VERSION
                } else {
                    ""
                }
            } else {
                ""
            }
        } else {
            sitePlugin.version
        } ?: ""
    }

    private companion object {
        const val WCPAY_RECEIPTS_SENDING_SUPPORT_VERSION = "4.0.0"
        const val WC_CAN_GENERATE_RECEIPTS_VERSION = "8.7.0"

        const val RECEIPT_EXPIRATION_DAYS = 2
    }

    class IsDevSiteSupported @Inject constructor() {
        operator fun invoke() = BuildConfig.DEBUG
    }
}
