package com.woocommerce.android.screenshots.orders

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.woocommerce.android.R
import com.woocommerce.android.screenshots.util.Screen

class OrderCreationScreen : Screen {
    companion object {
        const val ORDER_CREATION = R.id.order_creation_root
        const val CREATE_BUTTON = R.id.menu_create
    }

    constructor() : super(ORDER_CREATION)

    fun createEmptyOrder(): SingleOrderScreen {
        clickOn(CREATE_BUTTON)
        return SingleOrderScreen()
    }

    fun assertNewOrderScreen(): OrderCreationScreen {
        Espresso.onView(withId(R.id.collapsing_toolbar))
            .check(matches(hasDescendant(withText(R.string.order_creation_fragment_title))))
            .check(matches(isDisplayed()))
        Espresso.onView(withId(ORDER_CREATION)).check(matches(isDisplayed()))
        Espresso.onView(withId(CREATE_BUTTON)).check(matches(isDisplayed()))
        return this
    }
}
