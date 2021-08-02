package com.woocommerce.android.ui.products.addons

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.woocommerce.android.model.ProductAddon

class AddonListAdapter(
    val addons: List<ProductAddon>
) : RecyclerView.Adapter<AddonListAdapter.AddonsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        AddonsViewHolder(ProductAddonCard(parent.context))

    override fun onBindViewHolder(holder: AddonsViewHolder, position: Int) {
        holder.addonCard.bind(addons[position])
    }

    override fun getItemCount() = addons.size

    inner class AddonsViewHolder(
        val addonCard: ProductAddonCard
    ) : RecyclerView.ViewHolder(addonCard)
}
