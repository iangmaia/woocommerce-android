package com.woocommerce.android.ui.orders.creation

import com.woocommerce.android.viewmodel.BaseUnitTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.assertj.core.api.Assertions
import org.junit.Test

@ExperimentalCoroutinesApi
class EAN8CheckDigitRemoverTest : BaseUnitTest() {
    private val checkDigitRemover = EAN8CheckDigitRemover()

    @Test
    fun `given EAN-8 format barcode SKU with check digit, then return SKU with check digit removed`() {
        val sku = "12345678"
        Assertions.assertThat(checkDigitRemover.getSKUWithoutCheckDigit(sku)).isEqualTo(
            "1234567"
        )
    }

    @Test
    fun `given alpha numeric EAN-8 format barcode SKU with check digit, then return SKU with check digit removed`() {
        val sku = "1a345Z78"
        Assertions.assertThat(checkDigitRemover.getSKUWithoutCheckDigit(sku)).isEqualTo(
            "1a345Z7"
        )
    }
}
