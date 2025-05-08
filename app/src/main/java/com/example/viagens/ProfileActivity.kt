package com.example.viagens

import android.os.Bundle
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

        // Carregar dados do utilizador logado
        loadCurrentUser()

        // Clique nos ícones de editar
        setupEditListeners()

        // Clique no botão "Guardar Alterações"
        setupSaveButton()
    }

    private fun loadCurrentUser() {
        // Exemplo: pegar o email do usuário logado via SharedPreferences
        val sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE)
        val userEmail = sharedPreferences.getString("email", null)

        if (userEmail != null) {
            userViewModel.getUserByEmail(userEmail) { user ->
                if (user != null) {
                    currentUser = user
                    updateUIWithUserData(user)
                }
            }
        }
    }

    private fun updateUIWithUserData(user: User) {
        binding.nameTextView.text = user.name
        binding.emailTextView.text = user.email
        binding.passwordTextView.text = "********"

        // Simulação de permissões (exemplo)
        binding.notificationsSwitch.isChecked = true
    }

    private fun setupEditListeners() {
        // Editar nome
        binding.nameTextView.setOnClickListener {
            showEditDialog("name", currentUser.name) { newText ->
                currentUser = currentUser.copy(name = newText)
                binding.nameTextView.text = newText
            }
        }

        // Editar email
        binding.emailTextView.setOnClickListener {
            showEditDialog("email", currentUser.email) { newText ->
                currentUser = currentUser.copy(email = newText)
                binding.emailTextView.text = newText
            }
        }

        // Editar palavra-passe
        binding.passwordTextView.setOnClickListener {
            showEditDialog("password", "********") { newText ->
                currentUser = currentUser.copy(password = newText)
                binding.passwordTextView.text = "********"
            }
        }
    }

    private fun setupSaveButton() {
        binding.saveChangesButton.setOnClickListener {
            // Atualizar utilizador no banco
            userViewModel.updateUser(currentUser)

            // Feedback ao utilizador
            showToast("Alterações guardadas!")
        }
    }

    private fun showEditDialog(field: String, currentText: String, onConfirm: (String) -> Unit) {
        // TODO: Implementar diálogo de edição com EditText
        // Posso te ajudar com isso se quiser!
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}