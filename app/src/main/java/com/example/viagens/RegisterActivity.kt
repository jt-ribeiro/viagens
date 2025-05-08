package com.example.viagens

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.viagens.data.model.User
import com.example.viagens.databinding.ActivityRegisterBinding
import com.example.viagens.ui.viewmodel.UserViewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Infla o layout com View Binding
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializa o ViewModel
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        // Clique do botão de registro
        binding.registerButton.setOnClickListener {
            val name = binding.nameInput.text.toString().trim()
            val email = binding.emailInput.text.toString().trim()
            val password = binding.passwordInput.text.toString()
            val confirmPassword = binding.confirmPasswordInput.text.toString()

            // Validação dos campos
            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Verifica se o usuário já existe
            userViewModel.checkIfUserExists(email) { exists ->
                if (exists) {
                    runOnUiThread {
                        Toast.makeText(this, "E-mail já cadastrado", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Cria o usuário e salva no banco
                    val user = User(email = email, name = name, password = password)
                    userViewModel.registerUser(user)

                    Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()

                    // Vai para a tela de login
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            }
        }

        // Link para Login
        binding.loginLink.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
