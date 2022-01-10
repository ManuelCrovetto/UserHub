package com.example.userhub.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Result(
    val dob: Dob,
    val email: String,
    val gender: String,
    val id: Id,
    val location: Location,
    val name: Name,
    val nat: String,
    val phone: String,
    val picture: Picture
): Parcelable