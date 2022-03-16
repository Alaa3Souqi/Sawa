package com.aspire.sawa.models

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Category(@StringRes val name: Int, @DrawableRes val image: Int, @ColorRes val color: Int)