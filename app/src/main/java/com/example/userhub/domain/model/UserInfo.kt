package com.example.userhub.domain.model

import android.os.Parcelable
import com.example.userhub.data.model.Result
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInfo (val response: Result): Parcelable