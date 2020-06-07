package koleton.sample

import androidx.annotation.DrawableRes

data class Journey(
    val date: String,
    @DrawableRes val icon: Int,
    val address: String,
    val price: String
)