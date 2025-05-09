package com.example.viagens

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.viagens.data.model.Viagem
import com.example.viagens.databinding.ActivityMinhasViagensBinding
import com.example.viagens.ui.adapter.ViagemAdapter
import com.example.viagens.ui.viewmodel.ViagemViewModel

class MinhasViagensActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMinhasViagensBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var viewModel: ViagemViewModel
    private lateinit var viagemAdapter: ViagemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMinhasViagensBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("user_session", Context.MODE_PRIVATE)
        viewModel = ViewModelProvider(this)[ViagemViewModel::class.java]

        // Configurar RecyclerView
        binding.viagensRecyclerView.layoutManager = LinearLayoutManager(this)
        viagemAdapter = ViagemAdapter(emptyList()) { viagem ->
            mostrarDetalhesDaViagem(viagem)
        }
        binding.viagensRecyclerView.adapter = viagemAdapter

        // Carregar todas as viagens inicialmente
        viewModel.getTodasViagens { listaViagens ->
            viagemAdapter.updateViagens(listaViagens)
        }

        // Logout
        binding.logoutButton.setOnClickListener {
            sharedPreferences.edit().clear().apply()
            startActivity(Intent(this, LoginActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            })
            finish()
        }

        // Botão voltar
        binding.backButton.setOnClickListener {
            finish()
        }

        // Botão adicionar
        binding.addButton.setOnClickListener {
            startActivity(Intent(this, NovaViagemActivity::class.java))
        }

        // Bottom Navigation
        binding.navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> true // Já está na activity
                R.id.navigation_dashboard -> {
                    startActivity(Intent(this, GaleriaActivity::class.java))
                    finish()
                    true
                }
                R.id.navigation_notifications -> {
                    startActivity(Intent(this, NovaViagemActivity::class.java))
                    // Não chamar finish aqui
                    true
                }
                R.id.navigation_dashboard -> {
                    startActivity(Intent(this, GaleriaActivity::class.java))
                    // Não chamar finish aqui
                    true
                }
                R.id.navigation_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }

        // Filtros
        binding.filterMelhorClassificacao.setOnClickListener {
            aplicarFiltro("melhor_classificacao")
        }

        binding.filterMaisRecente.setOnClickListener {
            aplicarFiltro("mais_recente")
        }

        binding.filterMaisAntiga.setOnClickListener {
            aplicarFiltro("mais_antiga")
        }
    }

    private fun aplicarFiltro(tipo: String) {
        viewModel.getTodasViagens { listaViagens ->
            val viagensFiltradas = when (tipo) {
                "melhor_classificacao" -> listaViagens.sortedByDescending { it.rating }
                "mais_recente" -> listaViagens.sortedByDescending { it.dataInicio }
                "mais_antiga" -> listaViagens.sortedBy { it.dataInicio }
                else -> listaViagens
            }
            viagemAdapter.updateViagens(viagensFiltradas)
            Toast.makeText(
                this,
                when (tipo) {
                    "melhor_classificacao" -> "Filtrado por melhor classificação"
                    "mais_recente" -> "Filtrado por mais recente"
                    "mais_antiga" -> "Filtrado por mais antiga"
                    else -> "Filtro desconhecido"
                },
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun mostrarDetalhesDaViagem(viagem: Viagem) {
        // Redirecionar para DetalhesDasViagensActivity, passando o ID da viagem
        val intent = Intent(this, DetalhesDasViagensActivity::class.java).apply {
            putExtra("VIAGEM_ID", viagem.id)
        }
        startActivity(intent)
    }
}