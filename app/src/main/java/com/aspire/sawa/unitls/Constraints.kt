package com.aspire.sawa.unitls

import com.aspire.sawa.R
import com.aspire.sawa.models.Category
import com.aspire.sawa.models.Place

object Constraints {
    const val CLOSED = "closed"
    const val LIGHT = "light"
    const val CROWDED = "crowded"
    const val MODERATE = "moderate"

    const val MY_PREFS_NAME = "settings"

    const val LANGUAGE = "language"
    const val ARABIC = "ar"
    const val ENGLISH = "en"

    const val THEME = "theme"
    const val PINK = "pink"
    const val BLUE = "blue"

    val categoryList = listOf(
        Category(R.string.all, R.drawable.ic_grid, R.color.all),
        Category(R.string.retail, R.drawable.ic_shopping_bag, R.color.alice_blue),
        Category(R.string.health, R.drawable.ic_hospital, R.color.light_cyan),
        Category(R.string.banks, R.drawable.ic_bank, R.color.honeydew),
        Category(R.string.government, R.drawable.ic_government_building, R.color.government),
        Category(R.string.entertainment, R.drawable.ic_circus, R.color.entertainment),
        Category(R.string.telecommunication, R.drawable.ic_sim_card, R.color.sim),
        Category(R.string.education, R.drawable.ic_school, R.color.education),
        Category(R.string.travel, R.drawable.ic_plane, R.color.travel),
        Category(R.string.insurance, R.drawable.ic_key, R.color.honeydew),
    )

    val placeList = listOf(
        Place(
            "Star Bucks",
            "200 m away",
            R.drawable.star_bucks,
            "Open now: 8am–9pm",
            "Capacity: 142/250",
            MODERATE
        ),
        Place(
            "Carrefour Gardens",
            "1.2 km away",
            R.drawable.carrefour,
            "Closed: Friday",
            "Capacity:",
            CLOSED
        ),
        Place(
            "Carrefour Jabal Al Hussein",
            "1.4 km away",
            R.drawable.carrefour,
            "Open now: 8am–9pm",
            "Capacity: 11/250:",
            LIGHT
        ),

        Place(
            "Carrefour Galleria Mall",
            "5 km away",
            R.drawable.carrefour,
            "Open now: 8am–9pm",
            "Capacity: 243/250",
            CROWDED
        )
    )


//        behavior.addBottomSheetCallback(object : BottomSheetCallback() {
//            var oldOffSet = 0f
//
//            override fun onStateChanged(bottomSheet: View, newState: Int) {
//                if (newState == STATE_SETTLING) {
//                    if (oldOffSet > 0.5 ) {
//                        behavior.state = STATE_EXPANDED
//                    } else
//                        behavior.state = STATE_COLLAPSED
//                }
//            }
//
//            override fun onSlide(bottomSheet: View, slideOffset: Float) {
//                oldOffSet = slideOffset
//
//            }
//        })

}