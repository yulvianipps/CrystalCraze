package com.example.crystalcraze.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.crystalcraze.R
import com.example.crystalcraze.database.DatabaseContract.CheckoutColumns.Companion.CHECKOUT_TABLE_NAME
import com.example.crystalcraze.database.DatabaseContract.GameColumns.Companion.GAME_TABLE_NAME
import com.example.crystalcraze.database.DatabaseContract.UserColumns.Companion.USER_TABLE_NAME
import com.example.crystalcraze.helper.TopUpListHelper
import com.google.gson.Gson

internal class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val gson = Gson()

    companion object {

        private const val DATABASE_NAME = "dbapp"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE_USER = "CREATE TABLE $USER_TABLE_NAME" +
            " (${DatabaseContract.UserColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
            " ${DatabaseContract.UserColumns.USERNAME} TEXT NOT NULL," +
            " ${DatabaseContract.UserColumns.EMAIL} TEXT NOT NULL," +
            " ${DatabaseContract.UserColumns.PASSWORD} TEXT NOT NULL," +
            " ${DatabaseContract.UserColumns.IS_LOGIN} BOOLEAN NOT NULL)"

        private const val SQL_CREATE_TABLE_GAME = "CREATE TABLE $GAME_TABLE_NAME" +
            " (${DatabaseContract.GameColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
            " ${DatabaseContract.GameColumns.GAME_NAME} TEXT NOT NULL," +
            " ${DatabaseContract.GameColumns.ICON_GAME} INT NOT NULL," +
            " ${DatabaseContract.GameColumns.TOP_UP_LIST} INT NOT NULL)"

        private const val SQL_CREATE_TABLE_CHECKOUT = "CREATE TABLE $CHECKOUT_TABLE_NAME" +
            " (${DatabaseContract.CheckoutColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
            " ${DatabaseContract.CheckoutColumns.DATE_TIME} TEXT NOT NULL," +
            " ${DatabaseContract.CheckoutColumns.ACCOUNT_ID} TEXT NOT NULL," +
            " ${DatabaseContract.CheckoutColumns.TITLE_TOPUP} TEXT NOT NULL," +
            " ${DatabaseContract.CheckoutColumns.PAYMENT_METHOD} INT NOT NULL," +
            " ${DatabaseContract.CheckoutColumns.GAME_NAME} TEXT NOT NULL," +
            " ${DatabaseContract.CheckoutColumns.GAME_ICON} INT NOT NULL," +
            " ${DatabaseContract.CheckoutColumns.PRICE_TOPUP} INT NOT NULL," +
            " ${DatabaseContract.CheckoutColumns.WHATSAPP_NUMBER} TEXT NOT NULL," +
            " ${DatabaseContract.CheckoutColumns.OWNED_BY} INT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val valoIcon = R.drawable.valo
        val mlIcon = R.drawable.ml
        val pubgIcon = R.drawable.pubg
        val lolIcon = R.drawable.ll
        val tbtIcon = R.drawable.tbt
        val rslIcon = R.drawable.rsl
        val rokIcon = R.drawable.rok
        val lorIcon = R.drawable.lor
        val ffIcon = R.drawable.ff
        val gorIcon = R.drawable.gor
        val disorderIcon = R.drawable.d
        val crIcon = R.drawable.cor
        val cocIcon = R.drawable.coc
        val cmIcon = R.drawable.cm
        val aovIcon = R.drawable.av
        val hagoIcon = R.drawable.hago
        val valoItems = gson.toJson(TopUpListHelper.getPointsItems())
        val mlItems = gson.toJson(TopUpListHelper.getDiamondsItems())
        val pubgItems = gson.toJson(TopUpListHelper.getUCGlobalItems())
        val lolItems = gson.toJson(TopUpListHelper.getRPItems())
        db.execSQL(SQL_CREATE_TABLE_USER)
        db.execSQL(SQL_CREATE_TABLE_GAME)
        db.execSQL(SQL_CREATE_TABLE_CHECKOUT)
        db.execSQL("INSERT INTO $GAME_TABLE_NAME VALUES (0, 'Valorant', ($valoIcon), '$valoItems')")
        db.execSQL("INSERT INTO $GAME_TABLE_NAME VALUES (1, 'Mobile Legends', ($mlIcon), '$mlItems')")
        db.execSQL("INSERT INTO $GAME_TABLE_NAME VALUES (2, 'PUBG', ($pubgIcon), '$pubgItems')")
        db.execSQL("INSERT INTO $GAME_TABLE_NAME VALUES (3, 'League of Legends', ($lolIcon), '$lolItems')")
        db.execSQL("INSERT INTO $GAME_TABLE_NAME VALUES (4, 'The Bloop Troop', ($tbtIcon), '$valoItems')")
        db.execSQL("INSERT INTO $GAME_TABLE_NAME VALUES (5, 'Raid Shadow Legens', ($rslIcon), '$mlItems')")
        db.execSQL("INSERT INTO $GAME_TABLE_NAME VALUES (6, 'Rise of Kingdoms', ($rokIcon), '$pubgItems')")
        db.execSQL("INSERT INTO $GAME_TABLE_NAME VALUES (7, 'Legends of Runeterra', ($lorIcon), '$lolItems')")
        db.execSQL("INSERT INTO $GAME_TABLE_NAME VALUES (8, 'Free Fire', ($ffIcon), '$valoItems')")
        db.execSQL("INSERT INTO $GAME_TABLE_NAME VALUES (9, 'God of War Ragnarok', ($gorIcon), '$mlItems')")
        db.execSQL("INSERT INTO $GAME_TABLE_NAME VALUES (10, 'Disorder', ($disorderIcon), '$pubgItems')")
        db.execSQL("INSERT INTO $GAME_TABLE_NAME VALUES (11, 'Clash Royale', ($crIcon), '$lolItems')")
        db.execSQL("INSERT INTO $GAME_TABLE_NAME VALUES (12, 'Clash of Clans', ($cocIcon), '$valoItems')")
        db.execSQL("INSERT INTO $GAME_TABLE_NAME VALUES (13, 'Coin Master', ($cmIcon), '$mlItems')")
        db.execSQL("INSERT INTO $GAME_TABLE_NAME VALUES (14, 'Arena of Valor', ($aovIcon), '$pubgItems')")
        db.execSQL("INSERT INTO $GAME_TABLE_NAME VALUES (15, 'Hago', ($hagoIcon), '$lolItems')")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $USER_TABLE_NAME")
        db.execSQL("DROP TABLE IF EXISTS $GAME_TABLE_NAME")
        db.execSQL("DROP TABLE IF EXISTS $CHECKOUT_TABLE_NAME")
        onCreate(db)
    }
}