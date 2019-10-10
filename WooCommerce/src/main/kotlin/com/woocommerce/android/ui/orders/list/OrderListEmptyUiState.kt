package com.woocommerce.android.ui.orders.list

import androidx.annotation.DrawableRes
import com.woocommerce.android.R
import com.woocommerce.android.model.UiString
import com.woocommerce.android.model.UiString.UiStringRes
import com.woocommerce.android.ui.orders.list.OrderListType.ALL
import com.woocommerce.android.ui.orders.list.OrderListType.SEARCH

sealed class OrderListEmptyUiState(
    val title: UiString? = null,
    @DrawableRes val imgResId: Int? = null,
    val buttonText: UiString? = null,
    val onButtonClick: (() -> Unit)? = null,
    val emptyViewVisible: Boolean = true
) {
    class ShareStore(
        onButtonClick: (() -> Unit)?
    ) : OrderListEmptyUiState(
            title = UiStringRes(R.string.orderlist_no_orders),
            imgResId = R.drawable.ic_woo_waiting_customers,
            buttonText = UiStringRes(R.string.share_store_button),
            onButtonClick = onButtonClick
    )

    class EmptyList(title: UiString) : OrderListEmptyUiState(title)

    object DataShown : OrderListEmptyUiState(emptyViewVisible = false)

    object Loading : OrderListEmptyUiState(title = UiStringRes(R.string.orderlist_fetching))

    class RefreshError(
        title: UiString,
        buttonText: UiString? = null,
        onButtonClick: (() -> Unit)? = null
    ) : OrderListEmptyUiState(
            title = title,
            imgResId = R.drawable.ic_woo_error_state,
            buttonText = buttonText,
            onButtonClick = onButtonClick)
}

fun createEmptyUiState(
    orderListType: OrderListType,
    isNetworkAvailable: Boolean,
    isLoadingData: Boolean,
    isListEmpty: Boolean,
    isSearchPromptRequired: Boolean,
    isError: Boolean = false,
    fetchFirstPage: () -> Unit,
    shareStoreFunc: (() -> Unit)? = null
): OrderListEmptyUiState {
    return if (isListEmpty) {
        when {
            isError -> createErrorListUiState(isNetworkAvailable, fetchFirstPage)
            isLoadingData -> {
                // don't show intermediate screen when loading search results
                if (orderListType == SEARCH) {
                    OrderListEmptyUiState.DataShown
                } else {
                    OrderListEmptyUiState.Loading
                }
            }
            else -> {
                createEmptyListUiState(orderListType, isSearchPromptRequired, shareStoreFunc)
            }
        }
    } else {
        OrderListEmptyUiState.DataShown
    }
}

private fun createErrorListUiState(
    isNetworkAvailable: Boolean,
    fetchFirstPage: () -> Unit
): OrderListEmptyUiState {
    val errorText = if (isNetworkAvailable) {
        UiStringRes(R.string.orderlist_error_fetch_generic)
    } else {
        UiStringRes(R.string.error_generic_network)
    }
    return OrderListEmptyUiState.RefreshError(errorText, UiStringRes(R.string.retry), fetchFirstPage)
}

private fun createEmptyListUiState(
    orderListType: OrderListType,
    isSearchPromptRequired: Boolean,
    shareStoreFunc: (() -> Unit)? = null
): OrderListEmptyUiState {
    return when (orderListType) {
        SEARCH -> {
            if (isSearchPromptRequired) {
                OrderListEmptyUiState.EmptyList(UiStringRes(R.string.orders_empty_message_with_search))
            } else {
                OrderListEmptyUiState.EmptyList(UiStringRes(R.string.orders_empty_message_with_search))
            }
        }
        ALL -> OrderListEmptyUiState.ShareStore(shareStoreFunc)
    }
}
