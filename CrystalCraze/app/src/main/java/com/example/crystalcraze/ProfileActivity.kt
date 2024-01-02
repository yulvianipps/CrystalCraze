package com.example.crystalcraze

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.crystalcraze.database.DatabaseContract
import com.example.crystalcraze.database.GameHelper
import com.example.crystalcraze.databinding.ActivityProfileBinding
import com.example.crystalcraze.helper.MappingHelper
import com.example.crystalcraze.model.UserModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var gameHelper: GameHelper
    private var userData = UserModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        gameHelper = GameHelper(applicationContext)
        gameHelper.open()
        getUserData()
        binding.ivBack.setOnClickListener { finish() }
        lifecycleScope.launch {
            binding.btnLogout.setOnClickListener {
                val rowId = gameHelper.updateUserById(userData.username, ContentValues().apply {
                    put(
                        DatabaseContract.UserColumns.USERNAME,
                        userData.username
                    )
                    put(
                        DatabaseContract.UserColumns.EMAIL,
                        userData.email
                    )
                    put(
                        DatabaseContract.UserColumns.PASSWORD,
                        userData.password
                    )
                    put(
                        DatabaseContract.UserColumns.IS_LOGIN,
                        false
                    )
                })
                if (rowId > 0) {
                    startActivity(Intent(this@ProfileActivity, Login::class.java))
                    finishAffinity()
                } else {
                    Toast.makeText(
                        this@ProfileActivity,
                        "Maaf terjadi kesalahan saat logout!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            binding.btnUpdate.setOnClickListener {
                val values = ContentValues().apply {
                    put(
                        DatabaseContract.UserColumns.USERNAME,
                        binding.etUsername.text.toString().trim()
                    )
                    put(
                        DatabaseContract.UserColumns.EMAIL,
                        binding.etEmail.text.toString().trim()
                    )
                    put(
                        DatabaseContract.UserColumns.PASSWORD,
                        userData.password
                    )
                    put(
                        DatabaseContract.UserColumns.IS_LOGIN,
                        true
                    )
                }
                val rowId = gameHelper.updateUserById(userData.username, values)
                if (rowId > 0) {
                    binding.etUsername.clearFocus()
                    binding.etEmail.clearFocus()
                    getUserData()
                    Toast.makeText(
                        this@ProfileActivity,
                        "Berhasil update data!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@ProfileActivity,
                        "Maaf terjadi kesalahan saat update data!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun getUserData() {
        lifecycleScope.launch {
            val deferred: Deferred<UserModel> = async(Dispatchers.IO) {
                val cursor = gameHelper.getUserLogin()
                MappingHelper.mapCursorToUserModel(cursor)
            }
            val user = deferred.await()
            userData = user
            binding.etUsername.setText(userData.username)
            binding.etEmail.setText(userData.email)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        gameHelper.close()
    }
}