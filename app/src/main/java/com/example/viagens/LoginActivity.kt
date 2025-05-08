package com.example.viagens

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.viagens.databinding.ActivityLoginBinding
import com.example.viagens.ui.viewmodel.UserViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Usando View Binding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializa o ViewModel
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        // Clique do botão de login
        binding.loginButton.setOnClickListener {
            val email = binding.emailInput.text.toString().trim()
            val password = binding.passwordInput.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Verificar login no banco usando ViewModel
            userViewModel.loginUser(email, password) { user ->
                if (user != null) {
                    // Login bem-sucedido
                    Toast.makeText(this, "Bem-vindo, ${user.name}!", Toast.LENGTH_SHORT).show()

                    // Navegar para a tela principal (por exemplo, MainActivity)
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    runOnUiThread {
                        Toast.makeText(this, "E-mail ou senha inválidos", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        // Link para Registro
        binding.registerLink.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}