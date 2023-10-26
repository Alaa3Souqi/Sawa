package com.aspire.sawa.models

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

open class Category(
    @StringRes var name: Int,
    @DrawableRes val image: Int,
    @ColorRes val color: Int
) {

    private var d = 2

    fun a() {
        d-1
    }


    constructor(name2: Int) : this(3, 2, 4) {
        d = name2

    }

    constructor(name2: Int, a: Int) : this(3, 2, 4) {

    }
}