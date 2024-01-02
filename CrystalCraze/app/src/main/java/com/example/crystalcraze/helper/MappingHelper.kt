package com.example.crystalcraze.helper

import android.database.Cursor
import com.example.crystalcraze.database.DatabaseContract
import com.example.crystalcraze.model.GameModel
import com.example.crystalcraze.model.TopUpModel
import com.example.crystalcraze.model.TransactionHistoryModel
import com.example.crystalcraze.model.UserModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object MappingHelper {

    fun mapCursorToUserModel(cursor: Cursor?): UserModel {
        val userModel = UserModel()

        cursor?.apply {
            while (moveToNext()) {
                userModel.id = getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns._ID))
                userModel.username =
                    getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.USERNAME))
                userModel.email =
                    getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.EMAIL))
                userModel.password =
                    getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.PASSWORD))
            }
        }
        return userModel
    }

    fun mapCursorToAllGames(cursor: Cursor?): ArrayList<GameModel> {
        val gamesList = ArrayList<GameModel>()
        val gson = Gson()
        cursor?.apply {
            while (moveToNext()) {
                val type = object : TypeToken<Array<TopUpModel>>() {}.type
                val topUpList: Array<TopUpModel> = gson.fromJson(
                    getString(getColumnIndexOrThrow(DatabaseContract.GameColumns.TOP_UP_LIST)),
                    type
                )
                gamesList.add(
                    GameModel(
                        getInt(getColumnIndexOrThrow(DatabaseContract.GameColumns._ID)),
                        getString(getColumnIndexOrThrow(DatabaseContract.GameColumns.GAME_NAME)),
                        getInt(getColumnIndexOrThrow(DatabaseContract.GameColumns.ICON_GAME)),
                        topUpList.toList(),
                    )
                )
            }
        }
        return gamesList
    }

    fun mapCursorToMyTransactionHistory(cursor: Cursor?): ArrayList<TransactionHistoryModel> {
        val transactionHistoryList = ArrayList<TransactionHistoryModel>()

        cursor?.apply {
            while (moveToNext()) {
                transactionHistoryList.add(
                    TransactionHistoryModel(
                        id = getInt(getColumnIndexOrThrow(DatabaseContract.CheckoutColumns._ID)),
                        dateTime = getString(getColumnIndexOrThrow(DatabaseContract.CheckoutColumns.DATE_TIME)),
                        accountId = getString(getColumnIndexOrThrow(DatabaseContract.CheckoutColumns.ACCOUNT_ID)),
                        titleTopUp = getString(getColumnIndexOrThrow(DatabaseContract.CheckoutColumns.TITLE_TOPUP)),
                        paymentMethod = getInt(getColumnIndexOrThrow(DatabaseContract.CheckoutColumns.PAYMENT_METHOD)),
                        gameName = getString(getColumnIndexOrThrow(DatabaseContract.CheckoutColumns.GAME_NAME)),
                        gameIcon = getInt(getColumnIndexOrThrow(DatabaseContract.CheckoutColumns.GAME_ICON)),
                        priceTopUp = getInt(getColumnIndexOrThrow(DatabaseContract.CheckoutColumns.PRICE_TOPUP)),
                        whatsappNumber = getString(getColumnIndexOrThrow(DatabaseContract.CheckoutColumns.WHATSAPP_NUMBER)),
                        ownedBy = getInt(getColumnIndexOrThrow(DatabaseContract.CheckoutColumns.OWNED_BY)),
                    )
                )
            }
        }
        return transactionHistoryList
    }
}