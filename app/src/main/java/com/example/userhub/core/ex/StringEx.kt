package com.example.userhub.core.ex

import java.util.*

fun String?.getCountryName(): String {
    return try {
        val country = Locale(Locale.getDefault().language, this ?: "")
        country.displayCountry.ifEmpty { "" }
    } catch (e:Exception) {
        e.printStackTrace()
        ""
    }
}