package com.aspire.sawa.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aspire.sawa.R
import com.aspire.sawa.databinding.ItemHomeNearPlacesBinding
import com.aspire.sawa.models.Place
import com.aspire.sawa.unitls.Constraints.CLOSED
import com.aspire.sawa.unitls.Constraints.CROWDED
import com.aspire.sawa.unitls.Constraints.LIGHT
import com.aspire.sawa.unitls.Constraints.MODERATE

class PlaceAdapter : RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Place>() {

        override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHomeNearPlacesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = differ.currentList[position]

        holder.run {

            name.text = item.name
            image.setImageResource(item.image)
            distance.text = item.distance
            capacity.text = item.capacity
            openedState.text = item.opened
            when (item.capacityState) {

                MODERATE -> {
                    capacityStateText.setTextByResources(R.string.capacity_moderator)
                    capacityState.setColor(R.color.capacity_moderator)
                }

                CLOSED -> {
                    capacityStateText.setTextByResources(R.string.capacity_closed)
                    capacityState.setColor(R.color.capacity_closed)
                    openedState.setTextColorByResources(R.color.capacity_crowded)
                }

                LIGHT -> {
                    capacityStateText.setTextByResources(R.string.capacity_light)
                    capacityState.setColor(R.color.capacity_light)
                }

                CROWDED -> {
                    capacityStateText.setTextByResources(R.string.capacity_crowded)
                    capacityState.setColor(R.color.capacity_crowded)
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class ViewHolder(binding: ItemHomeNearPlacesBinding) : RecyclerView.ViewHolder(binding.root) {

        val name = binding.tvPlaceName
        val image = binding.ivPlaceImage
        val distance = binding.tvPlaceDistance
        val capacityState = binding.cvCapacityState
        val capacityStateText = binding.tvCapacityState
        val capacity = binding.tvCapacity
        val openedState = binding.tvOpenedState

        internal fun TextView.setTextByResources(resId: Int) {
            return this.setText(itemView.context.getString(resId))
        }

        internal fun TextView.setTextColorByResources(resId: Int) {
            this.setTextColor(itemView.context.getColor(resId))
        }

        internal fun CardView.setColor(resId: Int) {
            this.setCardBackgroundColor(
                ContextCompat.getColor(
                    itemView.context,
                    resId
                )
            )
        }
    }
}