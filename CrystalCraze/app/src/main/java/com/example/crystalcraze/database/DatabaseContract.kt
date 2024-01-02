package com.example.crystalcraze.database

import android.provider.BaseColumns

internal class DatabaseContract {

    internal class UserColumns : BaseColumns {
        companion object {

            const val USER_TABLE_NAME = "user"
            const val _ID = "_id"
            const val USERNAME = "username"
            const val EMAIL = "email"
            const val PASSWORD = "password"
            const val IS_LOGIN = "is_login"
        }
    }

    internal class GameColumns : BaseColumns {
        companion object {

            const val GAME_TABLE_NAME = "game"
            const val _ID = "_id"
            const val GAME_NAME = "game_name"
            const val ICON_GAME = "icon_game"
            const val TOP_UP_LIST = "top_up_list"
        }
    }

    internal class CheckoutColumns : BaseColumns {
        companion object {

            const val CHECKOUT_TABLE_NAME = "checkout"
            const val _ID = "_id"
            const val DATE_TIME = "date_time"
            const val ACCOUNT_ID = "account_id"
            const val TITLE_TOPUP = "title"
            const val PAYMENT_METHOD = "payment"
            const val GAME_NAME = "game_name"
            const val GAME_ICON = "game_icon"
            const val PRICE_TOPUP = "price"
            const val WHATSAPP_NUMBER = "wa_number"
            const val OWNED_BY = "owned_by"
        }
    }
}