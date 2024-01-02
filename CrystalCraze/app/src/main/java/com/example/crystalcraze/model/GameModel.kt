package com.example.crystalcraze.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameModel(
    val id: Int,
    val name: String,
    val icon: Int,
    val topUpList: List<TopUpModel>
) : Parcelable