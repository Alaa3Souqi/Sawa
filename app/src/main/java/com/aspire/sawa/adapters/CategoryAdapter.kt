package com.aspire.sawa.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.aspire.sawa.databinding.ItemHomeCategoryBinding
import com.aspire.sawa.models.Category

class CategoryAdapter(private val items: List<Category>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHomeCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.run {

            name.text = itemView.context.getString(item.name)
            image.setImageResource(item.image)
            cardView.setCardBackgroundColor(
                ContextCompat.getColor(
                    itemView.context,
                    item.color
                )
            )

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(binding: ItemHomeCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        val cardView = binding.cardView
        val name = binding.tvCategoryName
        val image = binding.ivCategory
    }
}