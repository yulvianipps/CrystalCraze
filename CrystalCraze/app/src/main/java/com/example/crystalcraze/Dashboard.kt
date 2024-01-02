package com.example.crystalcraze

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crystalcraze.database.GameHelper
import com.example.crystalcraze.databinding.DashboardBinding
import com.example.crystalcraze.helper.MappingHelper
import com.example.crystalcraze.model.GameModel
import com.example.crystalcraze.model.TransactionHistoryModel
import com.example.crystalcraze.model.UserModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class Dashboard : AppCompatActivity() {

    private lateinit var binding: DashboardBinding
    private lateinit var gameHelper: GameHelper
    private var userData = UserModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DashboardBinding.inflate(layoutInflater)
        gameHelper = GameHelper(applicationContext)
        gameHelper.open()

        binding.ivProfile.setOnClickListener {
            startActivity(Intent(this@Dashboard, ProfileActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        getUserLogin()
        getAllTopUpGames()
    }

    private fun getUserLogin() {
        lifecycleScope.launch {
            val deferred: Deferred<UserModel> = async(Dispatchers.IO) {
                val cursor = gameHelper.getUserLogin()
                MappingHelper.mapCursorToUserModel(cursor)
            }
            val data = deferred.await()
            userData = data

            if (userData.username.isNotEmpty()) {
                setContentView(binding.root)
                binding.tvUsername.text = userData.username
            } else {
                startActivity(Intent(this@Dashboard, Login::class.java))
                finishAffinity()
            }
            getAllTransactionHistory()
        }
    }

    private fun getAllTopUpGames() {
        lifecycleScope.launch {
            val deferred: Deferred<ArrayList<GameModel>> = async(Dispatchers.IO) {
                val cursor = gameHelper.queryAllGames()
                MappingHelper.mapCursorToAllGames(cursor)
            }
            val data = deferred.await()

            if (data.isNotEmpty()) {
                binding.rvTopUp.layoutManager =
                    GridLayoutManager(this@Dashboard, 2, GridLayoutManager.HORIZONTAL, false)
                binding.rvTopUp.adapter = TopUpAdapter(data)
                binding.tvEmptyTopUpGames.isVisible = false
                binding.rvTopUp.isVisible = true
            } else {
                binding.tvEmptyTopUpGames.isVisible = true
                binding.rvTopUp.isVisible = false
            }
        }
    }

    private fun getAllTransactionHistory() {
        lifecycleScope.launch {
            val deferred: Deferred<ArrayList<TransactionHistoryModel>> = async(Dispatchers.IO) {
                val cursor = gameHelper.queryAllMyTransactionHistory()
                MappingHelper.mapCursorToMyTransactionHistory(cursor)
            }
            val data = deferred.await().filter { it.ownedBy == userData.id }

            if (data.isNotEmpty()) {
                binding.tvEmptyTransactionHistory.isVisible = false
                binding.rvTransactionHistory.isVisible = true
                binding.rvTransactionHistory.layoutManager = LinearLayoutManager(this@Dashboard)
                binding.rvTransactionHistory.adapter = TransactionHistoryAdapter(data) {
                    showDialogDelete(it)
                }
            } else {
                binding.tvEmptyTransactionHistory.isVisible = true
                binding.rvTransactionHistory.isVisible = false
            }
        }
    }

    private fun showDialogDelete(data: TransactionHistoryModel) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Kamu ingin menghapus riwayat transaksi ini?")
        builder.setMessage("Dengan menghapus, riwayat transaksi ini tidak dapat dikembalikan.\nPilih Iya untuk menghapus.\nPilih Batal untuk kembali.")
        builder.setPositiveButton("Iya") { dialogInterface: DialogInterface, _: Int ->
            val rowId = gameHelper.deleteMyTransactionHistoryById(data.id.toString())
            if (rowId > 0) {
                Toast.makeText(this, "Berhasil hapus transaksi history!", Toast.LENGTH_SHORT).show()
                dialogInterface.dismiss()
                getUserLogin()
            } else {
                Toast.makeText(this, "Gagal hapus transaksi history!", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("Batal") { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.dismiss()
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }
}