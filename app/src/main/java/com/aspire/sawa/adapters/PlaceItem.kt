package com.aspire.sawa.adapters

import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import com.aspire.sawa.R
import com.aspire.sawa.databinding.ItemHomeNearPlacesBinding
import com.aspire.sawa.models.Place
import com.aspire.sawa.unitls.Constraints
import com.xwray.groupie.viewbinding.BindableItem

class PlaceItem(private val item: Place) : BindableItem<ItemHomeNearPlacesBinding>() {

    override fun bind(viewBinding: ItemHomeNearPlacesBinding, position: Int) {
        viewBinding.run {
            tvPlaceName.text = item.name
            ivPlaceImage.setImageResource(item.image)
            tvPlaceDistance.text = item.distance
            tvCapacity.text = item.capacity
            tvOpenedState.text = item.opened

            fun setCapacityStateTextByResources(resId: Int) {
                tvCapacityState.text = root.context.getString(resId)
            }

            fun setOpenedStateTextColorByResources(resId: Int) {
                tvOpenedState.setTextColor(root.context.getColor(resId))
            }

            fun setCapacityStateTint(resId: Int) {
                tvCapacityState.backgroundTintList =
                    AppCompatResources.getColorStateList(root.context, resId)
            }

            tvCapacityState.run {
                when (item.capacityState) {

                    Constraints.MODERATE -> {
                        setCapacityStateTextByResources(R.string.capacity_moderator)
                        setCapacityStateTint(R.color.capacity_moderator)
                    }

                    Constraints.CLOSED -> {
                        setCapacityStateTextByResources(R.string.capacity_closed)
                        setCapacityStateTint(R.color.capacity_closed)
                        setOpenedStateTextColorByResources(R.color.capacity_crowded)
                    }

                    Constraints.LIGHT -> {
                        setCapacityStateTextByResources(R.string.capacity_light)
                        setCapacityStateTint(R.color.capacity_light)
                    }

                    Constraints.CROWDED -> {
                        setCapacityStateTextByResources(R.string.capacity_crowded)
                        setCapacityStateTint(R.color.capacity_crowded)
                    }
                }
            }
        }
    }

    override fun getLayout(): Int = R.layout.item_home_near_places

    override fun initializeViewBinding(view: View): ItemHomeNearPlacesBinding =
        ItemHomeNearPlacesBinding.bind(view)
}