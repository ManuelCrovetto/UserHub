package com.example.userhub.core.ex

import androidx.appcompat.widget.AppCompatImageView
import com.example.userhub.R
import com.example.userhub.data.constants.ApiConstants

fun AppCompatImageView.loadGenderImage(gender: String) {
    when (gender) {
        ApiConstants.MALE_GENDER -> this.setImageResource(R.drawable.ic_baseline_male_24)
        ApiConstants.FEMALE_GENDER -> this.setImageResource(R.drawable.ic_baseline_female_24)
    }
}