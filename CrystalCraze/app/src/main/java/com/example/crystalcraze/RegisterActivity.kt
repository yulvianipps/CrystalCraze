package com.example.crystalcraze

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.crystalcraze.database.DatabaseContract
import com.example.crystalcraze.database.GameHelper
import com.example.crystalcraze.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var gameHelper: GameHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        gameHelper = GameHelper.getInstance(applicationContext)
        gameHelper.open()

        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }

        binding.btnRegister.setOnClickListener {
            when {
                binding.edtUsername.text.toString().isEmpty() -> {
                    binding.edtUsername.error = "Username tidak boleh kosong!"
                    binding.edtUsername.requestFocus()
                }

                binding.edtEmail.text.toString().isEmpty() -> {
                    binding.edtEmail.error = "Email tidak boleh kosong!"
                    binding.edtEmail.requestFocus()
                }

                binding.edtPassword.text.toString().isEmpty() -> {
                    binding.edtPassword.error = "Password tidak boleh kosong!"
                    binding.edtPassword.requestFocus()
                }

                else -> {
                    val values = ContentValues().apply {
                        put(
                            DatabaseContract.UserColumns.USERNAME,
                            binding.edtUsername.text.toString().trim()
                        )
                        put(
                            DatabaseContract.UserColumns.EMAIL,
                            binding.edtEmail.text.toString().trim()
                        )
                        put(
                            DatabaseContract.UserColumns.PASSWORD,
                            binding.edtPassword.text.toString().trim()
                        )
                        put(
                            DatabaseContract.UserColumns.IS_LOGIN,
                            false
                        )
                    }
                    val result = gameHelper.insert(values)

                    if (result > 0) {
                        Toast.makeText(this, "Berhasil daftar!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, Login::class.java))
                        finish()
                    } else {
                        Toast.makeText(
                            this@RegisterActivity,
                            "Gagal Daftar akun",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            }
        }
    }
}