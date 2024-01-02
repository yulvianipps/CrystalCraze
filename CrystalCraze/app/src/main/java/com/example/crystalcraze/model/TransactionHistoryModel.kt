package com.example.crystalcraze.model

data class TransactionHistoryModel(
    val id: Int,
    val dateTime: String,
    val accountId: String,
    val titleTopUp: String,
    val paymentMethod: Int,
    val gameName: String,
    val gameIcon: Int,
    val priceTopUp: Int,
    val whatsappNumber: String,
    val ownedBy: Int
)