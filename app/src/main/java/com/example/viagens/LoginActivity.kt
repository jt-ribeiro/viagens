package com.example.viagens

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.viagens.databinding.ActivityLoginBinding
import com.example.viagens.ui.viewmodel.UserViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Usando View Binding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializa o ViewModel
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        // Inicializa SharedPreferences
        sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE)

        // Verifica se já existe uma sessão ativa
        checkActiveSession()


        // Clique do botão de login
        binding.loginButton.setOnClickListener {
            val email = binding.emailInput.text.toString().trim()
            val password = binding.passwordInput.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isValidEmail(email)) {
                Toast.makeText(this, "E-mail inválido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Mostrar ProgressBar (se tiver)
            // binding.progressBar.visibility = View.VISIBLE

            // Verificar login no banco usando ViewModel
            userViewModel.loginUser(email, password) { user ->
                if (user != null) {
                    // ✅ Isso agora roda na Main Thread
                    Toast.makeText(this, "Bem-vindo, ${user.name}!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MinhasViagensActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "E-mail ou senha inválidos", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Link para Registro
        binding.registerLink.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun saveUserSession(email: String, name: String) {
        val editor = sharedPreferences.edit()
        editor.putString("email", email)
        editor.putString("name", name)
        editor.putBoolean("is_logged_in", true)
        editor.apply()
    }

    private fun checkActiveSession() {
        val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)
        if (isLoggedIn) {
            val intent = Intent(this, MinhasViagensActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}