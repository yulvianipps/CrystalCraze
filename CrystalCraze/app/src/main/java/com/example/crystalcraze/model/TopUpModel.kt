package com.example.crystalcraze.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TopUpModel(
    val title: String,
    val price: Int
) : Parcelable