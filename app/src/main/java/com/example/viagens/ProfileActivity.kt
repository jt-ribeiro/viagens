package com.example.viagens

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.viagens.data.model.User
import com.example.viagens.databinding.ActivityProfileBinding
import com.example.viagens.ui.viewmodel.UserViewModel

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var currentUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        loadCurrentUser()
        setupEditListeners()
        setupSaveButton()

        // Configurar a navegação da barra inferior
        binding.navView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.navigation_home -> {
                    startActivity(Intent(this, MinhasViagensActivity::class.java))
                    // Não chamar finish aqui, pois queremos manter a navegação
                    true
                }
                R.id.navigation_dashboard -> {
                    startActivity(Intent(this, GaleriaActivity::class.java))
                    // Não chamar finish aqui
                    true
                }
                R.id.navigation_profile -> {
                    // Já está no perfil, então não fazemos nada
                    true
                }
                else -> false
            }
        }

        // Destacar o ícone de Perfil na barra de navegação
        binding.navView.selectedItemId = R.id.navigation_profile
    }

    private fun loadCurrentUser() {
        val sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE)
        val userEmail = sharedPreferences.getString("email", null)

        if (userEmail.isNullOrEmpty()) {
            showToast("Erro: sessão inválida. Faça login novamente.")
            finish() // Finaliza a activity e vai para o login
            return
        }

        userViewModel.getUserByEmail(userEmail) { user ->
            if (user != null) {
                currentUser = user
                updateUIWithUserData(user)
            } else {
                showToast("Utilizador não encontrado.")
            }
        }
    }

    private fun updateUIWithUserData(user: User) {
        binding.nameEditText.setText(user.name)
        binding.emailEditText.setText(user.email)
        binding.passwordEditText.setText("********")

        binding.notificationsSwitch.isChecked = true

        // Desabilitar os campos inicialmente
        binding.nameEditText.isEnabled = false
        binding.emailEditText.isEnabled = false
        binding.passwordEditText.isEnabled = false
    }

    private fun setupEditListeners() {
        binding.nameEditIcon.setOnClickListener {
            binding.nameEditText.isEnabled = true
            binding.nameEditText.requestFocus()
        }

        binding.emailEditIcon.setOnClickListener {
            binding.emailEditText.isEnabled = true
            binding.emailEditText.requestFocus()
        }

        binding.passwordEditIcon.setOnClickListener {
            binding.passwordEditText.isEnabled = true
            binding.passwordEditText.setText("") // Limpa os asteriscos
            binding.passwordEditText.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.passwordEditText.requestFocus()
        }
    }

    private fun setupSaveButton() {
        binding.saveChangesButton.setOnClickListener {
            val updatedName = binding.nameEditText.text.toString()
            val updatedEmail = binding.emailEditText.text.toString()
            val updatedPassword = binding.passwordEditText.text.toString()

            currentUser = currentUser.copy(
                name = updatedName,
                email = updatedEmail,
                password = if (updatedPassword.isNotBlank() && updatedPassword != "********") {
                    updatedPassword
                } else {
                    currentUser.password
                }
            )

            userViewModel.updateUser(currentUser)

            showToast("Alterações guardadas!")

            // Bloqueia novamente os campos
            binding.nameEditText.isEnabled = false
            binding.emailEditText.isEnabled = false
            binding.passwordEditText.isEnabled = false
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
