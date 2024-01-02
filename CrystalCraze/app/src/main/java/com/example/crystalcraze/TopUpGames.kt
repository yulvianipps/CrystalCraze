package com.example.crystalcraze

import android.content.ContentValues
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.crystalcraze.database.DatabaseContract
import com.example.crystalcraze.database.GameHelper
import com.example.crystalcraze.databinding.TopUpGamesBinding
import com.example.crystalcraze.helper.MappingHelper
import com.example.crystalcraze.model.GameModel
import com.example.crystalcraze.model.UserModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class TopUpGames : AppCompatActivity() {

    private lateinit var binding: TopUpGamesBinding
    private var gameModel: GameModel? = null
    private var selectedPaymentMethod = -1
    private lateinit var gameHelper: GameHelper
    private lateinit var adapter: NominalTopUpAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TopUpGamesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        gameHelper = GameHelper(applicationContext)
        gameHelper.open()

        gameModel = intent.getParcelableExtra("data")

        binding.rvNominal.layoutManager =
            GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        gameModel?.topUpList?.let {
            adapter = NominalTopUpAdapter(it)
        }
        binding.rvNominal.adapter = adapter

        binding.ivBack.setOnClickListener { finish() }

        binding.btnPesanSekarang.setOnClickListener {
            when {
                binding.etId.text.toString().trim().isEmpty() -> {
                    Toast.makeText(this, "Silakan masukkan ID", Toast.LENGTH_SHORT).show()
                }

                adapter.getSelectedItemPosition() == -1 -> {
                    Toast.makeText(this, "Silahkan pilih nominal top up", Toast.LENGTH_SHORT).show()
                }

                selectedPaymentMethod == -1 -> {
                    Toast.makeText(this, "Silahkan pilih metode pembayaran", Toast.LENGTH_SHORT)
                        .show()
                }

                !binding.ivPaymentSlip.isVisible -> {
                    Toast.makeText(this, "Silahkan upload bukti pembayaran", Toast.LENGTH_SHORT)
                        .show()
                }

                binding.etWhatsapp.text.toString().trim().isEmpty() -> {
                    Toast.makeText(this, "Silahkan masukkan nomor whatsapp", Toast.LENGTH_SHORT)
                        .show()
                }

                else -> {
                    lifecycleScope.launch {
                        val deferred: Deferred<UserModel> = async(Dispatchers.IO) {
                            val cursor = gameHelper.getUserLogin()
                            MappingHelper.mapCursorToUserModel(cursor)
                        }
                        val user = deferred.await()

                        val values = ContentValues().apply {
                            put(
                                DatabaseContract.CheckoutColumns.DATE_TIME,
                                getCurrentDateTimeInIndonesianLocale()
                            )
                            put(
                                DatabaseContract.CheckoutColumns.ACCOUNT_ID,
                                binding.etId.text.toString().trim()
                            )
                            put(
                                DatabaseContract.CheckoutColumns.TITLE_TOPUP,
                                adapter.getItemByPosition(adapter.getSelectedItemPosition()).title
                            )
                            put(DatabaseContract.CheckoutColumns.PAYMENT_METHOD, selectedPaymentMethod)
                            put(DatabaseContract.CheckoutColumns.GAME_NAME, gameModel?.name)
                            put(DatabaseContract.CheckoutColumns.GAME_ICON, gameModel?.icon)
                            put(
                                DatabaseContract.CheckoutColumns.PRICE_TOPUP,
                                adapter.getItemByPosition(adapter.getSelectedItemPosition()).price
                            )
                            put(
                                DatabaseContract.CheckoutColumns.WHATSAPP_NUMBER,
                                binding.etWhatsapp.text.toString().trim()
                            )
                            put(
                                DatabaseContract.CheckoutColumns.OWNED_BY,
                                user.id
                            )
                        }
                        val result = gameHelper.insertCheckoutTopUpGames(values)

                        if (result > 0) {
                            Toast.makeText(this@TopUpGames, "Berhasil top up game!", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@TopUpGames, Dashboard::class.java))
                            finishAffinity()
                        } else {
                            Toast.makeText(
                                this@TopUpGames,
                                "Gagal top up game!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }

        binding.ivQris.setOnClickListener {
            selectedPaymentMethod = 0
            setDefaultBackground()
            binding.ivQris.setBackgroundResource(R.color.white)
        }
        binding.ivBca.setOnClickListener {
            selectedPaymentMethod = 1
            setDefaultBackground()
            binding.ivBca.setBackgroundResource(R.color.white)
        }
        binding.ivBni.setOnClickListener {
            selectedPaymentMethod = 2
            setDefaultBackground()
            binding.ivBni.setBackgroundResource(R.color.white)
        }
        binding.ivShopeePay.setOnClickListener {
            selectedPaymentMethod = 3
            setDefaultBackground()
            binding.ivShopeePay.setBackgroundResource(R.color.white)
        }
        binding.ivAlfamart.setOnClickListener {
            selectedPaymentMethod = 4
            setDefaultBackground()
            binding.ivAlfamart.setBackgroundResource(R.color.white)
        }
        binding.ivDana.setOnClickListener {
            selectedPaymentMethod = 5
            setDefaultBackground()
            binding.ivDana.setBackgroundResource(R.color.white)
        }

        binding.btnUploadPaymentSlip.setOnClickListener {
            chooseAFile()
        }
    }

    private fun setDefaultBackground() {
        binding.ivQris.setBackgroundResource(android.R.color.transparent)
        binding.ivBca.setBackgroundResource(android.R.color.transparent)
        binding.ivBni.setBackgroundResource(android.R.color.transparent)
        binding.ivShopeePay.setBackgroundResource(android.R.color.transparent)
        binding.ivAlfamart.setBackgroundResource(android.R.color.transparent)
        binding.ivDana.setBackgroundResource(android.R.color.transparent)
    }

    private fun getCurrentDateTimeInIndonesianLocale(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd MMMM yyyy, HH.mm", Locale("id", "ID"))
        return dateFormat.format(calendar.time)
    }

    private fun chooseAFile() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            binding.ivPaymentSlip.visibility = View.VISIBLE
            binding.ivPaymentSlip.setImageURI(selectedImg)
        }
    }

}