package com.example.viagens.ui.trip

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.viagens.R
import com.example.viagens.data.model.Trip
import com.example.viagens.ui.viewmodel.TripViewModel
import com.example.viagens.databinding.ActivityNovaViagemBinding


class NovaViagemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNovaViagemBinding
    private lateinit var tripViewModel: TripViewModel

    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNovaViagemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tripViewModel = ViewModelProvider(this)[TripViewModel::class.java]

        setupListeners()
    }

    private fun setupListeners() {
        // Clique na imagem para selecionar via Galeria
        binding.imagemNovaViagem.setOnClickListener {
            openGallery()
        }

        // Botão Salvar Viagem
        binding.btnSalvarNovaViagem.setOnClickListener {
            saveTrip()
        }

        // Botão Voltar
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_IMAGE_PICK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            val uri = data.data
            selectedImageUri = uri
            binding.imagemNovaViagem.setImageURI(uri)
        }
    }

    private fun saveTrip() {
        val nomeDestino = binding.editNomeDestino.text.toString().trim()
        val descricao = binding.editDescricao.text.toString().trim()
        val rating = binding.ratingBarNovaViagem.rating.toDouble().toFloat()

        if (nomeDestino.isEmpty()) {
            Toast.makeText(this, "Por favor, digite o nome do destino", Toast.LENGTH_SHORT).show()
            return
        }

        // Recuperar usuário logado
        val sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE)
        val userEmail = sharedPreferences.getString("email", "") ?: ""

        // Criar objeto Viagem
        val trip = Trip(
            userId = userEmail,
            name = nomeDestino,
            description = descricao,
            rating = rating,
            imageUri = selectedImageUri?.toString()
        )

        // Salvar no Room Database
        tripViewModel.insertTrip(trip)

        // Feedback ao utilizador
        Toast.makeText(this, "Viagem salva!", Toast.LENGTH_SHORT).show()

        // Navegar de volta para MinhasViagensActivity
        finish()
    }

    companion object {
        const val REQUEST_IMAGE_PICK = 100
    }
}