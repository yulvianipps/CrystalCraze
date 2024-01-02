package com.example.crystalcraze

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.crystalcraze.database.DatabaseContract
import com.example.crystalcraze.database.GameHelper
import com.example.crystalcraze.databinding.LoginBinding
import com.example.crystalcraze.helper.MappingHelper
import com.example.crystalcraze.model.UserModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class Login : AppCompatActivity() {

    private lateinit var binding: LoginBinding
    private lateinit var gameHelper: GameHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        gameHelper = GameHelper.getInstance(applicationContext)
        gameHelper.open()

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {
            when {
                binding.edtUsername.text.toString().isEmpty() -> {
                    binding.edtUsername.error = "Username tidak boleh kosong!"
                    binding.edtUsername.requestFocus()
                }

                binding.edtPassword.text.toString().isEmpty() -> {
                    binding.edtPassword.error = "Password tidak boleh kosong!"
                    binding.edtPassword.requestFocus()
                }

                else -> {
                    lifecycleScope.launch {
                        val deferred: Deferred<UserModel> = async(Dispatchers.IO) {
                            val cursor = gameHelper.queryByUsername(
                                binding.edtUsername.text.toString().trim()
                            )
                            MappingHelper.mapCursorToUserModel(cursor)
                        }
                        val newUser = deferred.await()

                        if (newUser.username == binding.edtUsername.text.toString()
                                .trim() && newUser.password == binding.edtPassword.text.toString()
                                .trim()
                        ) {
                            val newValues = ContentValues().apply {
                                put(
                                    DatabaseContract.UserColumns.USERNAME,
                                    newUser.username
                                )
                                put(
                                    DatabaseContract.UserColumns.EMAIL,
                                    newUser.email
                                )
                                put(
                                    DatabaseContract.UserColumns.PASSWORD,
                                    newUser.password
                                )
                                put(
                                    DatabaseContract.UserColumns.IS_LOGIN,
                                    true
                                )

                            }

                            val deferredOldUser = async(Dispatchers.IO) {
                                val cursor = gameHelper.getUserLogin()
                                MappingHelper.mapCursorToUserModel(cursor)
                            }
                            val oldUser = deferredOldUser.await()

                            val oldValues = ContentValues().apply {
                                put(
                                    DatabaseContract.UserColumns.USERNAME,
                                    oldUser.username
                                )
                                put(
                                    DatabaseContract.UserColumns.EMAIL,
                                    oldUser.email
                                )
                                put(
                                    DatabaseContract.UserColumns.PASSWORD,
                                    oldUser.password
                                )
                                put(
                                    DatabaseContract.UserColumns.IS_LOGIN,
                                    false
                                )
                            }

                            gameHelper.updateUserLogin(
                                usernameExistingLogin = oldUser.username,
                                usernameNewLogin = newUser.username,
                                oldValues = oldValues,
                                newValues = newValues
                            )
                            Toast.makeText(
                                this@Login,
                                "Berhasil login!",
                                Toast.LENGTH_SHORT
                            ).show()
                            startActivity(Intent(this@Login, Dashboard::class.java))
                            finishAffinity()
                        } else {
                            Toast.makeText(
                                this@Login,
                                "Username/password salah",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }
}