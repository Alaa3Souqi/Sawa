package com.aspire.sawa.adapters

import android.view.View
import androidx.core.content.ContextCompat
import com.aspire.sawa.R
import com.aspire.sawa.databinding.ItemHomeCategoryBinding
import com.aspire.sawa.models.Category
import com.xwray.groupie.viewbinding.BindableItem

class CategoryItem(private val item: Category) : BindableItem<ItemHomeCategoryBinding>() {

    override fun bind(viewBinding: ItemHomeCategoryBinding, position: Int) {

        item.hashCode()
        viewBinding.run {
            tvCategoryName.text = root.context.getString(item.name)
            ivCategory.setImageResource(item.image)
            cardView.setCardBackgroundColor(
                ContextCompat.getColor(
                    root.context,
                    item.color
                )
            )
        }
    }

    override fun getLayout(): Int = R.layout.item_home_category

    override fun initializeViewBinding(view: View): ItemHomeCategoryBinding =
        ItemHomeCategoryBinding.bind(view)

}