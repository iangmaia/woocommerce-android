package com.woocommerce.android.e2e.screens.orders

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.woocommerce.android.R
import com.woocommerce.android.e2e.helpers.util.Screen

class OrderSelectProductScreen : Screen {
    companion object {
        const val SELECT_PRODUCT_ROOT = R.id.selectProduct_root
        const val LIST_VIEW = R.id.products_list
        const val SIMPLE_PRODUCT_NAME = "Akoya Pearl shades"
    }

    constructor() : super(SELECT_PRODUCT_ROOT)

    fun assertOrderSelectProductScreen(): OrderSelectProductScreen {
        Espresso.onView(withId(R.id.collapsing_toolbar))
            .check(matches(hasDescendant(withText(R.string.order_creation_add_products))))
            .check(matches(isDisplayed()))
        return this
    }

    fun selectProduct(productName: String): UnifiedOrderScreen {
        scrollToListItem(productName, LIST_VIEW)
        selectListItem(productName, LIST_VIEW)
        return UnifiedOrderScreen()
    }
}
