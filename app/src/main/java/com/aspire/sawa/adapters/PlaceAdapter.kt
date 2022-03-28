package com.aspire.sawa.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getColorStateList
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

            capacityState.run {
                when (item.capacityState) {

                    MODERATE -> {
                        setCapacityStateTextByResources(R.string.capacity_moderator)
                        setCapacityStateTint(R.color.capacity_moderator)
                    }

                    CLOSED -> {
                        setCapacityStateTextByResources(R.string.capacity_closed)
                        setCapacityStateTint(R.color.capacity_closed)
                        setOpenedStateTextColorByResources(R.color.capacity_crowded)
                    }

                    LIGHT -> {
                        setCapacityStateTextByResources(R.string.capacity_light)
                        setCapacityStateTint(R.color.capacity_light)
                    }

                    CROWDED -> {
                        setCapacityStateTextByResources(R.string.capacity_crowded)
                        setCapacityStateTint(R.color.capacity_crowded)
                    }
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
        val capacityState = binding.tvCapacityState
        val capacity = binding.tvCapacity
        val openedState = binding.tvOpenedState
        val context: Context = itemView.context

        internal fun setCapacityStateTextByResources(resId: Int) {
            capacityState.text = context.getString(resId)
        }

        internal fun setOpenedStateTextColorByResources(resId: Int) {
            openedState.setTextColor(context.getColor(resId))
        }

        internal fun setCapacityStateTint(resId: Int) {
            capacityState.backgroundTintList = getColorStateList(context, resId)
        }
    }
}