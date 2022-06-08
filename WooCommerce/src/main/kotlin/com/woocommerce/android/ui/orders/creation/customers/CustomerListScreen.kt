package com.woocommerce.android.ui.orders.creation.customers

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.woocommerce.android.R
import com.woocommerce.android.ui.compose.animations.SkeletonView
import org.wordpress.android.fluxc.model.customer.WCCustomerModel

@Composable
fun OrderCreationCustomerScreen(viewModel: CustomerListViewModel) {
    CustomerListScreen(
        customers = emptyList(),
        onCustomerClick = viewModel::onCustomerClick
    )
}

@Composable
fun CustomerListScreen(
    customers: List<WCCustomerModel>,
    onCustomerClick: (WCCustomerModel) -> Unit?
) {
    when {
        customers.isNotEmpty() -> CustomerList(customers, onCustomerClick)
        else -> EmptyCustomerList()
    }
}

@Composable
private fun EmptyCustomerList() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.major_200)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.order_creation_customer_search_empty),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(
                start = dimensionResource(id = R.dimen.major_150),
                end = dimensionResource(id = R.dimen.major_150)
            )
        )
        Spacer(Modifier.size(dimensionResource(id = R.dimen.major_325)))
        Image(
            painter = painterResource(id = R.drawable.img_empty_search),
            contentDescription = null,
        )
    }
}

@Composable
private fun CustomerList(
    customers: List<WCCustomerModel>,
    onCustomerClick: (WCCustomerModel) -> Unit?
) {
    val listState = rememberLazyListState()
    LazyColumn(
        state = listState,
        modifier = Modifier
            .background(color = MaterialTheme.colors.surface)
    ) {
        itemsIndexed(customers) { _, customer ->
            CustomerListItem(customer, onCustomerClick)
            Divider(
                modifier = Modifier.offset(x = dimensionResource(id = R.dimen.major_100)),
                color = colorResource(id = R.color.divider_color),
                thickness = dimensionResource(id = R.dimen.minor_10)
            )
        }
        /*if (loadingState == LoadingState.Appending) {
            item {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth()
                        .padding(vertical = dimensionResource(id = R.dimen.minor_100))
                )
            }
        }*/
    }
}

@Composable
private fun CustomerListItem(
    customer: WCCustomerModel,
    onCustomerClick: (WCCustomerModel) -> Unit?
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.minor_50)),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                enabled = true,
                role = Role.Button,
                onClick = { onCustomerClick(customer) }
            )
            .padding(
                horizontal = dimensionResource(id = R.dimen.major_100),
                vertical = dimensionResource(id = R.dimen.minor_100)
            ),
    ) {
        Row() {
            Image(
                painter = painterResource(id = R.drawable.ic_photos_grey_c_24dp),
                contentDescription = null
                // TODO nbracbury Glide
            )
        }
        Row() {
            Text(
                text = "${customer.lastName}, $(customer.firstName)",
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onSurface,
            )
        }
    }
}

@Composable
private fun CustomerListSkeleton() {
    val numberOfInboxSkeletonRows = 10
    LazyColumn(Modifier.background(color = MaterialTheme.colors.surface)) {
        repeat(numberOfInboxSkeletonRows) {
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.minor_50)),
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(id = R.dimen.major_100),
                        vertical = dimensionResource(id = R.dimen.minor_100)
                    )
                ) {
                    SkeletonView(
                        dimensionResource(id = R.dimen.skeleton_text_medium_width),
                        dimensionResource(id = R.dimen.major_125)
                    )
                    SkeletonView(
                        dimensionResource(id = R.dimen.skeleton_text_large_width),
                        dimensionResource(id = R.dimen.major_100)
                    )
                    SkeletonView(
                        dimensionResource(id = R.dimen.skeleton_text_small_width),
                        dimensionResource(id = R.dimen.major_125)
                    )
                }
                Divider(
                    modifier = Modifier
                        .offset(x = dimensionResource(id = R.dimen.major_100)),
                    color = colorResource(id = R.color.divider_color),
                    thickness = dimensionResource(id = R.dimen.minor_10)
                )
            }
        }
    }
}

@Composable
private fun SearchEmptyList(searchQuery: String) {
    if (searchQuery.isEmpty()) return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.major_200)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.empty_message_with_search, searchQuery),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(
                start = dimensionResource(id = R.dimen.major_150),
                end = dimensionResource(id = R.dimen.major_150)
            )
        )
        Spacer(Modifier.size(dimensionResource(id = R.dimen.major_325)))
        Image(
            painter = painterResource(id = R.drawable.img_empty_search),
            contentDescription = null,
        )
    }
}

@Preview
@Composable
private fun CustomerListPreview() {
    val customers = listOf(
        WCCustomerModel(
            id = 1,
            firstName = "George",
            lastName = "Carlin"
        )
    )

    CustomerList(customers)
}

@Preview
@Composable
private fun CustomerListEmptyPreview() {
    EmptyCustomerList()
}

@Preview
@Composable
private fun CustomerListSkeletonPreview() {
    CustomerListSkeleton()
}
