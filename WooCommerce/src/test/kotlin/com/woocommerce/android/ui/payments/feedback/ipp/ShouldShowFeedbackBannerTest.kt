package com.woocommerce.android.ui.payments.feedback.ipp

import com.woocommerce.android.AppPrefsWrapper
import com.woocommerce.android.cardreader.config.CardReaderConfigFactory
import com.woocommerce.android.extensions.daysAgo
import com.woocommerce.android.ui.payments.GetActivePaymentsPlugin
import com.woocommerce.android.ui.payments.cardreader.CashOnDeliverySettingsRepository
import com.woocommerce.android.viewmodel.BaseUnitTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.wordpress.android.fluxc.model.SiteModel
import org.wordpress.android.fluxc.model.WCSettingsModel
import org.wordpress.android.fluxc.store.WCInPersonPaymentsStore
import org.wordpress.android.fluxc.store.WooCommerceStore
import java.util.Calendar
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class ShouldShowFeedbackBannerTest : BaseUnitTest() {
    private val appPrefs: AppPrefsWrapper = mock()
    private val getActivePaymentsPlugin: GetActivePaymentsPlugin = mock()
    private val cashOnDeliverySettings: CashOnDeliverySettingsRepository = mock()
    private val siteModel: SiteModel = SiteModel()
    private val wooCommerceStore: WooCommerceStore = mock()
    private val siteSettings: WCSettingsModel = mock()

    private val sut = ShouldShowFeedbackBanner(
        prefs = appPrefs,
        getActivePaymentsPlugin = getActivePaymentsPlugin,
        cashOnDeliverySettings = cashOnDeliverySettings,
        wooCommerceStore = wooCommerceStore,
        siteModel = siteModel,
        cardReaderConfig = CardReaderConfigFactory()
    )

    @Before
    fun setup() = runBlocking {
        whenever(cashOnDeliverySettings.isCashOnDeliveryEnabled()).thenReturn(true)
        whenever(appPrefs.isIPPFeedbackSurveyCompleted()).thenReturn(false)
        whenever(appPrefs.isIPPFeedbackBannerDismissedForever()).thenReturn(false)
        whenever(getActivePaymentsPlugin())
            .thenReturn(WCInPersonPaymentsStore.InPersonPaymentsPluginType.WOOCOMMERCE_PAYMENTS)
        whenever(wooCommerceStore.getSiteSettings(any())).thenReturn(siteSettings)
        whenever(siteSettings.countryCode).thenReturn("US")
        Unit
    }

    @Test
    fun `given the store's country code is not US or CA, then the banner should not be shown`() = runBlocking {
        // given
        whenever(wooCommerceStore.getSiteSettings(siteModel)?.countryCode).thenReturn("GB")

        // when
        val result = sut()

        // then
        assertFalse(result)
    }

    @Test
    fun `given the store's country code is US, then the banner should be shown`() = runBlocking {
        // given
        whenever(wooCommerceStore.getSiteSettings(siteModel)?.countryCode).thenReturn("US")

        // when
        val result = sut()

        // then
        assertTrue(result)
    }

    @Test
    fun `given the store's country code is CA, then the banner should be shown`() = runBlocking {
        // given
        whenever(wooCommerceStore.getSiteSettings(siteModel)?.countryCode).thenReturn("CA")

        // when
        val result = sut()

        // then
        assertTrue(result)
    }

    @Test
    fun `given COD is not enabled, then banner should not be shown`() = runBlocking {
        // given
        whenever(cashOnDeliverySettings.isCashOnDeliveryEnabled()).thenReturn(false)

        // when
        val result = sut()

        // then
        assertFalse(result)
    }

    @Test
    fun `given Stripe plugin is used, then banner should not be shown`() = runBlocking {
        // given
        whenever(getActivePaymentsPlugin()).thenReturn(WCInPersonPaymentsStore.InPersonPaymentsPluginType.STRIPE)

        // when
        val result = sut()

        // then
        assertFalse(result)
    }

    @Test
    fun `given survey has been completed, then banner should not be shown`() = runBlocking {
        // given
        whenever(appPrefs.isIPPFeedbackSurveyCompleted()).thenReturn(true)

        // when
        val result = sut()

        // then
        assertFalse(result)
    }

    @Test
    fun `given survey has been dismissed forever, then banner should not be shown`() = runBlocking {
        // given
        whenever(appPrefs.isIPPFeedbackBannerDismissedForever()).thenReturn(true)

        // when
        val result = sut()

        // then
        assertFalse(result)
    }

    @Test
    fun `given survey has been dismissed less than 7 days ago, then banner should not be shown`() = runBlocking {
        // given
        val now = Calendar.getInstance().time
        val sixDaysAgo = now.daysAgo(6)
        whenever(appPrefs.getIPPFeedbackBannerLastDismissed()).thenReturn(sixDaysAgo.time)

        // when
        val result = sut()

        // then
        assertFalse(result)
    }

    @Test
    fun `given survey has been dismissed more than or equal to 7 days ago, then banner should be shown`() =
        runBlocking {
            // given
            val now = Calendar.getInstance().time
            val eightDaysAgo = now.daysAgo(7)
            whenever(appPrefs.getIPPFeedbackBannerLastDismissed()).thenReturn(eightDaysAgo.time)

            // when
            val result = sut()

            // then
            assertTrue(result)
        }

    @Test
    fun `given survey has not been dismissed, then banner should be shown`() = runBlocking {
        // given
        whenever(appPrefs.getIPPFeedbackBannerLastDismissed()).thenReturn(-1L)

        // when
        val result = sut()

        // then
        assertTrue(result)
    }
}
