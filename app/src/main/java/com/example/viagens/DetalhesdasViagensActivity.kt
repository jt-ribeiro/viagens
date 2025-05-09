package com.example.viagens

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.viagens.data.model.Viagem
import com.example.viagens.databinding.DetalhesDasViagensBinding
import com.example.viagens.ui.viewmodel.ViagemViewModel

class DetalhesDasViagensActivity : AppCompatActivity() {

    private lateinit var binding: DetalhesDasViagensBinding
    private lateinit var viewModel: ViagemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetalhesDasViagensBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ViagemViewModel::class.java]

        // Obter ID da viagem do Intent
        val viagemId = intent.getLongExtra("VIAGEM_ID", -1)
        if (viagemId == -1L) {
            Toast.makeText(this, "Viagem inválida", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Buscar detalhes da viagem
        viewModel.getViagemById(viagemId) { viagem ->
            if (viagem != null) {
                exibirDetalhes(viagem)
            } else {
                Toast.makeText(this, "Viagem não encontrada", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        // Configurar botões
        binding.backButton.setOnClickListener {
            finish()
        }

        binding.viewLocationsButton.setOnClickListener {
            Toast.makeText(this, "Funcionalidade de ver locais em desenvolvimento", Toast.LENGTH_SHORT).show()
        }

        binding.editDetailsButton.setOnClickListener {
            Toast.makeText(this, "Funcionalidade de edição em desenvolvimento", Toast.LENGTH_SHORT).show()
        }
    }

    private fun exibirDetalhes(viagem: Viagem) {
        binding.travelTitle.text = viagem.destino
        binding.startDate.text = viagem.dataInicio
        binding.endDate.text = viagem.dataFim
        binding.ratingBar.rating = viagem.rating
        binding.descriptionText.text = viagem.descricao // Removido o custo
        Glide.with(this)
            .load(viagem.imagemUri?.takeIf { it.isNotEmpty() } ?: R.drawable.background_main)
            .placeholder(R.drawable.background_main)
            .error(R.drawable.background_main)
            .into(binding.travelImage)

        // Configurar navegação inferior
        binding.navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    startActivity(Intent(this, MinhasViagensActivity::class.java))
                    finish()
                    true
                }
                R.id.navigation_dashboard -> {
                    startActivity(Intent(this, GaleriaActivity::class.java))
                    finish()
                    true
                }
                R.id.navigation_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}