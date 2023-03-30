package com.woocommerce.android.ui.products.selector

import androidx.lifecycle.SavedStateHandle
import com.woocommerce.android.analytics.AnalyticsTrackerWrapper
import com.woocommerce.android.initSavedStateHandle
import com.woocommerce.android.tools.SelectedSite
import com.woocommerce.android.ui.products.ProductTestUtils
import com.woocommerce.android.ui.products.ProductType
import com.woocommerce.android.ui.products.selector.ProductSelectorViewModel.ProductSelectorRestriction.NoVariableProductsWithNoVariations
import com.woocommerce.android.ui.products.selector.ProductSelectorViewModel.ProductSelectorRestriction.OnlyPublishedProducts
import com.woocommerce.android.ui.products.variations.selector.VariationSelectorRepository
import com.woocommerce.android.util.CurrencyFormatter
import com.woocommerce.android.viewmodel.BaseUnitTest
import com.woocommerce.android.viewmodel.ResourceProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.wordpress.android.fluxc.store.WooCommerceStore

@ExperimentalCoroutinesApi
internal class ProductSelectorViewModelTest : BaseUnitTest() {
    companion object {
        private val VALID_PRODUCT = ProductTestUtils.generateProduct(productId = 1L)
        private val DRAFT_PRODUCT = ProductTestUtils.generateProduct(productId = 2L, customStatus = "draft")
        private val VARIABLE_PRODUCT_WITH_NO_VARIATIONS = ProductTestUtils.generateProduct(
            productId = 3L,
            isVariable = true,
            variationIds = "[]",
        )
    }

    private val currencyFormatter: CurrencyFormatter = mock()
    private val wooCommerceStore: WooCommerceStore = mock()
    private val selectedSite: SelectedSite = mock()
    private val listHandler: ProductListHandler = mock {
        on { productsFlow } doReturn flowOf(listOf(VALID_PRODUCT, DRAFT_PRODUCT, VARIABLE_PRODUCT_WITH_NO_VARIATIONS))
    }
    private val variationSelectorRepository: VariationSelectorRepository = mock()
    private val resourceProvider: ResourceProvider = mock()
    private val tracker: AnalyticsTrackerWrapper = mock()

    @Test
    fun `given published products restriction, when view model created, should not show draft products`() {
        val navArgs = ProductSelectorFragmentArgs(
            selectedItems = emptyArray(),
            restrictions = arrayOf(OnlyPublishedProducts),
            productSelectorFlow = ProductSelectorViewModel.ProductSelectorFlow.Undefined,
        ).initSavedStateHandle()

        val sut = createViewModel(navArgs)

        sut.viewState.observeForever { state ->
            assertThat(state.products).isNotEmpty
            assertThat(state.products.filter { it.id == DRAFT_PRODUCT.remoteId }).isEmpty()
        }
    }

    @Test
    fun `given no variable products with no variations restriction, when view model created, should not show variable products with no variations`() {
        val navArgs = ProductSelectorFragmentArgs(
            selectedItems = emptyArray(),
            restrictions = arrayOf(NoVariableProductsWithNoVariations),
            productSelectorFlow = ProductSelectorViewModel.ProductSelectorFlow.Undefined,
        ).initSavedStateHandle()

        val sut = createViewModel(navArgs)

        sut.viewState.observeForever { state ->
            assertThat(state.products).isNotEmpty
            assertThat(
                state.products.filter { it.type == ProductType.VARIABLE && it.numVariations == 0 }
            ).isEmpty()
        }
    }

    @Test
    fun `given multiple restrictions, when view model created, should show correct products`() {
        val navArgs = ProductSelectorFragmentArgs(
            selectedItems = emptyArray(),
            restrictions = arrayOf(OnlyPublishedProducts, NoVariableProductsWithNoVariations),
            productSelectorFlow = ProductSelectorViewModel.ProductSelectorFlow.Undefined,
        ).initSavedStateHandle()

        val sut = createViewModel(navArgs)

        sut.viewState.observeForever { state ->
            assertThat(state.products).isNotEmpty
            assertThat(
                state.products.filter { it.type == ProductType.VARIABLE && it.numVariations == 0 }
            ).isEmpty()
            assertThat(state.products.filter { it.id == DRAFT_PRODUCT.remoteId }).isEmpty()
        }
    }

    @Test
    fun `given no restrictions, when view model created, should show all products`() {
        val navArgs = ProductSelectorFragmentArgs(
            selectedItems = emptyArray(),
            restrictions = emptyArray(),
            productSelectorFlow = ProductSelectorViewModel.ProductSelectorFlow.Undefined,
        ).initSavedStateHandle()

        val sut = createViewModel(navArgs)

        sut.viewState.observeForever { state ->
            assertThat(state.products.count()).isEqualTo(3)
            assertThat(
                state.products.map { it.id }
            ).containsAll(
                listOf(VALID_PRODUCT.remoteId, DRAFT_PRODUCT.remoteId, VARIABLE_PRODUCT_WITH_NO_VARIATIONS.remoteId)
            )
        }
    }

    private fun createViewModel(navArgs: SavedStateHandle) =
        ProductSelectorViewModel(
            navArgs,
            currencyFormatter,
            wooCommerceStore,
            selectedSite,
            listHandler,
            variationSelectorRepository,
            resourceProvider,
            tracker,
        )
}
