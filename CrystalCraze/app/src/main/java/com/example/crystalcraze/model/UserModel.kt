package com.example.crystalcraze.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    var id: Int = 0,
    var username: String = "",
    var email: String = "",
    var password: String = "",
) : Parcelable