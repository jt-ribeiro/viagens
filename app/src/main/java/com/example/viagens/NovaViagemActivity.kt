package com.example.viagens

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.viagens.data.model.Viagem
import com.example.viagens.databinding.ActivityNovaViagemBinding
import com.example.viagens.ui.viewmodel.ViagemViewModel

class NovaViagemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNovaViagemBinding
    private lateinit var viewModel: ViagemViewModel
    private var imagemUri: Uri? = null

    // Launcher para selecionar imagem da galeria
    private val selecionarImagemLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            imagemUri = result.data?.data
            imagemUri?.let {
                binding.imagemNovaViagem.setImageURI(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNovaViagemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ViagemViewModel::class.java]

        // Configurar clique na imagem para selecionar da galeria
        binding.imagemNovaViagem.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            selecionarImagemLauncher.launch(intent)
        }

        // Configurar botão salvar
        binding.btnSalvarNovaViagem.setOnClickListener {
            salvarViagem()
        }

        // Configurar botão voltar
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun salvarViagem() {
        val destino = binding.editNomeDestino.text.toString().trim()
        val descricao = binding.editDescricao.text.toString().trim()
        val rating = binding.ratingBarNovaViagem.rating
        // Campos fixos para simplificar (ajuste conforme necessário)
        val dataInicio = "2025-01-01" // Substitua por um DatePicker ou entrada do usuário
        val dataFim = "2025-01-07"   // Substitua por um DatePicker ou entrada do usuário
        val custo = 0.0              // Substitua por um EditText para entrada do usuário

        // Validação
        if (destino.isEmpty()) {
            Toast.makeText(this, "Por favor, insira o destino", Toast.LENGTH_SHORT).show()
            return
        }
        if (descricao.isEmpty()) {
            Toast.makeText(this, "Por favor, insira a descrição", Toast.LENGTH_SHORT).show()
            return
        }
        if (rating == 0f) {
            Toast.makeText(this, "Por favor, selecione uma classificação", Toast.LENGTH_SHORT).show()
            return
        }

        // Criar objeto Viagem
        val viagem = Viagem(
            destino = destino,
            descricao = descricao,
            rating = rating,
            dataInicio = dataInicio,
            dataFim = dataFim,
            custo = custo,
            imagemUri = imagemUri?.toString() // Armazena o URI da imagem, se selecionada
        )

        // Salvar no banco de dados
        viewModel.inserirViagem(viagem)
        Toast.makeText(this, "Viagem salva com sucesso!", Toast.LENGTH_SHORT).show()
        finish() // Volta para a MinhasViagensActivity
    }
}