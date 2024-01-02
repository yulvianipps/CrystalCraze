package com.example.crystalcraze.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.example.crystalcraze.database.DatabaseContract.CheckoutColumns.Companion.CHECKOUT_TABLE_NAME
import com.example.crystalcraze.database.DatabaseContract.GameColumns.Companion.GAME_TABLE_NAME
import com.example.crystalcraze.database.DatabaseContract.GameColumns.Companion._ID
import com.example.crystalcraze.database.DatabaseContract.UserColumns.Companion.IS_LOGIN
import com.example.crystalcraze.database.DatabaseContract.UserColumns.Companion.USERNAME
import com.example.crystalcraze.database.DatabaseContract.UserColumns.Companion.USER_TABLE_NAME

class GameHelper(context: Context) {
    private var dataBaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    fun close() {
        dataBaseHelper.close()

        if (database.isOpen)
            database.close()
    }

    fun updateUserLogin(usernameExistingLogin: String, usernameNewLogin: String, oldValues: ContentValues?, newValues: ContentValues?): Int {
        database.update(USER_TABLE_NAME, oldValues, "$USERNAME = ?", arrayOf(usernameExistingLogin))
        return database.update(USER_TABLE_NAME, newValues, "$USERNAME = ?", arrayOf(usernameNewLogin))
    }

    fun getUserLogin(): Cursor {
        return database.query(USER_TABLE_NAME, null, "$IS_LOGIN = true", null, null, null, null, null)
    }

    fun queryByUsername(username: String): Cursor {
        return database.query(USER_TABLE_NAME, null, "$USERNAME = ?", arrayOf(username), null, null, null, null)
    }

    fun insert(values: ContentValues?): Long {
        return database.insert(USER_TABLE_NAME, null, values)
    }

    fun updateUserById(username: String, values: ContentValues?): Int {
        return database.update(USER_TABLE_NAME, values, "$USERNAME = ?", arrayOf(username))
    }

    fun deleteById(id: String): Int {
        return database.delete(USER_TABLE_NAME, "$_ID = '$id'", null)
    }

    fun queryAllGames(): Cursor {
        return database.query(
            GAME_TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC",
            null
        )
    }

    fun deleteMyTransactionHistoryById(id: String): Int {
        return database.delete(CHECKOUT_TABLE_NAME, "$_ID = '$id'", null)
    }

    fun queryAllMyTransactionHistory(): Cursor {
        return database.query(
            CHECKOUT_TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC",
            null
        )
    }

    fun insertCheckoutTopUpGames(values: ContentValues?): Long {
        return database.insert(CHECKOUT_TABLE_NAME, null, values)
    }

    companion object {

        private var INSTANCE: GameHelper? = null

        fun getInstance(context: Context): GameHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: GameHelper(context)
            }
    }

}