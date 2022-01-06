package com.woocommerce.android.ui.orders.creation.products

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.woocommerce.android.model.Product
import com.woocommerce.android.ui.orders.creation.OrderCreationNavigationTarget.ShowProductVariations
import com.woocommerce.android.ui.products.ProductListRepository
import com.woocommerce.android.viewmodel.LiveDataDelegate
import com.woocommerce.android.viewmodel.MultiLiveEvent
import com.woocommerce.android.viewmodel.ScopedViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@HiltViewModel
class OrderCreationProductSelectionViewModel @Inject constructor(
    savedState: SavedStateHandle,
    private val productListRepository: ProductListRepository
) : ScopedViewModel(savedState) {
    val viewStateData = LiveDataDelegate(savedState, ViewState())
    private var viewState by viewStateData

    private val productList = MutableLiveData<List<Product>>()
    val productListData: LiveData<List<Product>> = productList

    init {
        fetchProductList()
    }

    fun fetchProductList(
        loadMore: Boolean = false
    ) {
        if (loadMore.not()) {
            viewState = viewState.copy(isSkeletonShown = true)
        }

        launch {
            productListRepository.getProductList()
                .takeIf { it.isNotEmpty() }
                ?.let {
                    productList.value = it
                    viewState = viewState.copy(isSkeletonShown = false)
                }

            productList.value = productListRepository.fetchProductList(loadMore)
            viewState = viewState.copy(isSkeletonShown = false)
        }
    }

    fun onProductSelected(productId: Long) {
        val product = productList.value!!.first { it.remoteId == productId }
        if (product.numVariations == 0) {
            triggerEvent(AddProduct(productId))
        } else {
            triggerEvent(ShowProductVariations(productId))
        }
    }

    @Parcelize
    data class ViewState(
        val isSkeletonShown: Boolean? = null
    ) : Parcelable

    data class AddProduct(val productId: Long) : MultiLiveEvent.Event()
}
